package com.library.util.concurrent.collections;

import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

public class SynchronizedCollectionDemo {

	public static void main(String... strings) {

		// create vector object
		Vector<String> vector = new Vector<String>();

		// populate the vector
		vector.add("1");
		vector.add("2");
		vector.add("3");
		vector.add("4");
		vector.add("5");

		// create a synchronized view
		Collection<String> c = Collections.synchronizedCollection(vector);

		System.out.printf("Sunchronized view is : %s%n" , c);
	}
}
