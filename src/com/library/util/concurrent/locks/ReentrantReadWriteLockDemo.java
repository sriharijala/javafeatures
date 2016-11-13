package com.library.util.concurrent.locks;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;



/**
 * User preferences, using a read-write lock.
 * 
 * <P>
 * The context: preference information is read in upon startup. The config data
 * is 'read-mostly': usually, a caller simply reads the information. It gets
 * updated only occasionally.
 * 
 * <P>
 * Using a read-write lock means that multiple readers can access the same data
 * simultaneously. If a single writer gets the lock, however, then all other
 * callers (either reader or writer) will block until the lock is released by
 * the writer.
 * 
 * <P>
 * (In practice, Brian Goetz reports that the implementation of
 * ConcurrentHashMap is so good that it would likely suffice in many cases,
 * instead of a read-write lock.)
 */
public class ReentrantReadWriteLockDemo {
	
	public static void main(String... args){
		
		Preferences pref = new Preferences();
		ExecutorService ex = Executors.newFixedThreadPool(4);
		
		Runnable readThread = new  Runnable() {
			
			public void run() {
				
				while(true) {
					String key = "" + (int)( Math.random()*(10-1) );
					
					String value = pref.fetch(key);
					
					System.out.printf( "%s %s reading key:%s value: %s%n", Thread.currentThread().getName(), System.currentTimeMillis(), key, value);
				
					try {
						Thread.currentThread().sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Runnable writeThread = new  Runnable() {
			
			public void run() {
				
				while(true) {
					
					String key = "" + (int) (Math.random()*(10-1));
					String value = "" + (int) (Math.random()*(4.0-1));
					
					System.out.printf( "%s %s writing key:%s value: %s%n", Thread.currentThread().getName(), System.currentTimeMillis(), key, value);
					
					pref.update(key, value);
					
					try {
						Thread.currentThread().sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		ex.execute(writeThread);
		ex.execute(readThread);
		ex.execute(readThread);
		ex.execute(readThread);
		
		
	}
	
}


final class Preferences {
	// PRIVATE

	/** Holds the preferences as simple name-value pairs of Strings. */
	private Map<String, String> fPreferences = new LinkedHashMap<>();
	private final ReentrantReadWriteLock fLock = new ReentrantReadWriteLock();
	private final Lock fReadLock = fLock.readLock();
	private final Lock fWriteLock = fLock.writeLock();
	
	

	public Preferences() {
		super();
		
		for(int i=0; i < 10; i++)
			this.fPreferences.put(""+i,""+i);
	}

	/** Fetch a setting - this is the more common operation. */
	public String fetch(String aName) {
		String result = "";
		fReadLock.lock();
		try {
			result = fPreferences.get(aName);
		} finally {
			fReadLock.unlock();
		}
		return result;
	}

	/** Change a setting - this is the less common operation. */
	public void update(String aName, String aValue) {
		fWriteLock.lock();
		try {
			fPreferences.put(aName, aValue);
		} finally {
			fWriteLock.unlock();
		}
	}

}
