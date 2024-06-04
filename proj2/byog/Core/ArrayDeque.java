package byog.Core;

public class ArrayDeque<T> {
    private int nextfirst;
    private int nextlast;
    private int size;
    private T[] item;
    private final int constant = 16;
    public ArrayDeque() {
        this.size = 0;
        this.nextfirst = 0;
        this.nextlast = 1;
        this.item = (T[]) new Object[8];
    }

    private int plusone(int x) {
        int index = x + 1;
        if (index == this.item.length) {
            index = 0;
        }
        return index;
    }

    private int minusone(int x) {
        int index = x - 1;
        if (index < 0) {
            index = this.item.length - 1;
        }
        return index;
    }
    private void resize(int capticy) {
        T[] a = (T[]) new Object[capticy];
        int i = 0;
        int index = this.nextfirst;
        while (i < this.size) {
            a[i] = this.item[plusone(index)];
            i += 1;
            index = plusone(index);
        }
        this.item = a;
        this.nextfirst = this.item.length - 1;
        this.nextlast = this.size;
    }

    public void addFirst(T x) {
        if (this.size == this.item.length) {
            resize(2 * this.size);
        }
        this.size += 1;
        this.item[this.nextfirst] = x;
        this.nextfirst = minusone(this.nextfirst);
    }

    public void addLast(T x) {
        if (this.size == this.item.length) {
            resize(2 * this.size);
        }
        this.size += 1;
        this.item[this.nextlast] = x;
        this.nextlast = plusone(this.nextlast);
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public T get(int index) {
        return this.item[(this.nextfirst + 1 + index) % this.item.length];
    }

    public void printDeque() {
        int i = 0;
        while (i < this.size) {
            System.out.print(this.get(i));
            System.out.print(' ');
            i += 1;
        }
    }

    public T removeFirst() {
        if (this.size == 0) {
            return null;
        }
        this.size -= 1;
        int index = plusone(this.nextfirst);
        T x = this.item[index];
        this.nextfirst = index;
        this.item[this.nextfirst] = null;
        if (this.item.length >= constant && this.size <= this.item.length * 0.25) {
            resize(this.size * 2);
        }
        return x;
    }

    public T removeLast() {
        if (this.size == 0) {
            return null;
        }
        this.size -= 1;
        int index = minusone(this.nextlast);
        T x = this.item[index];
        this.nextlast = index;
        this.item[this.nextlast] = null;
        if (this.item.length >= constant && this.size <= this.item.length * 0.25) {
            resize(this.size * 2);
        }
        return x;
    }
}
