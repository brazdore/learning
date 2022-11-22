#!/bin/bash

mkdir -p ./test-output

curl $1:8080/calc/sum/2/3 -o ./test-output/sum.txt
curl $1:8080/calc/sub/10/2 -o ./test-output/sub.txt
curl $1:8080/calc/mul/8/8 -o ./test-output/mul.txt
curl $1:8080/calc/div/5/2.5 -o ./test-output/div.txt
curl $1:8080/calc/exp/2/8 -o ./test-output/exp.txt

curl $1:8080/calc/history -o ./test-output/history.txt
curl $1:8080/calc/clear -o ./test-output/clear.txt
curl $1:8080/headers -o ./test-output/headers.txt