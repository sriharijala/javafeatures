package com.library.util.concurrent.collections.delayedQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

import org.junit.Test;

public class DelayedQueueDemo {

	
	public static void main(String[] args) {

		// Creates an instance of blocking queue using the DelayQueue.
		BlockingQueue queue = new DelayQueue();
		
		// Starting DelayQueue Producer to push some delayed objects to the queue 
		new DelayedQueueProducer(queue).start();
		
		// Starting DelayQueue Consumer to take the expired delayed objects from the queue
		new DelayedQueueConsumer("Consumer Thread-1", queue).start();
	}
	
}
