#!/usr/bin/env bash

echo "Provisioning machine"
sudo apt-get update > /dev/null

echo "Installing jdk8"
sudo add-apt-repository ppa:openjdk-r/ppa -y > /dev/null
sudo apt-get update -y > /dev/null
sudo apt-get install openjdk-8-jdk -y > /dev/null

echo "Installing debconf"
apt-get install debconf-utils -y > /dev/null

sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password 123"
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password 123"

echo "Installing MySQL Server"
sudo apt-get install mysql-server -y > /dev/null

echo "Installing Redis Server"
sudo apt-get install redis-server -y > /dev/null

echo "Starting http://localhost/hello service"
sudo java -jar /home/vagrant/demo-0.0.1-SNAPSHOT.jar