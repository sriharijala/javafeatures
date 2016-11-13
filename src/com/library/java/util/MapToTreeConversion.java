package com.library.java.util;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
HasMap is fater to add items. TreeMap reorder the keys.abstractIn this example you can 
convert the HashMap to TreeMap and see the keys are reordered.
*/


public class MapToTreeConversion {

	public static void main(String[] a) {
		
		Map<String, String> yourMap = new HashMap<String, String>();
		yourMap.put("1", "one");
		yourMap.put("3", "three");
		yourMap.put("2", "two");

		Map<String, String> sortedMap = new TreeMap<String, String>(yourMap);

		System.out.println(sortedMap);
	}

}
