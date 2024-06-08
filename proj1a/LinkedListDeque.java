public class LinkedListDeque<T> {
    private class DoubleNode<T> {
        private DoubleNode<T> prev;
        private T item;
        private DoubleNode<T> next;

        private DoubleNode(DoubleNode prev, T item, DoubleNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private int size;
    private DoubleNode<T> first;
    private DoubleNode<T> last;

    public LinkedListDeque() {
        size = 0;
        first = new DoubleNode(null, null, null);
        last = new DoubleNode(null, null, null);
        first.next = last;
        last.prev = first;
    }

    public int size() {
        return  size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        DoubleNode<T> p = first;
        while (p.next != null) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    public void addFirst(T item) {
        first.next = new DoubleNode<T>(first, item, first.next);
        first.next.next.prev = first.next;
        size += 1;
    }

    public void addLast(T item) {
        last.prev = new DoubleNode<T>(last.prev, item, last);
        last.prev.prev.next = last.prev;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = first.next.item;
        first.next = first.next.next;
        first.next.prev = first;
        size -= 1;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T x = last.prev.item;
        last.prev = last.prev.prev;
        last.prev.next = last;
        size -= 1;
        return x;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int i = 0;
        DoubleNode<T> p = first;
        while (i < index) {
            p = p.next;
            i += 1;
        }
        return p.next.item;
    }

    private LinkedListDeque(DoubleNode<T> first, int size, DoubleNode<T> last) {
        this.last = last;
        this.first = first;
        this.size = size;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else if (index == 0) {
            return first.next.item;
        } else {
            return new LinkedListDeque<T>(first.next, size - 1, last).getRecursive(index - 1);
        }
    }
}
