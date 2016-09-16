#!/bin/bash

echo "---- This is the deployer script."
echo "---- it run in the s2i builder image and does what it akes to build the application :"
echo "---- * building it ..."
echo "---- * sucking prebuilt components out of nexus"
echo "---- * ansible ?"


wget http://nexus-ci.ba.datr.eu/content/repositories/snapshots/com/ba/captwo/eda/demo/selling-services/1.0-SNAPSHOT/selling-services-1.0-20160916.161135-1.war