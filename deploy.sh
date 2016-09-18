#!/bin/bash

echo "---- This is the deployer script."
echo "---- it run in the s2i builder image and does what it akes to build the application :"
echo "---- * building it ..."
echo "---- * sucking prebuilt components out of nexus"
echo "---- * ansible ?"

cp /tmp/src/scripts/setenv.sh /ib/appl/tomcat7/bin

cat cp /tmp/src/scripts/logback.xml

cp /tmp/src/scripts/logback.xml /ib/appl/tomcat7/conf

wget http://central.maven.org/maven2/ch/qos/logback/logback-classic/1.1.7/logback-classic-1.1.7.jar
mv logback-classic-1.1.7.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/ch/qos/logback/logback-core/1.1.7/logback-core-1.1.7.jar
mv logback-core-1.1.7.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar
mv slf4j-api-1.7.5.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/org/slf4j/jcl-over-slf4j/1.7.5/jcl-over-slf4j-1.7.5.jar
mv jcl-over-slf4j-1.7.5.jar /ib/appl/tomcat7/lib

wget http://central.maven.org/maven2/org/apache/camel/camel-core/2.13.2/camel-core-2.13.2.jar
mv camel-core-2.13.2.jar /ib/appl/tomcat7/lib

wget "http://172.30.39.124:8081/service/local/artifact/maven/redirect?r=snapshots&g=com.ba.captwo.eda.demo&a=selling-services&v=LATEST&p=war" -O selling.war
mv selling.war  /ib/appl/tomcat7/webapps/selling.war