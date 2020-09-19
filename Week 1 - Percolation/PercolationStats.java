import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private Object IllegalArgumentException;
	private double[] openSiteFraction;
	private int numTrial;
	private int arrayLength;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("Invalid arguments are entered");
		}

		numTrial = trials;
		openSiteFraction = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);

//			System.out.println("Trial " + i + " started!");

			// making grid
			int[] grid = new int[n * n];
			for (int k = 0; k < n * n; k++) {
				grid[k] = k;
			}
			// shuffling grid
			StdRandom.shuffle(grid);

			int index = 0;
			while (!percolation.percolates()) {
				int indexX = grid[index] / n + 1;
				int indexY = grid[index] % n + 1;
				percolation.open(indexX, indexY);
				index = (index + 1) % (n * n);
//				System.out.println("Opening ("+indexX+","+indexY+")");
			}

//			System.out.print("Trial " + i + " is finished!\n Number of Open Sites recorded =");
//			System.out.println(percolation.numberOfOpenSites());

			openSiteFraction[i] = (double)percolation.numberOfOpenSites() / (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(openSiteFraction);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(openSiteFraction);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		double threshold = mean();
		threshold -= 1.96 * stddev() / Math.sqrt(numTrial);
		return threshold;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		double threshold = mean();
		threshold += 1.96 * stddev() / Math.sqrt(numTrial);
		return threshold;
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
//		int n = Integer.parseInt("2");
//		int trials = Integer.parseInt("10000");

		PercolationStats stats = new PercolationStats(n, trials);

		// printing out the finds
		String confidence = "[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]";
		StdOut.println("mean                    = " + stats.mean());
		StdOut.println("stddev                  = " + stats.stddev());
		StdOut.println("95% confidence interval = " + confidence);

	}
}