package com.library.general;

import java.util.ArrayList;
import java.util.Comparator;

/*
Comparator interface is used to provide different algorithms for sorting and we can chose the comparator we want to use to sort the given
collection of objects.
*/

public class ComparatorDemo {

	public static void main(String... args) {

		// Create the collection of people:
		ArrayList<Person> people = new ArrayList<>();
		people.add(new Person("Dan", 4));
		people.add(new Person("Andi", 2));
		people.add(new Person("Bob", 42));
		people.add(new Person("Debby", 3));
		people.add(new Person("Bob", 72));
		people.add(new Person("Barry", 20));
		people.add(new Person("Cathy", 40));
		people.add(new Person("Bob", 40));
		people.add(new Person("Barry", 50));

		System.out.printf("List of people :%s%n", people);

		// compare by name and then age
		Comparator<Person> comparator = Comparator.comparing((Person p) -> p.name).thenComparing(p -> p.age);

		// Sort the stream:
		people.sort(comparator);

		System.out.printf("List of people sorted : %s%n", people);

		// compare by age and then name 
		comparator = Comparator.comparing((Person p) -> p.age).thenComparing(p -> p.name);

		// Sort the stream:
		people.sort(comparator);

		System.out.printf("List of people sorted : %s%n", people);

	}

}

/**
 * A person in our system.
 */
class Person {
	/**
	 * Creates a new person.
	 * 
	 * @param name
	 *            The name of the person.
	 * @param age
	 *            The age of the person.
	 */
	public Person(String name, int age) {
		this.age = age;
		this.name = name;
	}

	/**
	 * The name of the person.
	 */
	public String name;

	/**
	 * The age of the person.
	 */
	public int age;

	@Override
	public String toString() {
		if (name == null)
			return super.toString();
		else
			return String.format("%s : %d", this.name, this.age);
	}
}