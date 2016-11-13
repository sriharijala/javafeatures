package com.library.util.concurrent.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
ConcurrentHashMap is the class that is similar to HashMap but works fine when you try to modify your map at runtime.

In following ConcurrentHashMap takes care of any new entry in the map whereas HashMap throws 
ConcurrentModificationException.

new entry got inserted in the HashMap but Iterator is failing. Actually Iterator on Collection objects 
are fail-fast i.e any modification in the structure or the number of entry in the collection object will 
trigger this exception thrown by iterator.

Lets look at the exception stack trace closely. The statement that has thrown Exception is:

String key = it1.next();

HashMap contains a variable to count the number of modifications and iterator use it when you call its next() function 
to get the next entry.

*/

public class ConcurrentHashMapDemo {

	public static void main(String[] args) {

		// ConcurrentHashMap
		Map<String, String> myMap = new ConcurrentHashMap<String, String>();
		myMap.put("1", "1");
		myMap.put("2", "1");
		myMap.put("3", "1");
		myMap.put("4", "1");
		myMap.put("5", "1");
		myMap.put("6", "1");
		
		System.out.printf("ConcurrentHashMap before iterator: %s%n" ,myMap);
		Iterator<String> it = myMap.keySet().iterator();

		while (it.hasNext()) {
			String key = it.next();
			if (key.equals("3"))
				myMap.put(key + "new", "new3");
		}
		
		System.out.printf("ConcurrentHashMap after iterator: %s%n" , myMap);

		// HashMap
		myMap = new HashMap<String, String>();
		myMap.put("1", "1");
		myMap.put("2", "1");
		myMap.put("3", "1");
		myMap.put("4", "1");
		myMap.put("5", "1");
		myMap.put("6", "1");
		
		System.out.printf("HashMap before iterator: %s%n " , myMap);
		Iterator<String> it1 = myMap.keySet().iterator();

		while (it1.hasNext()) {
			String key = it1.next();
			if (key.equals("3"))
				myMap.put(key + "new", "new3");
		}
		
		System.out.printf("HashMap after iterator: %s%n ", myMap);
	}

}
