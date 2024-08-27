package edu.cust.util;

public class IPUtil {

	public static long stringToLong(String strIp) {
		String[] ip = strIp.split("\\.");
		return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8)
				+ Long.parseLong(ip[3]);
	}

	public static String longToString(long longIp) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(0xff & (longIp >>> 24));
	    buffer.append(".");
	    buffer.append(0xff & (longIp >>> 16));
	    buffer.append(".");
	    buffer.append(0xff & (longIp >>> 8));
	    buffer.append(".");
	    buffer.append(0xff & longIp);
		return buffer.toString();
	}
}
