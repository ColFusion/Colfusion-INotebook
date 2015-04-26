#!/bin/bash
set -e

echo "installing mysql"

echo mysql-server mysql-server/root_password password $MYSQL_PASSWORD | debconf-set-selections
echo mysql-server mysql-server/root_password_again password $MYSQL_PASSWORD | debconf-set-selections

apt-get install -y mysql-server

echo "starting mysql"

service mysql start

echo "running sql script"

mysql -uroot -p$MYSQL_PASSWORD < /home/notebook/notebook.sql

echo "creating folder for servers source code"

cd /opt
mkdir -p project
cd project

echo "cloning git repos"

git clone https://github.com/infsci2711/MultiDBs-INotebook-IPython-Extention.git
git clone https://github.com/infsci2711/MultiDBs-INotebook-Server.git
git clone https://github.com/infsci2711/MultiDBs-Utils.git

echo "building utils project"

cd /opt/project/MultiDBs-Utils
mvn install

echo "building server project"

cd /opt/project/MultiDBs-INotebook-Server
mvn install

echo "creating folder for deployed code"

cd /opt/project
mkdir -p deployed

echo "copying jar and config file to deploed folder"

cp /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/target/multidbsinotebookserverapi-0.1-SNAPSHOT.jar /opt/project/deployed
cp /opt/project/MultiDBs-INotebook-Server/pleaseAddUser.sh /opt/project/deployed
cp /opt/project/MultiDBs-INotebook-Server/config.properties /opt/project/deployed

echo "setting jupyterhub"

echo "copy jupyter server configuration file from git"
echo "replace the IPython directory with your own one"
cp /opt/project/MultiDBs-INotebook-IPython-Extention/js/main.js /usr/local/lib/python3.4/dist-packages/IPython/html/static/notebook/js/main.js
cp /opt/project/MultiDBs-INotebook-IPython-Extention/css/overiride.css /usr/local/lib/python3.4/dist-packages/IPython/html/static/notebook/css/override.css

echo "starting java server"

cd /opt/project/deployed
nohup java -jar  /opt/project/deployed/multidbsinotebookserverapi-0.1-SNAPSHOT.jar /opt/project/deployed/config.properties > /opt/project/deployed/log.out 2> /opt/project/deployed/error.log < /dev/null &

echo "run jupyter"
cd /srv/jupyterhub/
jupyterhub --port 8888

exec "$@"