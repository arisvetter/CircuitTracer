import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * @author mvail, Aris Vetter
 */
public class CircuitBoard {
	/** current contents of the board */
	private char[][] board;
	/** location of row,col for '1' */
	private Point startingPoint;
	/** location of row,col for '2' */
	private Point endingPoint;

	// constants you may find useful
	private final int ROWS; // initialized in constructor
	private final int COLS; // initialized in constructor
	private final char OPEN = 'O'; // capital 'o', an open position
	private final char CLOSED = 'X';// a blocked position
	private final char TRACE = 'T'; // part of the trace connecting 1 to 2
	private final char START = '1'; // the starting component
	private final char END = '2'; // the ending component
	private final String ALLOWED_CHARS = "OXT12"; // useful for validating with indexOf

	/**
	 * Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 * 'O' an open position
	 * 'X' an occupied, unavailable position
	 * '1' first of two components needing to be connected
	 * '2' second of two components needing to be connected
	 * 'T' is not expected in input files - represents part of the trace
	 * connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 *                 file containing a grid of characters
	 * @throws FileNotFoundException      if Scanner cannot open or read the file
	 * @throws InvalidFileFormatException for any file formatting or content issue
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {
		// am i off by one in rows and cols?

		Scanner fileScan = new Scanner(new File(filename));

		Scanner firstLineScnr = new Scanner(fileScan.nextLine());
		if (firstLineScnr.hasNextInt()) {
			ROWS = firstLineScnr.nextInt();

			// what if isnt an integer
		} else {
			throw new InvalidFileFormatException("First line of file must contain two integers");
		}
		if (firstLineScnr.hasNextInt()) {
			COLS = firstLineScnr.nextInt();
		} else {
			throw new InvalidFileFormatException("First line of file must contain two integers");
		}

		if (ROWS <= 0 || COLS <= 0) {
			throw new InvalidFileFormatException("Invalid row or column sizes");
		}

		if (firstLineScnr.hasNext()) {
			fileScan.close();
			throw new InvalidFileFormatException("First line can only contain 2 integers.");
		}

		board = new char[ROWS][COLS];

		int rowCount = 0;
		int colCount = 0;

		// System.out.println("ideal rows " + ROWS + "ideal cols" + COLS);
		int numOnes = 0;
		int numTwos = 0;
		while (fileScan.hasNext()) {
			Scanner lineScanner = new Scanner(fileScan.nextLine());

			while (lineScanner.hasNext()) {
				String currChar = lineScanner.next();
				// System.out.println();
				// System.out.println("curr char" + currChar);
				// System.out.println("curr row" + rowCount);
				if (colCount >= COLS) {
					throw new InvalidFileFormatException("Incorrect number of columns in the file.");
				}
				if (rowCount >= ROWS) {
					throw new InvalidFileFormatException("Incorrect number of rows in the file.");
				}
				// System.out.println(colCount + " curr cols in row " + rowCount);
				board[rowCount][colCount] = currChar.charAt(0);
				switch (currChar) {

					case "1":
						numOnes++;
						startingPoint = new Point(rowCount, colCount);
						break;
					case "2":
						numTwos++;
						endingPoint = new Point(rowCount, colCount);
					case "O", "X":
						break;
					default:
						throw new InvalidFileFormatException(currChar + "is not a valid character.");
				}
				colCount++;
			}
			if (colCount != COLS) {
				throw new InvalidFileFormatException("Incorrect number of columns in file.");
			}

			colCount = 0;
			rowCount++;
			// System.out.println();
		}

		if (rowCount != ROWS) {
			throw new InvalidFileFormatException("Incorrect number of rows in the file.");
		}
		fileScan.close();
		if (numOnes != 1) {
			throw new InvalidFileFormatException("Exactly one 1 is required.");
		}
		if (numTwos != 1) {
			throw new InvalidFileFormatException("Exactly one 2 is required.");
		}


	}

	/**
	 * Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/**
	 * Utility method for copy constructor
	 * 
	 * @return copy of board array
	 */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}

	/**
	 * Return the char at board position x,y
	 * 
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}

	/**
	 * Return whether given board position is open
	 * 
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}

	/**
	 * Set given position to be a 'T'
	 * 
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}

	/** @return starting Point(row,col) */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}

	/** @return ending Point(row,col) */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}

	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}

	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}

}// class CircuitBoard
