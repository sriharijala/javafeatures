package com.hackathon;

import java.util.Scanner;
import java.util.logging.Logger;

import org.junit.Test;


/***
 * This is test1
 * @author Srihari
 *
 */
public class Test1 {
	
	 /**
	 * Default constructor
	 */
	public Test1() {
		super();
	}

	/***
	 * test 
	 */
	@Test
	public void test() {
		 
        Scanner scan = new Scanner(System.in);
        int i = scan.nextInt();
        double d= scan.nextDouble();
        String s = scan.nextLine();

        // Write your code here.

        System.out.println("String: " + s);
        System.out.println("Double: " + d);
        System.out.println("Int: " + i);
    }

}
