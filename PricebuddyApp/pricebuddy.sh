#!/bin/sh
cp=".:./navsmall-0.0.1-SNAPSHOT.jar:./log4j-1.2.17.jar"
export cp
java -cp $cp com.nav.driver.Driver "$@"
