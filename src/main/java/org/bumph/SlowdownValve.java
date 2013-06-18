package org.bumph;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.util.IOTools;
import org.apache.catalina.valves.ValveBase;

/**
 * This valve allows requests to be paused for a bit of time.
 * 
 * @author buckett
 */
public class SlowdownValve extends ValveBase {

	private Pattern matcher;
	private Random rnd;
	private int pause = 1000;
	private int randomPause = 0;

	public SlowdownValve() {
		rnd = new Random();
	}

	public void setRequestURI(String pattern) {
		this.matcher = Pattern.compile(pattern);
	}

	public void setPause(int milliseconds) {
		this.pause = milliseconds;
	}

	public void setRandomPause(int milliseconds) {
		this.randomPause = milliseconds;
	}

	@Override
	public void invoke(final Request request, final Response response)
			throws IOException, ServletException {
		if (matcher.matcher(request.getRequestURI()).matches()) {
			int duration = pause + rnd.nextInt(randomPause);
			try {
				Thread.sleep(duration);
				if (containerLog.isDebugEnabled()) {
					containerLog.debug("Slept for "+ duration+ "ms");
				}
			} catch (InterruptedException ie) {
				containerLog.warn("Didn't sleep for "+ duration+ "ms, got interrupted.");
			}
		}

		getNext().invoke(request, response);

	}

}
