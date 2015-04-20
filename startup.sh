#!/bin/bash
# This is a script to set up the "fresh" AWS server to the most updated state.
# Any required settings made on the AWS server should take a note to this file. Such as create database table, or insert some rows.

# Install mavan, git, ipython and its dependencies.
sudo apt-get install maven
sudo apt-get install git
sudo apt-get install openjdk-7-jdk
sudo apt-get install nginx
sudo apt-get install python-pip
sudo apt-get install python3-pip
sudo apt-get install python3-matplotlib
sudo pip install ipython
sudo pip3 install pyzmq
sudo pip install Jinja2
sudo pip install tornado
sudo pip3 install jsonschema

# Install mysql server, with password root for user root.
# Replace the password if needed
echo mysql-server mysql-server/root_password password root | sudo debconf-set-selections
echo mysql-server mysql-server/root_password_again password root | sudo debconf-set-selections
sudo apt-get install mysql-server

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

# Set AWS server
cd /opt/project/MultiDBs-Utils
sudo mvn install
cd /opt/project/MultiDBs-INotebook-Server
sudo mvn install
# Move a .sh and config file to target for java server
cp /opt/project/MultiDBs-INotebook-Server/pleaseAddUser.sh /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/target
cp /opt/project/MultiDBs-INotebook-Server/config.properties /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/target
# Run AWS server
cd /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/target
# Run java server
nohup java -jar multidbsinotebookserverapi-0.1-SNAPSHOT.jar config.properies> log.out 2> error.log < /dev/null &
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

# Copy jupyter server configuration file from git
# Replace the IPython directory with your own one
cp opt/project/MultiDBs-INotebook-IPython-Extention/js/main.js /usr/local/lib/python3.4/dist-packages/IPython/html/static/notebook/js/main.js
cp opt/project/MultiDBs-INotebook-IPython-Extention/css/overiride.css /usr/local/lib/python3.4/dist-packages/IPython/html/static/notebook/css/overiride.css

# Run jupyterhub
sudo jupyterhub --port 8888
