kill $(ps -e | grep java | awk '{print $1}')
iptables -D INPUT 1
