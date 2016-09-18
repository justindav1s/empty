#!/bin/bash

echo "---- This is the setenv script."
export JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=openshift"
export JAVA_OPTS="$JAVA_OPTS -Dlogback.configurationFile=/ib/appl/tomcat7/conf/logback.xml"