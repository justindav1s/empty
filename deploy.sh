#!/bin/bash

echo "---- This is the deployer script."
echo "---- it run in the s2i builder image and does what it akes to build the application :"
echo "---- * building it ..."
echo "---- * sucking prebuilt components out of nexus"
echo "---- * ansible ?"

cp /tmp/src/scripts/setenv.sh /ib/appl/tomcat7/bin

cp /tmp/src/scripts/logback.xml /ib/appl/tomcat7/conf

wget "http://172.30.39.124:8081/service/local/artifact/maven/redirect?r=snapshots&g=com.ba.captwo.eda.demo&a=selling-services&v=LATEST&p=war" -O selling.war
mv selling.war  /ib/appl/tomcat7/webapps/selling.war