/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private static final int INITIAL_CAPACITY = 2;
    private Object[] items;
    private int head;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = new Object[INITIAL_CAPACITY];
        head = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return head == -1;
    }

    // return the number of items on the randomized queue
    public int size() {
        return head + 1;
    }

    // add the item
    public void enqueue(Item item) {

        validate(item);

        if (head == items.length - 1) {
            resize(items.length * 2);
        }

        items[++head] = item;
    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty()) throw new NoSuchElementException();

        final int randIdx = StdRandom.uniform(head + 1);
        final Item item = (Item) items[randIdx];
        items[randIdx] = items[head];
        items[head] = null;
        head--;

        if (head * 4 == items.length - 1) {
            resize(items.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        final int randIdx = StdRandom.uniform(head + 1);
        final Item item = (Item) items[randIdx];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new MyIterator<>(this);
    }

    private void resize(int newSize) {
        final Object[] newItems = new Object[newSize];
        System.arraycopy(items, 0, newItems, 0, head + 1);
        items = newItems;
    }

    private void validate(Item item) {
        if (item == null) throw new IllegalArgumentException();
    }

    private static class MyIterator<T> implements Iterator<T> {

        private final Object[] items;
        private final int[] indices;
        private int i = 0;

        public MyIterator(RandomizedQueue<T> queue) {
            items = queue.items;
            indices = new int[queue.head + 1];
            for (int j = 0; j < indices.length; j++) {
                indices[j] = j;
            }
            StdRandom.shuffle(indices);
        }

        public boolean hasNext() {
            return i < indices.length;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            final T val = (T) items[indices[i]];
            i++;
            return val;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        final RandomizedQueue<Integer> q = new RandomizedQueue<>();

        //q.dequeue();

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);

        for (Integer integer : q) {
            System.out.println(integer);
        }

        System.out.println();
        final Iterator<Integer> iterator = q.iterator();
        System.out.println(iterator.hasNext());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println(iterator.hasNext());

        System.out.println();

        System.out.println(q.size());
        System.out.println(q.isEmpty());

        System.out.println();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        for (Integer integer : q) {
            System.out.println(integer);
        }
        System.out.println(q.size());
        System.out.println(q.isEmpty());

        System.out.println();
        q.enqueue(55);
        System.out.println(q.sample());
    }

}
