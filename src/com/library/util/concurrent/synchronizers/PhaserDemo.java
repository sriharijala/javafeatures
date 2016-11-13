package com.library.util.concurrent.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

/*
Phasers

A phaser is a thread-synchronization construct that's similar to a cyclic barrier in that it lets 
a group of threads wait on a barrier and then proceed after the last thread arrives. It also offers 
the equivalent of a barrier action. However, a phaser is more flexible.

Unlike a cyclic barrier, which coordinates a fixed number of threads, a phaser can coordinate a variable 
number of threads, which can register at any time. To implement this capability, a phaser takes advantage 
of phases and phase numbers.

A phase is the phaser's current state, and this state is identified by an integer-based phase number. 
When the last of the registered threads arrives at the phaser barrier, a phaser advances to the next phase 
and increments its phase number by 1.

The java.util.concurrent.Phaser class implements a phaser. Because this class is thoroughly described 
in its Javadoc, I'll point out only a few constructors and methods:

The Phaser(int threads) constructor creates a phaser that initially coordinates nthreads threads 
(which have yet to arrive at the phaser barrier) and whose phase number is initially set to 0.
The int register() method adds a new unarrived thread to this phaser and returns the phase number 
to which the arrival applies. This number is known as the arrival phase number.
The int arriveAndAwaitAdvance() method records arrival and waits for the phaser to advance 
(which happens after the other threads have arrived). It returns the phase number to which the arrival applies.
The int arriveAndDeregister() method arrives at this phaser and deregisters from it without 
waiting for others to arrive, reducing the number of threads required to advance in future phases.

This example shows how to use Phaser instead of CountDownLatch to control a one-shot action serving a variable
 number of threads.
The application creates a pair of runnable tasks that each report the time (in milliseconds relative to the Unix epoch) 
at which its starts to run. 

Thread-0 running at 1366315297635
Thread-1 running at 1366315297635

As you would expect from countdown latch behavior, both threads start running at (in this case) the same time 
even though a thread may have been delayed by as much as 349 milliseconds thanks to the presence of Thread.sleep().

Comment out phaser.arriveAndAwaitAdvance(); // await all creation and you should now observe the threads starting 
at radically different times, as illustrated below:

Thread-1 running at 1366315428871
Thread-0 running at 1366315429100
*/

public class PhaserDemo {

	public static void main(String... args) {
		
		List<Runnable> tasks = new ArrayList<Runnable>();
		
		tasks.add(new PhaserWorker());
		tasks.add(new PhaserWorker());
		
		runTasks(tasks);
	}
	
	static void runTasks(List<Runnable> tasks) {
		
		final Phaser phaser = new Phaser(1);
		
		//create and start Threads
		for (final Runnable task: tasks ) {
			
			new Thread() {
				
				@Override
				public void run() {
					
					
					String name = Thread.currentThread().getName();
					
					try {
						Thread.sleep(50 + (int)Math.random()*300);
					} catch (InterruptedException e) {
						
						System.out.printf("%s interrupted thread%n", name);
						e.printStackTrace();
					}
					
					phaser.arriveAndAwaitAdvance(); //wait for creation of all threads 
					
					task.run();
				}
				
			}.start();
		}
		
		  // allow threads to start and deregister self
		phaser.arriveAndDeregister();
	}
}

class PhaserWorker implements Runnable {

	@Override
	public void run() {
		
		String name = Thread.currentThread().getName();	
		
		System.out.printf("%s running at %s%n", name, System.currentTimeMillis());
		
	}
}

