package com.library.java.util;

import java.util.PriorityQueue;
import java.util.Queue;

/*
PriorityQueue is an unbounded Queue implementation in Java, which is based on priority heap. 
PriorityQueue allows you to keep elements in a particular order, according to there natural order or 
custom order defined by Comparator interface in Java. Head of priority queue data structure will 
always contain least element with respect to specified ordering. For example, in this post, we will
create a PriorityQueue of Items, which are ordered based upon there price, this will allow us to
process Items, starting from lowest price. Priority queue is also very useful in implementing Dijkstra 
algorithm in Java. You can use to PriorityQueue to keep unsettled nodes for processing. One of the 
key thing to remember about PriorityQueue in Java is that it's Iterator doesn't guarantee any order, 
if you want to traverse in ordered fashion, better use Arrays.sort(pq.toArray()) method. 
PriorityQueue is also not synchronized, which means can not be shared safely between multiple threads, 
instead it's concurrent counterpart PriorityBlockingQueue is thread-safe and should be used in
multithreaded environment. Priority queue provides O(log(n)) time performance for common enqueing and 
dequeing methods e.g. offer(), poll() and add(), but constant time for retrieval methods e.g. peek() and element().
*/

public class PriorityQueueDemo {
	public static void main(String args[]) {

        Queue<Item> items = new PriorityQueue<Item>();
        items.add(new Item("IPone", 900));
        items.add(new Item("IPad", 1200));
        items.add(new Item("Xbox", 300));
        items.add(new Item("Watch", 200));

        System.out.println("Order of items in PriorityQueue");
        System.out.println(items);
      
        System.out.println("Element consumed from head of the PriorityQueue : " + items.poll());
        System.out.println(items);
      
        System.out.println("Element consumed from head of the PriorityQueue : " + items.poll());
        System.out.println(items);
      
        System.out.println("Element consumed from head of the PriorityQueue : " + items.poll());
        System.out.println(items);
      
        //items.add(null); // null elements not allowed in PriorityQueue - NullPointerException

    }

    private static class Item implements Comparable<Item> {

        private String name;
        private int price;

        public Item(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Item other = (Item) obj;
            if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
                return false;
            }
            if (this.price != other.price) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash =  hash + (this.name != null ? this.name.hashCode() : 0);
            hash =  hash + this.price;
            return hash;
        }

        @Override
        public int compareTo(Item i) {
            if (this.price == i.price) {
                return this.name.compareTo(i.name);
            }

            return this.price - i.price;
        }

        @Override
        public String toString() {
            return String.format("%s: $%d", name, price);
        }      
      
    }
}


