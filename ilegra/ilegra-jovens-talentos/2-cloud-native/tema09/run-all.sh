#!/bin/bash

./gradlew :div-microservice:run > div.log &
./gradlew :mul-microservice:run > mul.log &
./gradlew :sub-microservice:run > sub.log &
./gradlew :sum-microservice:run > sum.log &
./gradlew :exp-microservice:run > exp.log &
./gradlew :calc-microservice:run > calc.log &
