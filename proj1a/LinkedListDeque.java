public class LinkedListDeque<T> {
    private class DoubleNode<T> {
        private DoubleNode<T> prev;
        private T item;
        private DoubleNode<T> next;

        private DoubleNode(DoubleNode<T> prev, T item, DoubleNode<T> next) {
            this.next = next;
            this.prev = prev;
            this.item = item;
        }
    }

    private DoubleNode<T> firstSential;
    private DoubleNode<T> lastSential;
    private int size;

    public LinkedListDeque() {
        size = 0;
        firstSential = new DoubleNode<T>(null, null, null);
        lastSential = new DoubleNode<T>(null, null, null);
        firstSential.next = lastSential;
        lastSential.prev = firstSential;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        firstSential.next = new DoubleNode<T>(firstSential, item, firstSential.next);
        firstSential.next.next.prev = firstSential.next;
        size += 1;
    }

    public void addLast(T item) {
        lastSential.prev = new DoubleNode<T>(lastSential.prev, item, lastSential);
        lastSential.prev.prev.next = lastSential.prev;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = firstSential.next.item;
        firstSential.next = firstSential.next.next;
        firstSential.next.prev = firstSential;
        size -= 1;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = lastSential.prev.item;
        lastSential.prev = lastSential.prev.prev;
        lastSential.prev.next = lastSential;
        size -= 1;
        return x;
    }

    public void printDeque() {
        DoubleNode<T> p = firstSential.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        DoubleNode<T> p = firstSential.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private LinkedListDeque(DoubleNode<T> firstSential, int size, DoubleNode<T> lastSential) {
        this.size = size;
        this.firstSential = firstSential;
        this.lastSential = lastSential;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else if (index == 0) {
            return firstSential.next.item;
        } else {
            return new LinkedListDeque<T>(firstSential.next,
                    size - 1, lastSential).getRecursive(index - 1);
        }
    }
}
