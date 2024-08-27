iptables -D INPUT 1
iptables -D FORWARD 1
iptables -D OUTPUT 1
kill $(ps -e | grep java | awk '{print $1}')
