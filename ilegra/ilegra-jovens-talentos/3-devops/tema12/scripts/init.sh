#!/bin/bash

cd main
go mod init example.com/calc
go mod tidy
echo "Go Module: Initialized"