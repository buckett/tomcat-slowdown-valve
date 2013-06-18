Tomcat Slowdown Valve
================

A quick tomcat valve which pauses a request for a little time.

Installation
------------

Drop the jar file into the server/lib folder at the toplevel of the Tomcat distribution.
As it needs to be in the same classloader as org.apache.catalina.valves.ValveBase which
is normally in catalina.jar

Configuration
-------------

In your tomcat server configuration file (eg server.xml) add a Valve element to a <Engine>,
<Host> or <Context>. The requestURI attribute specifies the request URI which must match
to require a user parameter on the request. The pause attribute specifies how many milliseconds
to pause for (default 1000ms). The pauseRandom attribute specifies that amount of random data
to be added on to the pause time (default 0ms);
  
    <!-- Example showing a context which has all /login requests protected. -->
    <Context docBase="quick-test" path="/quick-test" reloadable="true">
      <Valve className="org.bumph.SlowdownValve" requestURI="/quick-test/service/.*" pause="500" pauseRandom="1000" />
    </Context>
