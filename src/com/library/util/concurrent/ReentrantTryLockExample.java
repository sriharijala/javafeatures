package com.library.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
 * Try Lock without blocking on getting the lock, it will timeout after a defined period
 */
public class ReentrantTryLockExample {

	private final ReentrantLock lock = new ReentrantLock();
	private int count = 0;

	// Locking using Lock and ReentrantLock
	public int getCount(int delay) {

		boolean lockAcquired = false;
		try {
			
			//how long the thread to wait for lock example here is set to 1 sec
			lockAcquired = lock.tryLock(1, TimeUnit.SECONDS);

			// if lock acquired in 1 seconds do something
			if (lockAcquired) {

				System.out.println(Thread.currentThread().getName() + " gets Count: " + count);
				
				Thread.sleep(delay);
				
				return count++;
				
			} else {
				
				throw new RuntimeException(Thread.currentThread().getName() + " Unable to acquire lock");
				
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block

			System.out.println(Thread.currentThread().getName() + " Could not acquire lock");

			e.printStackTrace();

		} finally {
			
			if (lockAcquired)
				lock.unlock();
		}

		return -1;
	}

	public static void main(String args[]) {

		final ReentrantTryLockExample counter = new ReentrantTryLockExample();

		Thread t1 = new Thread("t1") {

			@Override
			public void run() {
				
				int i =0;
				while (i < 6) {
					
					try {
						i = counter.getCount(500);
						
					} catch (Exception e) {
						
						e.printStackTrace();
					}

				}
				
			}
		};

		Thread t2 = new Thread("t2") {

			@Override
			public void run() {
				
				int i =0;
				while (i < 6) {
					
					try {
						i = counter.getCount(500);
						
					} catch (Exception e) {
					
						e.printStackTrace();
					}

				}
			}
		};

		t1.start();
		t2.start();

	}

}
