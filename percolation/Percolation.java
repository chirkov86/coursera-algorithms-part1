/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final boolean[][] isOpen;
    private final int size;
    private boolean percolates;
    private int topRowOpenComponentIdx;
    private int bottomRowOpenComponentIdx;
    private int openCounter;
    private final WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = n;
        this.isOpen = new boolean[n][n];
        this.openCounter = 0;
        this.percolates = false;
        // Two extra elements to track open sites at the top row together
        // or open sites at the bottom together
        this.uf = new WeightedQuickUnionUF(n * n + 2);
        topRowOpenComponentIdx = n * n;
        bottomRowOpenComponentIdx = n * n + 1;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);
        if (isOpen[row - 1][col - 1]) return;
        isOpen[row - 1][col - 1] = true;
        openCounter++;
        final int thisSite = size * (row - 1) + col - 1;
        if (row > 1 && isOpen(row - 1, col)) {
            // upper
            uf.union(thisSite, size * (row - 2) + col - 1);
        }
        if (row < size && isOpen(row + 1, col)) {
            // lower
            uf.union(thisSite, size * (row) + col - 1);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            // left
            uf.union(thisSite, size * (row - 1) + col - 2);
        }
        if (col < size && isOpen(row, col + 1)) {
            // right
            uf.union(thisSite, size * (row - 1) + col);
        }
        if (row == 1) {
            uf.union(thisSite, topRowOpenComponentIdx);
        }
        if (row == size) {
            final int thisSiteComponent = uf.find(thisSite);
            this.percolates = thisSiteComponent == uf.find(topRowOpenComponentIdx);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return isOpen[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        boolean isFull = false;
        if (isOpen[row - 1][col - 1]) {
            final int thisSite = size * (row - 1) + col - 1;
            final int thisSiteComponent = uf.find(thisSite);
            isFull = thisSiteComponent == uf.find(topRowOpenComponentIdx);
        }
        return isOpen[row - 1][col - 1] && isFull;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCounter;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.percolates;
    }

    private void validateRowCol(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // intentionally empty
    }
}
