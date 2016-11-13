package com.library.data.structures;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Algorithms
{
   @Test
   public void BinarySearchTest()
   {
	   int [] x = { -5, 12, 15, 20, 30, 72, 456 };
	   
	   assertEquals(5, BinarySearchDemo(x, 72));
	   assertEquals(72, x[BinarySearchDemo(x, 72)]);
      
	   assertEquals(-1, BinarySearchDemo(x, 99));
	  
   }

   private int BinarySearchDemo( int [] x, int srch) {
	
	
      int loIndex = 0;
      int hiIndex = x.length - 1;
      int midIndex= -1;
      while (loIndex <= hiIndex)
      {
         midIndex = (loIndex + hiIndex) / 2;
         if (srch > x [midIndex])
             loIndex = midIndex + 1;
         else if (srch < x [midIndex])
             hiIndex = midIndex - 1;
         else
             break;
      }
      
      if (loIndex > hiIndex)
          return -1;
      else
         return midIndex;
   }
   
   @Test
   public void BinarySortTest() {
	   
	   int [] x = { 20, 15, 12, 30, -5, 72, 456 };
	   int [] expected = {-5, 12, 15, 20, 30, 72, 456};
	   
	   BinarySortDemo(x);
	   
	   assertEquals(x, expected);
	   
	   for (int i = 0; i <  x.length; i++)
	        System.out.println (x [i]);
   }
   
   private int [] BinarySortDemo(int [] x){
	   
	   int i, pass;
	   
	   for (pass = 0; pass <= x.length - 2; pass++)
	        for (i = 0; i <= x.length - pass - 2; i++)
	             if (x [i] >  x [i + 1])
	             {
	                 int temp = x [i];
	                 x [i] = x [i + 1];
	                 x [i + 1] = temp;
	             }
	   
	   return x;
   }
}