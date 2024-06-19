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
    private Node<T> sentBack;
    private int size;

    public LinkedListDeque() {
        size = 0;
        sentFront = new Node<>(null, null, null);
        sentBack = new Node<>(null, null, null);
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
        sentBack.prev = new Node<>(sentBack.prev, item, sentBack);
        sentBack.prev.prev.next = sentBack.prev;
        size += 1;
    }

    public void printDeque() {
        Node<T> p = sentFront.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T x = sentFront.next.item;
        sentFront.next = sentFront.next.next;
        sentFront.next.prev = sentFront;
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
        Node<T> p = sentFront.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    private LinkedListDeque(Node<T> sentFront, int size, Node<T> sentBack) {
        this.size = size;
        this.sentFront = sentFront;
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
