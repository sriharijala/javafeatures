package com.library.concurrent.fork.join;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
The Fork/Join Framework in Java 7 is designed for work that can be broken down into smaller tasks 
and the results of those tasks combined to produce the final result. In general, classes that use 
the Fork/Join Framework follow the following simple algorithm:

// pseudocode
Result solve(Problem problem) {
  if (problem.size < SEQUENTIAL_THRESHOLD)
    return solveSequentially(problem);
  else {
    Result left, right;
    INVOKE-IN-PARALLEL {
      left = solve(extractLeftHalf(problem));
      right = solve(extractRightHalf(problem));
    }
    return combine(left, right);
  }
}

*/

public class MaximumFinder extends RecursiveTask<Integer> {

	private static int SEQUENTIAL_THRESHOLD = 5;

	private final int[] data;
	private final int start;
	private final int end;

	public MaximumFinder(int[] data, int start, int end) {
		this.data = data;
		this.start = start;
		this.end = end;
	}

	public MaximumFinder(int[] data) {
		this(data, 0, data.length);
	}

	@Override
	protected Integer compute() {
		
		final int length = end - start;
		
		if (length < SEQUENTIAL_THRESHOLD) {
			return computeDirectly();
		}
		
		final int split = length / 2;
		
		final MaximumFinder left = new MaximumFinder(data, start, start + split);
		left.fork();
		
		final MaximumFinder right = new MaximumFinder(data, start + split, end);
		
		return Math.max(right.compute(), left.join());
	}

	/*
	 * Compute the max number directly.
	 */
	private Integer computeDirectly() {

		System.out.println(Thread.currentThread() + " computing: " + start + " to " + end);

		int max = Integer.MIN_VALUE;
		for (int i = start; i < end; i++) {
			if (data[i] > max) {
				max = data[i];
			}
		}
		return max;
	}

	public static void main(String[] args) {
		
		// create a random data set
		final int[] data = new int[1000];
		final Random random = new Random();
		for (int i = 0; i < data.length; i++) {
			data[i] = random.nextInt(100);
		}

		SEQUENTIAL_THRESHOLD = 5;
		
		long startTime = System.currentTimeMillis();
		
		// submit the task to the pool
		ForkJoinPool pool = new ForkJoinPool(4);
		MaximumFinder finder = new MaximumFinder(data);
		System.out.println(pool.invoke(finder));
		
		System.out.format("\nSEQUENTIAL_THRESHOLD = %s Total time is: %s", SEQUENTIAL_THRESHOLD, System.currentTimeMillis() - startTime);
		
		SEQUENTIAL_THRESHOLD = 1000;
		
		startTime = System.currentTimeMillis();
		
		// submit the task to the pool
		pool = new ForkJoinPool(4);
		finder = new MaximumFinder(data);
		System.out.println(pool.invoke(finder));
		
		System.out.format("\nSEQUENTIAL_THRESHOLD = %s Total time is: %s", SEQUENTIAL_THRESHOLD, System.currentTimeMillis() - startTime);
		
		
	}

}
