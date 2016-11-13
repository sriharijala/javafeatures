package com.library.util.collections.skiplist;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConcurrentNavigableMapDemo {

	public static void main(String[] args) {
		
	
		ConcurrentNavigableMap map = new ConcurrentSkipListMap();

		map.put("1", "one");
		map.put("2", "two");
		map.put("3", "three");

		ConcurrentNavigableMap headMap = map.headMap("2");
		
		System.out.println("The name associated with headMap 2 is "+ headMap);

		ConcurrentNavigableMap tailMap = map.tailMap("2");
		
		System.out.println("The name associated with tailMap 2 is "+ tailMap);
		
		ConcurrentNavigableMap subMap = map.subMap("2", "3");
		
		System.out.println("The name associated with subMao 2 upto 3 is "+ subMap);
		
	}
	
}
