#!/bin/bash

echo "---- This is the deployer script."
echo "---- it run in the s2i builder image and does what it akes to build the application :"
echo "---- * building it ..."
echo "---- * sucking prebuilt components out of nexus"
echo "---- * ansible ?"

cp /tmp/src/setenv.sh /ib/appl/tomcat7/bin

wget http://central.maven.org/maven2/ch/qos/logback/logback-classic/1.1.7/logback-classic-1.1.7.jar
mv logback-classic-1.1.7.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/ch/qos/logback/logback-core/1.1.7/logback-core-1.1.7.jar
mv logback-core-1.1.7.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.21/slf4j-api-1.7.21.jar
mv slf4j-api-1.7.21.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/org/apache/camel/camel-core/2.13.2/camel-core-2.13.2.jar
mv camel-core-2.13.2.jar /ib/appl/tomcat7/lib

wget http://172.30.39.124:8081/content/repositories/snapshots/com/ba/captwo/eda/demo/selling-services/1.0-SNAPSHOT/selling-services-1.0-20160916.161135-1.war
mv selling-services-1.0-20160916.161135-1.war  /ib/appl/tomcat7/webapps/selling.war