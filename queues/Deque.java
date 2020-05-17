/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head = null;
    private Node<Item> tail = null;
    private int size;

    private static class Node<Item> {
        private Item val;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item val) {
            this.val = val;
        }
    }

    // construct an empty deque
    public Deque() {
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {

        validate(item);

        final Node<Item> newNode = new Node<>(item);
        newNode.next = this.head;
        if (this.head != null) {
            this.head.prev = newNode;
        }
        this.head = newNode;

        if (tail == null) {
            tail = newNode;
        }

        this.size++;
    }

    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    // add the item to the back
    public void addLast(Item item) {

        validate(item);

        final Node<Item> newNode = new Node<>(item);
        if (this.tail != null) {
            this.tail.next = newNode;
        }
        newNode.prev = this.tail;
        this.tail = newNode;

        if (head == null) {
            head = newNode;
        }

        this.size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {

        if (isEmpty()) throw new NoSuchElementException();

        final Node<Item> oldHead = this.head;
        final Item item = oldHead.val;
        this.head = oldHead.next;
        oldHead.next = null;
        if (this.head != null) {
            this.head.prev = null;
        }

        if (tail == oldHead) {
            tail = null;
        }

        this.size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {

        if (isEmpty()) throw new NoSuchElementException();

        final Node<Item> oldTail = this.tail;
        final Item item = oldTail.val;

        this.tail = oldTail.prev;
        if (this.tail != null) {
            this.tail.next = null;
        }
        oldTail.prev = null;

        if (head == oldTail) {
            head = null;
        }

        this.size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new MyIterator<>(this);
    }

    private static class MyIterator<T> implements Iterator<T> {

        private Node<T> currNode;

        public MyIterator(Deque<T> deque) {
            currNode = deque.head;
        }

        public boolean hasNext() {
            return currNode != null;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            final T val = currNode.val;
            currNode = currNode.next;
            return val;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final Deque<Integer> dequeInt = new Deque<>();

        dequeInt.addLast(1);
        dequeInt.addLast(2);
        dequeInt.addLast(3);
        dequeInt.addFirst(5);
        for (Integer integer : dequeInt) {
            System.out.println(integer);
        }

        System.out.println();

        System.out.println(dequeInt.size());

        System.out.println();

        final Iterator<Integer> iterator = dequeInt.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.hasNext());

        System.out.println();

        dequeInt.removeFirst();
        for (Integer integer : dequeInt) {
            System.out.println(integer);
        }

        System.out.println();

        dequeInt.removeLast();
        for (Integer integer : dequeInt) {
            System.out.println(integer);
        }

        System.out.println();

        dequeInt.removeFirst();
        dequeInt.removeFirst();
        for (Integer integer : dequeInt) {
            System.out.println(integer);
        }
        System.out.println(dequeInt.size());
    }
}
