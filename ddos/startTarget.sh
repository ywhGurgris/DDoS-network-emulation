if [ $# != 3 ] ; then
echo "USAGE: $0 ServerIP ServerPort LocalIP"
exit 1;
fi
java -cp "ddos.jar:lib/*" -Djava.library.path=lib/ edu.cust.target.Main $1 $2 $3 > log 2>&1 &
