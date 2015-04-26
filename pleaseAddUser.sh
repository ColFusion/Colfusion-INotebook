#!/bin/bash

pass=$(openssl passwd -crypt $2)
echo $pass

useradd -m -p $pass $1