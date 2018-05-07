#!/bin/sh
java -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4242 -cp "libs/*" uk.co.itstherules.cardplanner.server.CardPlannerServer