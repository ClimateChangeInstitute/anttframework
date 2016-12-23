sudo echo "deb http://http.debian.net/debian jessie-backports main" >> /etc/apt/sources.list
sudo apt-get update
sudo apt-get upgrade
sudo apt-get -y install openjdk-8-jdk maven build-essential git postgresql linux-headers-$(uname -r)
