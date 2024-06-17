public class LinkedListDeque<T> {
    private class Node<T> {
        private Node<T> prev;
        private T item;
        private Node<T> next;
        private Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private Node<T> sentFront;

    private int size;

    private Node<T> sentBack;

    public LinkedListDeque() {
        sentFront = new Node<>(null, null, null);
        sentBack = new Node<>(null, null, null);
        size = 0;
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T item) {
        sentFront.next = new Node<T>(sentFront, item, sentFront.next);
        sentFront.next.next.prev = sentFront.next;
        size += 1;
    }

    public void addLast(T item) {
        sentBack.prev = new Node<T>(sentBack.prev, item, sentBack);
        sentBack.prev.prev.next = sentBack.prev;
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T x = sentFront.next.item;
        sentFront.next = sentFront.next.next;
        sentFront.next.prev = sentFront;
        return x;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        T x = sentBack.prev.item;
        sentBack.prev = sentBack.prev.prev;
        sentBack.prev.next = sentBack;
        return x;
    }

    public void printDeque() {
        Node<T> p = sentFront.next;
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
        Node<T> p = sentFront.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private LinkedListDeque(Node<T> sentFront, int size, Node<T> sentBack) {
        this.sentFront = sentFront;
        this.size = size;
        this.sentBack = sentBack;
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        } else if (index == 0) {
            return sentFront.next.item;
        } else {
            return new LinkedListDeque<T>(sentFront.next,
                    size - 1, sentBack).getRecursive(index - 1);
        }
    }
}
