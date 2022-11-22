#!/bin/bash

today=$(date +%F)
target_path=$HOME/backup/data/$today

echo -n "Source folder path: "
read source_path
mkdir -p $target_path
zip -r -j $source_path.zip $source_path
mv $source_path.zip $target_path