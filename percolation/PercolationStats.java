/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final double mean;
    private final double stdDev;
    private final double confidenceLo;
    private final double confidenceHi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        double[] results = new double[trials];

        for (int i = 0; i < trials; i++) {
            final Percolation percolation = new Percolation(n);
            int openCounter = 0;
            while (true) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                while (percolation.isOpen(row, col)) {
                    row = StdRandom.uniform(n) + 1;
                    col = StdRandom.uniform(n) + 1;
                }
                percolation.open(row, col);
                openCounter++;
                if (percolation.percolates()) {
                    results[i] = openCounter * 1.0 / (n * n);
                    break;
                }
            }
        }
        this.mean = StdStats.mean(results);
        this.stdDev = StdStats.stddev(results);
        final double v = CONFIDENCE_95 * stddev() / Math.sqrt(trials);
        this.confidenceLo = this.mean - v;
        this.confidenceHi = this.mean + v;
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        int latticeSize = 0;
        int numberOfTrials = 0;
        try {
            latticeSize = Integer.parseInt(args[0]);
            numberOfTrials = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid input: " + args[0] + ", " + args[1]);
        }
        final PercolationStats stats = new PercolationStats(latticeSize, numberOfTrials);
        System.out.println("mean \t\t\t\t\t\t= " + stats.mean());
        System.out.println("stddev \t\t\t\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval \t= [" +
                                   stats.confidenceLo() + "," +
                                   stats.confidenceHi() + "]");
    }
}
