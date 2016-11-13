package com.library.util.concurrent.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/***
 * 
 * EWxample of -  “Communicating Sequential Processes”
 * 
 * a BlockingQueue – it represents a FIFO (first in, first out) queue of values, is itself thread-safe, 
 * and allows for blocking on both producing and consuming processes. If you give your threads the same queue, 
 * they can pass messages between each other in a safe way.
 * 
 * For example, let’s say we have 3 processes that each generate a unique number every so often, and run in parallel. 
 * However, we also want to sum all the numbers and print them as they come in.
 * 
 * Example Ref: http://coderscampus.com/java-multithreading-java-util-concurrent/
 * 
 * @author Srihari
 *
 */
public class CSPUsingBlockingQueue {

	public static void main(String[] args) {
		
		// First we create a queue with capacity 1. This means that if there  
        // is already an item in the queue waiting to be consumed, any other  
        // threads wanting to add an item are blocked until it is consumed.  
        // Also, if the consumer tries to get an element out of the queue, but  
        // there aren't any items, the consumer will block.
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
		
		// We create an executor service, which runs our number generators and  
        // our summing process. They all get the same queue so they can  
        // communicate with each other via messages. 
		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		
		// The NumberGenerators generate different numbers, and wait for different  
        // periods of time. 
		threadPool.submit(new NumberGenerator(7, 100, queue));
		threadPool.submit(new NumberGenerator(8, 120, queue));
		threadPool.submit(new NumberGenerator(4, 110, queue));
		
		// The SummingProcess returns the final sum, so we can get a Future  
        // that represents the answer at a future time and wait for it to finish.  
		Future totalSum = threadPool.submit(new SummingProcess(queue));

		try {
			
			// Waits for the SummingProcess to finish, after it's sum is > 100  
	        Integer sumResult = (Integer) totalSum.get();  
	  
	        System.out.println("Done! Sum was " + sumResult);  
	  
	       
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			 // Interrupts the other threads for shutdown. You can also shutdown  
	        // threads more gracefully with shutdown() and awaitTermination(),  
	        // but here we just want them to exit immediately.  
	        threadPool.shutdownNow(); 
	        
		}
	}
	
	private static final class NumberGenerator implements Runnable {
		
		private final int numberToGenerate;
		private final int sleepPeriod;
		private final BlockingQueue<Integer> queue;
		
			/**
		 * @param numberToGenerate
		 * @param sleepPeriod
		 * @param queue
		 */
		public NumberGenerator(int numberToGenerate, int sleepPeriod, BlockingQueue<Integer> queue) {
			super();
			this.numberToGenerate = numberToGenerate;
			this.sleepPeriod = sleepPeriod;
			this.queue = queue;
		}

		@Override
		public void run() {

			try {
				
				// Produce numbers indefinitely 
				while (true) {

					Thread.sleep(this.sleepPeriod);
					
					// puts the integer into the queue, waiting as necessary for  
                    // there to be space. 
					queue.put(this.numberToGenerate);
				}

			} catch (InterruptedException e) {

				// Allow our thread to be interrupted
				Thread.currentThread().interrupt();
			}		
		}
		
	}
	
	private static final class SummingProcess implements Callable {

		private final BlockingQueue<Integer> queue;
		
		/**
		 * @param queue
		 */
		public SummingProcess(BlockingQueue<Integer> queue) {
			super();
			this.queue = queue;
		}

		@Override
		public Integer call() throws Exception {
			
			try {
				
				Integer sum = 0;
				while(sum < 100) {
					
					// take() gets the next item from the queue, waiting as necessary  
                    // for there to be elements. 
					Integer nextInteger = queue.take();
					
					sum += nextInteger;
					
					System.out.println("Got number : " + nextInteger + " total sum is :" + sum);
					
					
				}
				return sum;
				
			} catch (InterruptedException e) {

				// Allow our thread to be interrupted
				Thread.currentThread().interrupt();
				return -1; // this will never run, but the compiler needs it  
			}
			
		}
		
	}
}
