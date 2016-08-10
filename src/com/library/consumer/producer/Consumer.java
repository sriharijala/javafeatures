package com.library.consumer.producer;

import java.util.Random;

/*
The consumer thread, defined in Consumer, simply retrieves the messages and prints them out, 
until it retrieves the "DONE" string. This thread also pauses for random intervals.
*/

public class Consumer implements Runnable {
	
	private Drop drop;

	public Consumer(Drop drop) {
		this.drop = drop;
	}

	public void run() {
		
		String currentThreadName = Thread.currentThread().getName() + " ";
		
		Random random = new Random();
		
		for (String message = drop.take(); !message.contains("DONE"); message = drop.take()) {
			
			System.out.format(currentThreadName + "MESSAGE RECEIVED: %s%n", message);
			
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {
			}
			
		}
		
		System.out.println(currentThreadName + "Consumer is all set!");
	}
}