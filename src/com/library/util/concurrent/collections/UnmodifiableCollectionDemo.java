package com.library.util.concurrent.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/*
The unmodifiableCollection() method is used to return an unmodifiable view of the specified collection.
And an attempt to modify the collection will result in an UnsupportedOperationException.
*/

public class UnmodifiableCollectionDemo {

	public static void main(String[] args) {
		
		// create array list
		List<Character> list = new ArrayList<Character>();

		// populate the list
		list.add('X');
		list.add('Y');

		System.out.println("Initial list: " + list);

		Collection<Character> immutablelist = Collections.unmodifiableCollection(list);

		// try to modify the list
		immutablelist.add('Z');
	}

}
