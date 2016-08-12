package com.library.util.concurrent.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
1) Callable is a SAM type interface, so it can be used in lambda expression.

2) Callable has just one method call() which holds all the code needs to executed asynchronously.

3) In Runnable interface, there was no way to return the result of computation or throw checked exception but with Callable you can both return a value and can throw checked exception.

4) You can use get() method of Future to retrieve result once computation is done. You can check if computation is finished or not by using isDone() method.

5) You can cancel the computation by using Future.cancel() method.

6) get() is a blocking call and it blocks until computation is completed.

*/
		
public class DemoCallableAndFuture {
	

	public static void main(String... args) throws InterruptedException, ExecutionException {
		
		//create thread pool which executes tasks
		ExecutorService fixedPool = Executors.newFixedThreadPool(3);
		
		ExecutorService singleThread = Executors.newSingleThreadExecutor();
	
		long pooledTime = factorialService(fixedPool);
		long singleThreadTime = factorialService(singleThread);
		
		System.out.println( "Single Thread Run time (ms) : " + singleThreadTime);
		System.out.println( "Fixed Pooled Thread Run time (ms) : " + pooledTime);
		System.out.println("Performance improvement (ms): " + (singleThreadTime - pooledTime));
	
	}

	private static long factorialService(ExecutorService executorServ) throws InterruptedException, ExecutionException {
		
		long startTime = System.currentTimeMillis();
		 
		Future<Long> fact10 = executorServ.submit(new FactorialCalculator(10));
		Future<Long> fact20 = executorServ.submit(new FactorialCalculator(20));
		Future<Long> fact30 = executorServ.submit(new FactorialCalculator(30));
		
		
		long result10 =  fact10.get();
		long result20 =  fact20.get();
		long result30 =  fact30.get();
		
		long endTime = System.currentTimeMillis();
		
		System.out.println( "Result10 " + result10);
		System.out.println( "Result20 " + result20);
		System.out.println( "Result30 " + result30);
		
		long totalTime = endTime - startTime;
		
		
		
		return totalTime;
	}
	
	

}

/***
 * Calculate facgtorial of a number. It implements Callable interface and can be run by ExecutorService
 * @author Srihari
 *
 */
class FactorialCalculator implements Callable<Long> {

	private int number;
	
	
	public FactorialCalculator(int number) {
		super();
		this.number = number;
	}


	@Override
	public Long call() throws Exception {
		// TODO Auto-generated method stub
		long result = findFactorial(number);
		
		System.out.println(Thread.currentThread().getName() + " " +  System.currentTimeMillis());
		
		return result;
	}
	
	private long findFactorial(int n) throws InterruptedException {
		
		long result = 1;
		
		while( n != 0) {
			
			result = n * result;
			
			n--;
			
			Thread.sleep(100);
		}
		
		return result;
		
	}
}