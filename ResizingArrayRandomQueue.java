import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Random queue implementation using a resizing array.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    private Item[] q;       //Queue elements
    private int N;          //size of queue

    // Construct an empty queue.
    public ResizingArrayRandomQueue() {
        q = (Item[]) new Object[2];         //start with size 2
        this.N = 0;
    }

    // Is the queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // The number of items on the queue.
    public int size() {
        return N;
    }

    // Add item to the queue.
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        if (N == q.length)
            resize(2*q.length);
        q[N++] = item;
    }

    // Remove and return a random item from the queue.
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int ran = StdRandom.uniform(N);         //N is exclusive
        Item item = q[ran];                     //save random q[ran] in item
        q[ran] = q[N-1];                        //copy last index element to q[ran]
        q[N-1] = null;                          //remove last index
        if (N > 0 && N == q.length/4)
            resize(q.length/2);
        N--;
        return item;
    }

    // Return a random item from the queue, but do not remove it.
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int ran = StdRandom.uniform(N);         //N is exclusive
        Item item = q[ran];
        return item;
    }

    // An independent iterator over items in the queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] items;
        private int current;        //index of current item

        RandomQueueIterator() {
            items = (Item[]) new Object[N];
            for (int i = 0; i < N; i++)                 //copying all items in q to array items[]
                items[i] = q[i];
            StdRandom.shuffle(items);
            current = 0;
        }

        public boolean hasNext()  { return current < N; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[current++];
        }
    }

    // A string representation of the queue.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Helper method for resizing the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q =
            new ResizingArrayRandomQueue<Integer>();
        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readInt());
        }
        int sum1 = 0;
        for (int x : q) {
            sum1 += x;
        }
        int sum2 = sum1;
        for (int x : q) {
            sum2 -= x;
        }
        int sum3 = 0;
        while (q.size() > 0) {
            sum3 += q.dequeue();
        }
        StdOut.println(sum1);
        StdOut.println(sum2);
        StdOut.println(sum3);
        StdOut.println(q.isEmpty());
    }
}
