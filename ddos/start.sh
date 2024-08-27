iptables -A INPUT -i eth0 -j NFQUEUE --queue-num 0
java -cp "ddos.jar:lib/*" -Djava.library.path=lib/ edu.cust.ddos.Main > log 2>&1
