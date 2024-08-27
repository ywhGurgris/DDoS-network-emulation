package edu.cust.ddos.domain;

import lombok.Data;

@Data
public class BhrNode {

	private String id;
	private String name;
	private String ip;
	private int port;
	private String targetIp;
	private String status;
}
