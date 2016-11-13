package com.library.util.concurrent.synchronizers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 
 http://www.javaworld.com/article/2078809/java-concurrency/java-concurrency-java-101-the-next-generation-java-concurrency-without-the-pain-part-1.html?page=2
 
Cyclic barriers

A cyclic barrier is a thread-synchronization construct that lets a set of threads wait for each other to reach a common barrier point. The barrier is called cyclic because it can be re-used after the waiting threads are released.

A cyclic barrier is implemented by the java.lang.concurrent.CyclicBarrier class. This class provides the following constructors:

CyclicBarrier(int nthreads, Runnable barrierAction) causes a maximum of nthreads-1 threads to wait at the barrier. When one more thread arrives, it executes the nonnull barrierAction and then all threads proceed. This action is useful for updating shared state before any of the threads continue.
CyclicBarrier(int nthreads) is similar to the previous constructor except that no runnable is executed when the barrier is tripped.
Either constructor throws java.lang.IllegalArgumentException when the value passed to nthreads is less than 1.

CyclicBarrier declares an int await() method that typically causes the calling thread to wait unless the thread is the final thread. If so, and if a nonnull Runnable was passed to barrierAction, the final thread executes the runnable before the other threads continue.

await() throws InterruptedException when the thread that invoked this method is interrupted while waiting. This method throws BrokenBarrierException when another thread was interrupted while the invoking thread was waiting, the barrier was broken when await() was called, or the barrier action (when present) failed because an exception was thrown from the runnable's run() method.

Working with cyclic barriers

Cyclic barriers can be used to perform lengthy calculations by breaking them into smaller individual tasks (as demonstrated by CyclicBarrier's Javadoc example code). They're also used in multiplayer games that cannot start until the last player has joined, 
*/		
public class CyclicBarrierDemo {
	
	private static final int MAX_PALYERS = 3;
	
	public static void main(String... args) {
		
		
		Runnable barrierAction = new Runnable() {
			
			@Override
			public void run() {
				
				String name = Thread.currentThread().getName();
				
				System.out.printf("Thread %s executing barrier action. %n", name);
				
			}
		};
		
		final CyclicBarrier barrier = new CyclicBarrier(MAX_PALYERS, barrierAction);
		
		Runnable task = new Runnable() {
			
			@Override
			public void run() {
				
				String name = Thread.currentThread().getName();
				
				System.out.printf("Thread %s ready to join. %n", name);	
				
				try {
					
					barrier.await();
					
				} catch (InterruptedException | BrokenBarrierException e) {
					
					e.printStackTrace();
				}
				
				System.out.printf("Thread %s joined. %n", name);
			}
		};
		
	
		ExecutorService es = Executors.newFixedThreadPool(MAX_PALYERS);
		
		for (int i = 0; i < MAX_PALYERS; i++) 
			es.execute(task);
		
		es.shutdown();
	}
}
