@echo off

set cpath=".;./navsmall-0.0.1-SNAPSHOT.jar;./log4j-1.2.17.jar"
java -classpath %cpath% com.nav.driver.Driver %*
