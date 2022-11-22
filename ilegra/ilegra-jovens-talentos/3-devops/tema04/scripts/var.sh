#!/bin/bash

today=$(date +%F)
target_path=$HOME/backup/conf/$today

mkdir -p $target_path
cd $target_path
touch env_var.txt
printenv > env_var.txt