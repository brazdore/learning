#!/bin/bash

terraform init
terraform apply -auto-approve -var "aws_access_key=$1" -var "aws_secret_key=$2" -var "aws_key_name=$3"