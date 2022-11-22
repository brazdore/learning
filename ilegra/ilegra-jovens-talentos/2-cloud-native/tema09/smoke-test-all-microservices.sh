#!/bin/bash

echo "SUM/SUB/MUL/DIV/EXP"
echo "2+2= $(curl -s http://localhost:6004/math/sum/2/2)"
echo "6*6= $(curl -s http://localhost:6002/math/mul/6/6)"
echo "5-2= $(curl -s http://localhost:6003/math/sub/5/2)"
echo "10/3= $(curl -s http://localhost:6001/math/div/10/3)"
echo "2^8= $(curl -s http://localhost:6009/math/exp/2/8)"
echo "-----------------"
echo "CALC"
echo "sum:2+2= $(curl -s http://localhost:6005/math/calc/sum/2/2)"
echo "sub:5-3= $(curl -s http://localhost:6005/math/calc/sub/5/3)"
echo "mul:6*6= $(curl -s http://localhost:6005/math/calc/mul/6/6)"
echo "div:10/2= $(curl -s http://localhost:6005/math/calc/div/10/2)"
echo "exp:2^8= $(curl -s http://localhost:6005/math/calc/exp/2/8)"
echo "ops= $(curl -s http://localhost:6005/math/ops/)"


