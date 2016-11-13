package com.library.java.util;

import java.util.EnumSet;
import java.util.Set;

/*
In this example, we have used basic colours RED, GREEN and BLUE to create a custom colour by using EnumSet. 

1) EnumSet is a special Set implementation, only applicable for Enums in Java, but you can only store instances of the single enum type. Adding an instance of different enum will result in compile time error, as EnumSet provide type-safety.

2) EnumSet internal representation is extremely efficient and represented as bit vectors. Library itself chooses one of two implementations available depending upon the size of a key universe. RegularEnumSet has chosen if a number of instances are less than 64, otherwise JumboEnumSet is used.

3) As described in Effective Java, EnumSet can be safely used as a type-safe alternative of traditional int based "bit flags". EnumSet provides much-needed readability, without compromising performance.

4) Iterator returned by EnumSet traverse the elements in their natural order, i.e. the order on which enum constants are declared, or the order returned by ordinal() method.

5) An EnumSet iterator is weakly consistent and never throws ConcurrentModificationException, and may or may not show the effect of any modification to the Set while the iteration is in progress.

6) EnumSet is also not synchronized in Java. Though if you need, you can make EnumSet synchronized similar to other collection by using utility methods from Collections class. Here is how to do that :

Set<YourEnum> s = Collections.synchronizedSet(EnumSet.noneOf(YourEnum.class));

7) EnumSet is an abstract class, which means you cannot create its instance using new() operator. This is actually carefully thought to provide special implementation, and that's why EnumSet provides several static factory methods for creating instance e.g. noneOf() returns an empty EnumSet with specified enum type, EnumSet.of(....) returns Set of specified enum constants and allOf() method creates an enum set containing all elements of specified enum.

8) EnumSet also provides methods like complementOf(EnumSet es) and copyOf(EnumSet es) to create EnumSet from other EnumSet, coplementOf() returns enum set with containing all the elements of this type that are not contained in the specified set.

*/
public class EnumSetDemo {

	private enum Color {
		
		RED(255, 0, 0), GREEN(0, 255, 0), BLUE(0, 0, 255);
		
		private int r;
		private int g;
		private int b;

		private Color(int r, int g, int b) {
			this.r = r;
			this.g = g;
			this.b = b;
		}

		public int getR() {
			return r;
		}

		public int getG() {
			return g;
		}

		public int getB() {
			return b;
		}
	}

	public static void main(String args[]) {
		
		// this will draw line in yellow color
		EnumSet<Color> yellow = EnumSet.of(Color.RED, Color.GREEN);
		drawLine(yellow);
		
		// RED + GREEN + BLUE = WHITE
		EnumSet<Color> white = EnumSet.of(Color.RED, Color.GREEN, Color.BLUE);
		drawLine(white);
		
		// RED + BLUE = PINK
		EnumSet<Color> pink = EnumSet.of(Color.RED, Color.BLUE);
		drawLine(pink);
		
		//copy of 
		EnumSet<Color> copiedColor = EnumSet.copyOf(white);
		System.out.println("Copied colors of copiedColor are : " + copiedColor);
		
		//copy of 
		EnumSet<Color> complement = EnumSet.complementOf(yellow);
		System.out.println("Copied colors of complement are : " + complement);
		
 	}

	public static void drawLine(Set<Color> colors) {
		
		System.out.println("Requested Colors to draw lines : " + colors);
		
		for (Color c : colors) {
			System.out.println("drawing line in color : " + c);
		}
	}
}
