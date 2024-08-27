package edu.cust;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;

public class FloatConvertTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "0.e+00,0.e+00,0.e+00,0.e+00,8.655467e+00,0.e+00,5.0560384e+00,6.92544e-0";
		s = "0.e+00,0.e+00,0.e+00,0.e+00,1.8339832e+01,0.e+00,1.0555315e+01,2.3655358e-0";
		String[] arr = s.split(",");
		for (String string : arr) {
			double d = Double.parseDouble(string);
			System.out.println(d);
		}
		System.out.println(new Random().nextInt(Integer.MAX_VALUE));
		
		System.out.println(Arrays.toString("tcp   a  b   ".split(" +")));
		HashMap<String,Boolean> map = new HashMap<>();
		map.put("protect", true);
		Map<?,?> obj = new Gson().fromJson(new Gson().toJson(map), Map.class);
		boolean protect = (Boolean) obj.get("protect");
		System.out.println(protect);
		System.out.println(0xe7ff);
		System.out.println(0x4354);
		System.out.println(0x4359);
		System.out.println(0x4361);
	}

}
