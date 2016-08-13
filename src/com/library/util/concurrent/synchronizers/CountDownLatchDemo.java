package com.library.util.concurrent.synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
Countdown latches

A countdown latch is a thread-synchronization construct that causes one 
or more threads to wait until a set of operations being performed by other threads finishes. 
It consists of a count and "cause a thread to wait until the count reaches zero" and "decrement the count" operations.

The java.util.concurrent.CountDownLatch class implements a countdown latch. Its CountDownLatch(int count) constructor initializes the
countdown latch to the specified count. A thread invokes the void await() method to wait until the count has reached zero 
(or the thread has been interrupted). Subsequent calls to await() for a zero count return immediately. 
A thread calls void countDown() to decrement the count.

Working with countdown latches

Countdown latches are useful for decomposing a problem into smaller pieces and giving a piece to a separate thread, as follows:

A main thread creates a countdown latch with a count of 1 that's used as a "starting gate" to start a group of worker threads simultaneously.
Each worker thread waits on the latch and the main thread decrements this latch to let all worker threads proceed.
The main thread waits on another countdown latch initialized to the number of worker threads.
When a worker thread completes, it decrements this count. After the count reaches zero (meaning that all worker threads have finished), 
the main thread proceeds and gathers the results.

*/
public class CountDownLatchDemo {
	
	private final static int THREAD_POOL = 3;
	
	public static void main(String... args) throws InterruptedException {
		
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal  = new CountDownLatch(THREAD_POOL);
		
		ExecutorService es = Executors.newFixedThreadPool(THREAD_POOL);
		
		for(int i=0; i< THREAD_POOL; i++ )			
			es.execute(new Worker(startSignal, doneSignal));
		
		System.out.printf("Main thread about to let go the worker threads.%n");		
		startSignal.countDown();
		
		System.out.printf("Main thread waiting on worker threads.%n");
		doneSignal.await();
				
		System.out.printf("Main threadterminating.%n");		
		es.shutdown();
	
	}
	
}


class Worker implements Runnable {

	private final static int PROCESS_COUNT = 4;
	
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	
	public Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
		super();
		this.startSignal = startSignal;
		this.doneSignal  = doneSignal;
	}

	@Override
	public void run() {

		try {
			
			String name = Thread.currentThread().getName();
			
			//wait for the main thread permits to start work
			startSignal.await();
			
			for (int i=0; i < PROCESS_COUNT; i++) {
				
				System.out.printf("%s working %n", name);
				
				try {
					
					Thread.sleep((int)Math.random()*300);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
			System.out.printf("%s finished %n", name);
			
			doneSignal.countDown();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
}