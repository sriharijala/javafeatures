package com.library.util.concurrent.locks;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
The traditional way of providing synchronization in multi-threaded environment was to use the synchronized keyword. 
However, the synchronized keyword is considered rather rigid under certain circumstances.For example,
if a thread is already executing inside a synchronized block and another thread tries to enter the block, 
it has to wait until the currently running thread exits the block. As long as the thread does not enter 
the synchronized block it remains blocked and cannot be interrupted.
Any thread can acquire the lock once the synchronized thread is exited by a currently running thread. 
This may lead to starvation of some(unlucky?) thread.
Synchronized blocks don’t offer any mechanism to query the status of “waiting queue” of threads for a particular resource.
The synchronized block have to be present within the same method. A synchronized block cannot start in one
method and end in another.

ReentrantLock to the Rescue!

While every application does not need the flexibility discussed above, it is certainly worth looking into how 
we can design a more flexible application using the ReentrantLock Class.
The ReentrantLock was provided in the java.util.concurrent.locks package since JDK 1.5, which provides extrinsic 
locking mechanism.
ReentrantLock Class provides a constructor that optionally takes a boolean parameter fair. When more than one thread 
are competing for the same lock, the lock favors granting access to the longest waiting thread.
(The reader should not misunderstand this as a means to guarantee fairness in Thread scheduling.)
ReentrantLock Class provides a number of ways to acquire locks. The tryLock() and the
tryLock(long timeout, TimeUnit unit) methods provide means for the threads to time-out while waiting for a 
lock instead of indefinitely waiting till lock is acquired.

*/

public class ReentrantLockDemo2<E>
{
	private final Lock lock = new ReentrantLock();

	private final List<E> list = new ArrayList<E>();

	private static int i = 0;

	public void set(E o)
	{
			lock.lock();

			try
			{
				i++;
				list.add(o);
				System.out.println("Adding element by thread"+Thread.currentThread().getName());
			}
			finally
			{
				lock.unlock();
			}
	}

	public static void main(String[] args)
	{

			final ReentrantLockDemo2<String> lockExample = new ReentrantLockDemo2<String>();
			Runnable syncThread = new Runnable()
			{

				@Override
				public void run()
					{
						while (i < 6)
						{
							lockExample.set(String.valueOf(i));
									
							try
							{
								Thread.sleep(100);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
			};
			Runnable lockingThread = new Runnable()
			{

				@Override
				public void run()
					{
						while (i < 6)
						{
							lockExample.set(String.valueOf(i));
							try
							{
								Thread.sleep(100);
							}
							catch (InterruptedException e)
							{
								e.printStackTrace();
							}
						}
					}
			};
			Thread t1 = new Thread(syncThread, "syncThread");
			Thread t2 = new Thread(lockingThread, "lockingThread");
			t1.start();
			t2.start();
	}
}
