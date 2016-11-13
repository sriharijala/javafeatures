package com.library.util.concurrent.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/*
Producer consumer pattern is every where in real life and depict coordination and collaboration. 
Like one person is preparing food (Producer) while other one is serving food (Consumer), both will 
use shared table for putting food plates and taking food plates. Producer which is the person preparing 
food will wait if table is full and Consumer (Person who is serving food) will wait if table is empty.
table is a shared object here. On Java library Executor framework itself implement Producer Consumer 
design pattern be separating responsibility of addition and execution of task.


Benefit of Producer Consumer Pattern

Its indeed a useful design pattern and used most commonly while writing multi-threaded or concurrent code. here
is few of its benefit:

1) Producer Consumer Pattern simple development. you can Code Producer and Consumer independently and Concurrently, they just need to know shared object.

2) Producer doesn't need to know about who is consumer or how many consumers are there. Same is true with Consumer.

3) Producer and Consumer can work with different speed. There is no risk of Consumer consuming half-baked item.
In fact by monitoring consumer speed one can introduce more consumer for better utilization.

4) Separating producer and Consumer functionality result in more clean, readable and manageable code.

*/

public class BlockingQueueDemo {
	
	public static void main(String... args) {
		
		BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();
		
		ExecutorService ex = Executors.newFixedThreadPool(3);
				
		ex.execute(new Producer(blockingQueue));
		ex.execute(new Consumer(blockingQueue));
		ex.execute(new Consumer(blockingQueue));
		ex.shutdown();
				
	}
	
}

class Producer implements Runnable {

	BlockingQueue<String> blockingQueue;

	
	public Producer(BlockingQueue<String> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}


	@Override
	public void run() {

		String threadName = Thread.currentThread().getName();
		
		for(int i=0; i< 10; i++) {
			
			try {
				
				String message = "Producer : "  + i;
				
				System.out.printf("%s - Producer sending : %s%n", threadName, message);
				
				blockingQueue.put(message);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	
}

class Consumer implements Runnable {
	
	BlockingQueue<String> blockingQueue;

	public Consumer(BlockingQueue<String> blockingQueue) {
		super();
		this.blockingQueue = blockingQueue;
	}

	@Override
	public void run() {
		
		String threadName = Thread.currentThread().getName();
		
		while(true) {
			
			try {
				
				String receivedMessage = blockingQueue.take();
				
				System.out.printf("%s - Consumer got: %s%n", threadName, receivedMessage);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}
	
	
}