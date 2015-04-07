cat < username.txt | xargs adduser --gecos ""
chpasswd < serc.txt
pwconv
echo "OK"