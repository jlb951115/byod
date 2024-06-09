public class LinkedListDeque<T> {
    private class DoubleNode<T> {
        private T item;
        private DoubleNode<T> prev;
        private DoubleNode<T> next;
        private DoubleNode(DoubleNode<T> prev, T item, DoubleNode<T> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private DoubleNode<T> first;
    private DoubleNode<T> last;
    private int size;

    public LinkedListDeque() {
        first = new DoubleNode<>(null, null, null);
        last = new DoubleNode<>(null, null, null);
        size = 0;
        first.next = last;
        last.prev = first;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        size += 1;
        first.next = new DoubleNode<T>(first, item, first.next);
        first.next.next.prev = first.next;
    }

    public void addLast(T item) {
        size += 1;
        last.prev = new DoubleNode<T>(last.prev, item, last);
        last.prev.prev.next = last.prev;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T x = first.next.item;
        first.next = first.next.next;
        first.next.prev = first;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T x = last.prev.item;
        last.prev = last.prev.prev;
        last.prev.next = last;
        return x;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        DoubleNode<T> p = first.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    public void printDeque() {
        DoubleNode<T> p = first.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    private LinkedListDeque(DoubleNode<T> first, int size, DoubleNode<T> last) {
        this.size = size;
        this.first = first;
        this.last = last;
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
