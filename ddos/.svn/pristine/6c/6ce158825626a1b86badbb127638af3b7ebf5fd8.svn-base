iptables -A INPUT -j NFQUEUE --queue-num 0
iptables -A OUTPUT -j NFQUEUE --queue-num 1
java -cp "ddos.jar:lib/*" -Djava.library.path=lib/ edu.cust.ccsds.InputMain > log0 2>&1 &
java -cp "ddos.jar:lib/*" -Djava.library.path=lib/ edu.cust.ccsds.OutputMain > log1 2>&1 &