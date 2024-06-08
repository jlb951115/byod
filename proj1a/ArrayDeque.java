public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        nextLast = 1;
        items = (T[]) new Object[8];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private int plusone(int x) {
        if (x + 1 == items.length) {
            return 0;
        } else {
            return x + 1;
        }
    }

    private int minusone(int x) {
        if (x - 1 < 0) {
            return items.length - 1;
        } else {
            return x - 1;
        }
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        return items[(nextFirst + 1 + index) % items.length];
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

    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i));
            System.out.print(" ");
        }
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        size += 1;
        items[nextFirst] = item;
        nextFirst = minusone(nextFirst);
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        size += 1;
        items[nextLast] = item;
        nextLast = plusone(nextLast);
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = items[plusone(nextFirst)];
        size -= 1;
        nextFirst = plusone(nextFirst);
        if (size <= items.length * 0.25) {
            resize(size * 2);
        }
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = items[minusone(nextLast)];
        size -= 1;
        nextLast = minusone(nextLast);
        if (size <= items.length * 0.25) {
            resize(size * 2);
        }
        return x;
    }
}
