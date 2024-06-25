public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    private static final int LENGTH = 16;

    private static final double FACTOR = 0.25;

    private static final int BEGIN = 8;

    public ArrayDeque() {
        size = 0;
        nextLast = 1;
        nextFirst = 0;
        items = (T[]) new Object[BEGIN];
    }

    private int plusOne(int index) {
        if (index + 1 == items.length) {
            return 0;
        } else {
            return index + 1;
        }
    }

    private int minusOne(int index) {
        if (index - 1 < 0) {
            return items.length - 1;
        } else {
            return index - 1;
        }
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            a[i] = items[(nextFirst + 1 + i) % items.length];
        }
        items = a;
        nextLast = size;
        nextFirst = capacity - 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
    }

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            System.out.print(" ");
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        size += 1;
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextFirst = plusOne(nextFirst);
        T x = items[nextFirst];
        if (items.length >= LENGTH && size < items.length * FACTOR) {
            resize(2 * size);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextLast = minusOne(nextLast);
        T x = items[nextLast];
        if (items.length >= LENGTH && size < items.length * FACTOR) {
            resize(2 * size);
        }
        return x;
    }
}
