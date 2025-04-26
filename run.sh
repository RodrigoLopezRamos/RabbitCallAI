#!/bin/bash

mvn compile quarkus:dev -Dnet.bytebuddy.experimental=true -Dquarkus.profile=local -Dmaven.surefire.debug -DskipTests
