package com.library.java.util;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

/*
Java provides us Queue interface, where we can keep and handle elements before processing. 
Except the methods that Collection provides, it also supports some basic operations in order 
to simulate the classic queue structure. Each of these operations exists in two forms:
if a method fails, an exception is thrown. This form includes add(), remove() and element() methods.
if a method fails, a special value is returned (null or false). This form contains offer(), poll() and peek() operations.
 
 
It is worth to mention that FIFO (first-in-first-out) is the most common way to order the elements 
in the Queue. In this example we are going to show the operations in order to handle a queue.

1. Explanation of Queue operations
First of all, lets see analytically the basic operations that exist in two different forms.

1.1. Throws exception
add(E e): inserts the element e to the tail of the queue. If there is no space available because of capacity restrictions, IllegalStateException is thrown.
remove(): removes and returns the head (the first element) of the queue. If the queue is empty, NoSuchElementException is thrown.
element(): just returns the head of the queue, without removing it. If the queue is empty, again NoSuchElementException is thrown.

1.2. Returns special value
offer(E e): adds the element e to the tail of the queue. If the insertion is successful the method returns true, otherwise it returns false. Generally, if there are capacity bounds, it is preferred to use add method instead.
poll(): like remove() function, it retrieves and removes the head of the queue. The only difference from remove() is that poll() operation returns null when the queue is empty.
peek(): just like element() operation it retrieves and returns the head of the queue, without removing it. In this situation when the queue is empty, it returns null.
*/

public class QueueDemo {

	public static void main(String[] args) {

		Queue<String> myQueue = new LinkedList<String>();

		// add elements in the queue using offer() - return true/false
		myQueue.offer("Monday");
		myQueue.offer("Tuesday");
		boolean flag = myQueue.offer("Wednesday");

		System.out.println("Wednesday inserted successfully? " + flag);

		// add more elements using add() - throws IllegalStateException
		try {
			myQueue.add("Thursday");
			myQueue.add("Friday");
			myQueue.add("Weekend");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		System.out.println("Pick the head of the queue: " + myQueue.peek());

		String head = null;
		try {
			// remove head - remove()
			head = myQueue.remove();
			System.out.print("1) Push out " + head + " from the queue ");
			System.out.println("and the new head is now: " + myQueue.element());
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}

		// remove the head - poll()
		head = myQueue.poll();
		System.out.print("2) Push out " + head + " from the queue");
		System.out.println("and the new head is now: " + myQueue.peek());

		// find out if the queue contains an object
		System.out.println("Does the queue contain 'Weekend'? " + myQueue.contains("Weekend"));
		System.out.println("Does the queue contain 'Monday'? " + myQueue.contains("Monday"));
	}

}
