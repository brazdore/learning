#!/bin/bash

cd ..
cd data-analysis-app
./gradlew clean build
java -jar build/libs/data-analysis-app.jar