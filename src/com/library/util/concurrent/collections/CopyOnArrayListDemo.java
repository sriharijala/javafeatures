package com.library.util.concurrent.collections;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/*
CopyOnWriteArrayList is a concurrent Collection class introduced in Java 5 Concurrency API along with
its popular cousin ConcurrentHashMap in Java. CopyOnWriteArrayList implements List interface like ArrayList, 
Vector and LinkedList but its a thread-safe collection and it achieves its thread-safety in a slightly 
different way than Vector or other thread-safe collection class. As name suggest CopyOnWriteArrayList 
creates copy of underlying ArrayList with every mutation operation e.g. add or set. Normally CopyOnWriteArrayList 
is very expensive because it involves costly Array copy with every write operation but its very efficient if you 
have a List where Iteration outnumber mutation e.g. you mostly need to iterate the ArrayList and don't modify 
it too often. Iterator of CopyOnWriteArrayList is fail-safe and doesn't throw ConcurrentModificationException 
even if underlying CopyOnWriteArrayList is modified once Iteration begins because Iterator is operating on 
separate copy of ArrayList. Consequently all the updates made on CopyOnWriteArrayList is not available to Iterator. 

ArrayList and Vector are similar classes in many ways.

Both are index based and backed up by an array internally.
Both maintains the order of insertion and we can get the elements in the order of insertion.
The iterator implementations of ArrayList and Vector both are fail-fast by design.

ArrayList and Vector both allows null values and random access to element using index number.

These are the differences between ArrayList and Vector.
1) First and foremost difference between CopyOnWriteArrayList and ArrayList in Java is that CopyOnWriteArrayList 
is a thread-safe collection while ArrayList is not thread-safe and can not be used in multi-threaded environment.

2) Second difference between ArrayList and CopyOnWriteArrayList is that Iterator of ArrayList is fail-fast and
 throw ConcurrentModificationException once detect any modification in List once iteration begins but Iterator of CopyOnWriteArrayList is fail-safe and doesn't throw ConcurrentModificationException.

3) Third difference between CopyOnWriteArrayList vs ArrayList is that Iterator of former doesn't support remove operation while Iterator of later supports remove() operation.



*/

public class CopyOnArrayListDemo {

	public static void main(String args[]) {

		CopyOnWriteArrayList<String> threadSafeList = new CopyOnWriteArrayList<String>();
		
		threadSafeList.add("Java");
		threadSafeList.add("J2EE");
		threadSafeList.add("Collection");

		// add, remove operator is not supported by CopyOnWriteArrayList
		// iterator
		Iterator<String> failSafeIterator = threadSafeList.iterator();
		
		System.out.printf("Initial List : %s%n" , threadSafeList);
		
		int i=0;
		while (failSafeIterator.hasNext()) {
			System.out.printf("Read from CopyOnWriteArrayList : %s %n", failSafeIterator.next());
		
			threadSafeList.add("new Item " + i++);
		}
		
		System.out.printf("Modified List : %s%n " , threadSafeList);
		
		
		failSafeIterator = threadSafeList.iterator();
		while (failSafeIterator.hasNext()) {
			System.out.printf("Final List : %s %n", failSafeIterator.next());
			
		}
		
		failSafeIterator.remove(); // not supported in CopyOnWriteArrayList in Java
	}

}
