#!/bin/bash

echo "operation=$1"
echo "x=$2"
echo "y=$3"

op=$1
x=$2
y=$3

sum() {
    result=$(($x + $y))
    echo $result
}

sub() {
   result=$(($x - $y))
   echo $result
}

mul() {
  result=$(($x * $y))
  echo $result
}

div() {
  if [ $y = 0 ]; then
    echo "Invalid! Cannot divide by [ZERO]"
  else
  result=$((${x}00/${y}))
  echo "${result:0:-2}.${result: -2}"
  fi
}

exp() {
  result=$(($x ** $y))
  echo $result
}

case $op in

  sum)
    sum
    ;;
    
  sub)
    sub
    ;;
    
  mul)
    mul
    ;;
    
  div)
    div
    ;;
    
  exp)
    exp
    ;;
    
  *)
    echo -n "Unknown operation: [$op] is not valid. Try: sum; sub; div; mul; exp."
    ;;
esac