package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private int[][] array;
    private WeightedQuickUnionUF rowSet;
    private WeightedQuickUnionUF set;
    private int count;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Illegal Argument");
        }
        n = N;
        array = new int[N][N];
        rowSet = new WeightedQuickUnionUF(N * N + 1);
        set = new WeightedQuickUnionUF(N * N + 2);
        count = 0;
        for (int i = 0; i < N; i++) {
            set.union(i, N * N);
            set.union(i + N * (N - 1), N * N + 1);
            rowSet.union(i, N * N);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row >= n || col >= n) {
            throw new IndexOutOfBoundsException("Out of Bounds");
        }
        return array[row][col] == 1;
    }

    private int getIndex(int row, int col) {
        return row * n + col;
    }

    public void open(int row, int col) {
        if (row >= n || col >= n) {
            throw new IndexOutOfBoundsException("Out of Bounds");
        }
        if (isOpen(row, col)) {
            return;
        }
        array[row][col] = 1;
        count += 1;
        int index = getIndex(row, col);
        for (int i = row - 1; i <= row + 1; i++) {
            if (i >= n || i < 0) {
                continue;
            }
            if (isOpen(i, col)) {
                rowSet.union(getIndex(i, col), index);
                set.union(getIndex(i, col), index);
            }
        }

        for (int i = col - 1; i <= col + 1; i++) {
            if (i >= n || i < 0) {
                continue;
            }
            if (isOpen(row, i)) {
                set.union(getIndex(row, i), index);
                rowSet.union(getIndex(row, i), index);
            }
        }
    }

    public boolean isFull(int row, int col) {
        if (row >= n || col >= n) {
            throw new IndexOutOfBoundsException("Out of Bounds");
        }
        return set.connected(getIndex(row, col), n * n) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return rowSet.connected(n * n, n * n + 1) && count > 0;
    }

    public static void main(String[] args) {
        System.out.println("finished");
    }
}
