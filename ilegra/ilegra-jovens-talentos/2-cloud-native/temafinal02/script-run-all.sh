#!/bin/bash

./gradlew clean
./gradlew build

./gradlew :song-service:bootRun > log-song-service-8091.log &
./gradlew :playlist-service:bootRun > log-playlist-service-8092.log &
./gradlew :app-service:bootRun > log-app-service-8093.log
