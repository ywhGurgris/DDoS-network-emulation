package edu.cust.ccsds;

public class OutputMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OutputMonitor om = new OutputMonitor();
		new Thread(om).start();
	}
}
