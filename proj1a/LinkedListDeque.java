public class LinkedListDeque<T> {
    private class DoubleNode<T> {
        private T item;
        private DoubleNode<T> prev;
        private DoubleNode<T> next;
        private DoubleNode(DoubleNode<T> prev, T item, DoubleNode<T> next) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private DoubleNode<T> sentFirst;

    private DoubleNode<T> sentBack;

    private int size;

    public LinkedListDeque() {
        sentFirst = new DoubleNode<T>(null, null, null);
        sentBack = new DoubleNode<T>(null, null, null);
        size = 0;
        sentFirst.next = sentBack;
        sentBack.prev = sentFirst;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        size += 1;
        sentFirst.next = new DoubleNode<T>(sentFirst, item, sentFirst.next);
        sentFirst.next.next.prev = sentFirst.next;
    }

    public void addLast(T item) {
        size += 1;
        sentBack.prev = new DoubleNode<T>(sentBack.prev, item, sentBack);
        sentBack.prev.prev.next = sentBack.prev;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = sentFirst.next.item;
        sentFirst.next = sentFirst.next.next;
        sentFirst.next.prev = sentFirst;
        size -= 1;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = sentBack.prev.item;
        sentBack.prev = sentBack.prev.prev;
        sentBack.prev.next = sentBack;
        size -= 1;
        return x;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        DoubleNode<T> p = sentFirst.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private LinkedListDeque(DoubleNode<T> sentFirst, int size, DoubleNode<T> sentBack) {
        this.size = size;
        this.sentBack = sentBack;
        this.sentFirst = sentFirst;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else if (index == 0) {
            return sentFirst.next.item;
        } else {
            return new LinkedListDeque<T>(sentFirst.next, size - 1,
                    sentBack).getRecursive(index - 1);
        }
    }

    public void printDeque() {
        DoubleNode<T> p = sentFirst.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }
}
