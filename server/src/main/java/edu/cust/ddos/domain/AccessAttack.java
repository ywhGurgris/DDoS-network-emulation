package edu.cust.ddos.domain;

import lombok.Data;

@Data
public class AccessAttack {

	//private String attackIp;
	//private int attackPort;
	private String targetIp;
	private int targetPort;
	//private String ssid;
	private int threshold;
}
