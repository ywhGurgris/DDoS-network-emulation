if [ $# != 4 ] ; then
echo "USAGE: $0 ServerIP ServerPort LocalIP LocalDev"
exit 1;
fi
iptables -A INPUT -j NFQUEUE --queue-num 0
iptables -A FORWARD -j NFQUEUE --queue-num 0
iptables -A OUTPUT -j NFQUEUE --queue-num 0
java -cp "ddos.jar:lib/*" -Djava.library.path=lib/ edu.cust.blackhole.Main $1 $2 $3 $4 > log 2>&1
