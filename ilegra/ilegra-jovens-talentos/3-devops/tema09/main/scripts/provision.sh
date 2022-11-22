#!/bin/bash

sudo apt-get -y update
sudo apt-get -y install python3-pip
sudo sh -c 'echo "export PATH=$PATH:/usr/local/go/bin" >> /etc/profile'
sudo apt-get -y install ansible
