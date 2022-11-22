#!/bin/bash

cd go-calc

go mod init example.com/calc
go mod tidy
go run .
