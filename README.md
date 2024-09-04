`server` directory: This is a Spring Boot-based application responsible for managing and configuring DDoS attacks. 
It handles interaction with the MySQL database, manages attack configuration, and receives and parses network control instructions. 
This part of the code ensures that the configuration and scheduling of attacks can be managed through the web interface.

`DDoS` directory: Contains programs that actually perform TCP packet attacks. It implements a TCP SYN Flood attack by constructing and sending forged SYN packets. 
In addition, it includes the ability to monitor network traffic to identify and process attack-related SYN packets. 
This part of the code is responsible for specific attack execution and traffic monitoring.

``rocksaw-1.1.0` directory: This is a C-based Netfilter implementation that integrates with Java through JNI to read IP queues from the kernel. It provides low-level network packet capture capabilities, enabling Java applications to access and process network packets from the kernel. 
This part of the code allows Java programs to call C language libraries through JNI, thereby achieving efficient reading and processing of IP queues.
