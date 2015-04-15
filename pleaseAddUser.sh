#!/bin/bash

pass=$(openssl passwd -crypt $2)
echo $pass

echo infsci27115 | sudo -S useradd -m -p $pass $1