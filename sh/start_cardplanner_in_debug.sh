#!/bin/sh
java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4242 -cp ./lib/YAWF.jar:./lib/jetty-continuation.jar:./lib/jetty-http.jar:./lib/jetty-io.jar:./lib/jetty-jndi.jar:./lib/jetty-plus.jar:./lib/jetty-security.jar:./lib/jetty-server.jar:./lib/jetty-servlet.jar:./lib/jetty-util.jar:./lib/jetty-webapp.jar:./lib/jetty-xml.jar:./lib/servlet-api-3.0.jar uk.co.itstherules.yawf.server.StandaloneServerApplication /CardPlanner ./lib/CardPlanner.war

