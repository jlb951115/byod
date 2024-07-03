public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    private static final int LENGTH = 8;
    private static final double FACTOR = 0.25;

    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        items = (T[]) new Object[LENGTH];
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
            a[i] = get(i);
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
        size -= 1;
        nextFirst = plusOne(nextFirst);
        T x = items[nextFirst];
        if (size < items.length * FACTOR && items.length >= LENGTH) {
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
        if (size < items.length * FACTOR && items.length >= LENGTH) {
            resize(2 * size);
        }
        return x;
    }
}
