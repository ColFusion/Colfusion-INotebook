#!/bin/bash
# This is a script to set up the "fresh" AWS server to the most updated state.
# Any required settings made on the AWS server should take a note to this file. Such as create database table, or insert some rows.

# Installation
sudo apt-get install maven
sudo apt-get install git
sudo apt-get install openjdk-7-jdk
sudo apt-get install mysql-server
sudo apt-get install nginx
sudo apt-get install python-pip
sudo apt-get install python3-pip
sudo pip install ipython
sudo pip3 install pyzmq
sudo pip install Jinja2
sudo pip install tornado
sudo pip3 install jsonschema

# update python to 3.3 (can be ignored)
#sudo apt-get install build-essential
#sudo apt-get install libsqlite3-dev
#sudo apt-get install sqlite3 # for the command-line client
#sudo apt-get install bzip2 libbz2-dev
#wget http://www.python.org/ftp/python/3.3.5/Python-3.3.5.tar.xz
#tar xJf ./Python-3.3.5.tar.xz
#cd ./Python-3.3.5
#./configure --prefix=/opt/python3.3
#make && sudo make install
#mkdir ~/bin
#ln -s /opt/python3.3/bin/python3.3 ~/bin/py
#Then run python 3.3 by /opt/python3.3/bin/python3

# Create project directory
cd /opt
sudo mkdir project
cd project

# Clone repositories from github
sudo git clone https://github.com/infsci2711/MultiDBs-INotebook-IPython-Extention.git
sudo git clone https://github.com/infsci2711/MultiDBs-INotebook-Server.git
sudo git clone https://github.com/infsci2711/MultiDBs-INotebook-WebClient.git
sudo git clone https://github.com/infsci2711/MultiDBs-Utils.git

# Create symlink to the client code in the project folder (need to be done each time after git update on MultiDBs-INotebook-WebClient)
cd /usr/share/nginx
sudo rm -R html
sudo ln -sv /opt/project/MultiDBs-INotebook-WebClient html
# Make "project" folder searchable
sudo chmod 755 /opt/project

# create database, table and user
mysql -u root -proot < /opt/project/MultiDBs-INotebook-Server/notebook.sql

# Set notebook server (http://ipython.org/ipython-doc/1/interactive/public_server.html#notebook-public-server)
#ipython
#In [1]: from IPython.lib import passwd
#In [2]: passwd()
#Enter password: notebook
#Verify password: notebook
#Out[2]: 'sha1:e704eb4d8f11:c83c9c8785390abf5d90c366083cd497b0c149b6'
## Self-assigned certificate
#sudo openssl req -x509 -nodes -days 365 -newkey rsa:1024 -keyout mycert.pem -out mycert.pem
#ipython notebook --certfile=mycert.pem

# Running a public notebook server
#ipython profile create nbserver
#pico /home/student/.ipython/profile_nbserver/ipython_notebook_config.py
##"add" something to it
##run inotebook
#ipython notebook --profile=nbserver

# Set AWS server
cd /opt/project/MultiDBs-Utils
sudo mvn install
cd /opt/project/MultiDBs-INotebook-Server
sudo mvn install
# Run AWS server
cd /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/target
nohup java -jar multidbsinotebookserverapi-0.1-SNAPSHOT.jar > log.out 2> error.log < /dev/null &
# to check if it is running: "lsof -i:portNumber" on another terminal

# Jupyter Notebook for multi-user
cd /opt/project
sudo git clone https://github.com/jupyter/jupyterhub.git
cd /opt/project/jupyterhub
sudo apt-get install npm nodejs-legacy
sudo npm install -g configurable-http-proxy
sudo pip install -r requirements.txt
sudo pip3 install .
sudo pip install -r dev-requirements.txt
sudo pip3 install -e .

# Run jupyterhub
sudo jupyterhub --port 8888

# Manage ipython interface (copy from git to /home/student/.ipython)
cp -r /opt/project/MultiDBs-INotebook-IPython-Extention/nbextensions /home/student/.ipython
cp -r /opt/project/MultiDBs-INotebook-IPython-Extention/profile_default /home/student/.ipython
cp -r /opt/project/MultiDBs-INotebook-IPython-Extention/profile_nbserver /home/student/.ipython

# Add linux user
# sudo adduser --gecos "" username # and set password

#!/bin/bash

pass=$(openssl passwd -crypt $2)
echo $pass

echo infsci27115 | sudo -S useradd -m -p $pass $1
