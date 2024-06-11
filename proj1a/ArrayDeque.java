public class ArrayDeque<T> {
    private int size;

    private int nextFirst;

    private int nextLast;

    private T[] items;

    private static final int LENGTH = 16;

    private static final double FACTOR = 0.25;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
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
        nextFirst = capacity - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        items[nextFirst] = item;
        size += 1;
        nextFirst = minusOne(nextFirst);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(2 * size);
        }
        items[nextLast] = item;
        size += 1;
        nextLast = plusOne(nextLast);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T x = items[nextFirst];
        size -= 1;
        if (items.length >= LENGTH && size < items.length * FACTOR) {
            resize(2 * size);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T x = items[nextLast];
        size -= 1;
        if (items.length >= LENGTH && size < items.length * FACTOR) {
            resize(2 * size);
        }
        return x;
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
}
