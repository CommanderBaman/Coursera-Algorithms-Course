import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//import java.util.Scanner;

public class Percolation {

	// defining the site where we are exploring
	private final WeightedQuickUnionUF fullSite;
	private final boolean[][] openGrid;
	private final boolean[][] fullGrid;
	private final int topVirtualPoint;
	private final int bottomVirtualPoint;
	private final int fullSiteLength;
	private int numOpenSites = 0;

	// creates a n by n grid, with all sites initially blocked
	public Percolation(int n){
		// throwing exceptions for invalid input
		if ( n <= 0){
			throw new IllegalArgumentException("Invalid arguments recorded: n must be greater than 0");
		}
		fullSite = new WeightedQuickUnionUF(n*n + 2);
		openGrid = new boolean[n][n];
		fullGrid = new boolean[n][n];
		topVirtualPoint = n*n;
		bottomVirtualPoint = n*n + 1;
		fullSiteLength = n;

		for ( int col = 1; col <= fullSiteLength; col++) {
			fullSite.union( convertTo1D( 1, col), topVirtualPoint);
			fullSite.union( convertTo1D( fullSiteLength, col), bottomVirtualPoint);
		}
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if ( !isOpen(row, col)) {
			openGrid[row-1][col-1] = true;
			numOpenSites++;

			// adding as full if side neighbours are full
			// for top
			if (checkIndex(row-1) && checkIndex(col)){
				if (isOpen(row-1,col)){
					fullSite.union(convertTo1D(row,col), convertTo1D(row-1, col));
				}
			}
			// for right
			if (checkIndex(row) && checkIndex(col+1)){
				if (isOpen(row,col+1)){
					fullSite.union(convertTo1D(row,col), convertTo1D(row, col+1));
				}
			}

			// for bottom
			if (checkIndex(row+1) && checkIndex(col)){
				if (isOpen(row+1,col)){
					fullSite.union(convertTo1D(row,col), convertTo1D(row+1, col));
				}
			}

			// for left
			if (checkIndex(row) && checkIndex(col-1)){
				if (isOpen(row,col-1)){
					fullSite.union(convertTo1D(row,col), convertTo1D(row, col-1));
				}
			}
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		// throwing exceptions for invalid input
		validateIndex(row);
		validateIndex(col);

		return openGrid[row-1][col-1];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col){
		// throwing exceptions for invalid input
		validateIndex(row);
		validateIndex(col);

		if (fullGrid[row-1][col-1]){
			return true;
		}
		else if (isOpen(row, col)) {
			if (fullSite.find( convertTo1D( row, col)) == fullSite.find(topVirtualPoint)) {
				fullGrid[row-1][col-1] = true;
				return true;
			}
		}
		return false;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return numOpenSites;
	}

	// does the system percolate?
	public boolean percolates(){
		if( fullSite.find( topVirtualPoint) == fullSite.find( bottomVirtualPoint)) return true;
		return false;
	}

	private int convertTo1D (int row, int col){
		return ((row - 1) * fullSiteLength) + (col - 1);
	}

	private void validateIndex(int index){
		if ( index < 1 || index > fullSiteLength){
			throw new IllegalArgumentException("Invalid indices recorded");
		}
	}

	private boolean checkIndex(int index){
		return index >= 1 && index <= fullSiteLength;
	}

	// test client (optional)
	public static void main(String [] args) {
//		Scanner scanner = new Scanner(System.in);
//		int size = scanner.nextInt();
//
//		Percolation percolation = new Percolation(size);
//		int argCount = scanner.nextInt();
//		for (int i = 0; i < argCount; i++) {
//			int row = scanner.nextInt();
//			int col = scanner.nextInt();
//			percolation.open(row, col);
//			System.out.printf("Adding row: %d  col: %d \n", row, col);
//			if (percolation.percolates()) {
//				System.out.printf("The System percolates\n");
//				System.out.print("Number of open sites = " + percolation.numOpenSites);
//			}
//		}
//		if (!percolation.percolates()) {
//			System.out.print("Does not percolate\n");
//			System.out.println("Number of open sites = " + percolation.numOpenSites);
//		}
	}
}
