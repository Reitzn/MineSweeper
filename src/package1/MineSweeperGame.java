/***********************************************************************
 *Rules of the game mine sweeper with a user being able to select how
 * many cells there will be. The game starts with a 10X10 board.
 *
 * @author Nicholas Reitz
 * @version February 10 2014
 **********************************************************************/

package package1;

import java.util.*;

public class MineSweeperGame {

	/** 2-d array to create the board */
	private Cell[][] board;

	/** the status of the game */
	private GameStatus status;

	/** the size of the board */
	private int size;

	/** total number of mines */
	private int totalMineCount;

	/** number of wins in a game */
	private int win;

	/** number of losses in a game */
	private int loss;

	/** number of flags left */
	private int flags;

	/*******************************************************************
	 * Game Constructor that sets the default status and that starts the game.
	 ******************************************************************/
	public MineSweeperGame() {
		win = 0;
		loss = 0;
		size = 10;
		totalMineCount = 10;
		flags = totalMineCount;

		// creates the cells
		reset();
	}

	/*******************************************************************
	 * Method that places selected number of mines on the board randomly and
	 * sends each cell to get counted to find how many mines are around them.
	 ******************************************************************/
	private void setMines() {

		Random random = new Random();
		int mineCount = 0;

		// places number of selected mines
		while (mineCount < totalMineCount) {
			int col = random.nextInt(size);
			int row = random.nextInt(size);

			// sets the mine in the cell if randomly picked
			if (!board[row][col].isMine()) {
				board[row][col].setMine(true);
				mineCount++;
			}
		}

		// sends all the cells to get counted
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)

				// sends the cell to get counted
				countMine(row, col);

	}

	/*******************************************************************
	 * Method that takes the selected cell checks if for a mine, exposes the
	 * cell, opens cells around it, and checks if the game is won.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 ******************************************************************/
	public void select(int row, int col) {

		// if there is a flag do nothing
		if (board[row][col].isFlagged())
			return;

		// if its a mine change status and adds to loss
		if (board[row][col].isMine()) {

			// board[row][col].setExposed(true);
			status = GameStatus.Lost;
			loss++;
			return;
		}

		// sends the cell to get open and find all the zeros
		openZero(row, col);

		// counts all the exposed mines
		int i = 0;
		for (int r = 0; r < size; r++)
			for (int c = 0; c < size; c++)
				if (board[r][c].isExposed())
					i++;

		// checks to see if the game has been won
		if (i + totalMineCount == size * size) {
			status = GameStatus.Won;
			win++;
		}

	}

	/*******************************************************************
	 * Recursive method that opens all of the zeros and cells around a zero.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 ******************************************************************/

	private void openZero(int row, int col) {

		// checks if the cell is out of bounds
		if (row >= 0 && col >= 0 && row < size && col < size) {

			// cell is zero, not exposed, not mine, not flagged
			if (board[row][col].getMineCount() == 0
					&& !board[row][col].isExposed()
					&& !board[row][col].isMine()
					&& !board[row][col].isFlagged()) {

				board[row][col].setExposed(true);

				// sends the right cell to get opened
				openZero(row, col + 1);

				// sends the left cell to get opened
				openZero(row, col - 1);

				// sends the bottom cell to get opened
				openZero(row + 1, col);

				// sends the bottom left corner cell to get opened
				openZero(row + 1, col - 1);

				// sends the bottom right corner cell to get opened
				openZero(row + 1, col + 1);

				// sends the top cell to get opened
				openZero(row - 1, col);

				// sends the top left corner cell to get opened
				openZero(row - 1, col - 1);

				// sends the top right corner cell to get opened
				openZero(row - 1, col + 1);

				// if not a zero, not flagged, not mine cell is opened
			} else if (board[row][col].getMineCount() > 0
					&& !board[row][col].isFlagged()
					&& !board[row][col].isMine()) {
				board[row][col].setExposed(true);
			}
		}
	}

	/*******************************************************************
	 * Method that counts the number of mines around a cell.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 ******************************************************************/
	public void countMine(int row, int col) {

		// Make sure inbounds to the bottom
		if (row + 1 >= size) {
		} else {

			// If a mine is below it adds to the count
			if (board[row + 1][col].isMine()) {
				board[row][col]
						.setMineCount(board[row][col].getMineCount() + 1);
			}
			// Make sure inbounds to the left
			if (col - 1 < 0) {
			} else {

				// If a mine is in the bottom left adds to the count
				if (board[row + 1][col - 1].isMine()) {
					board[row][col]
							.setMineCount(board[row][col].getMineCount() + 1);
				}
			}
			// Make sure inbounds to the right
			if (col + 1 >= size) {
			} else {

				// If a mine is in the bottom right adds to the count
				if (board[row + 1][col + 1].isMine()) {
					board[row][col]
							.setMineCount(board[row][col].getMineCount() + 1);
				}
			}
		}

		// Make sure inbounds to the right
		if (col + 1 >= size) {
		} else {

			// If a mine is to the right adds to the count
			if (board[row][col + 1].isMine()) {
				board[row][col]
						.setMineCount(board[row][col].getMineCount() + 1);
			}
		}

		// Make sure inbounds to the left
		if (col - 1 < 0) {
		} else {
			if (board[row][col - 1].isMine()) {
				board[row][col]
						.setMineCount(board[row][col].getMineCount() + 1);
			}
		}

		// Make sure inbounds to the top
		if ((row - 1) < 0) {
		} else {

			// If a mine is above adds to the count
			if (board[row - 1][col].isMine()) {
				board[row][col]
						.setMineCount(board[row][col].getMineCount() + 1);
			}

			// Make sure inbounds to the left
			if (col - 1 < 0) {
			} else {

				// If a mine is above and left adds to the count
				if (board[row - 1][col - 1].isMine()) {
					board[row][col]
							.setMineCount(board[row][col].getMineCount() + 1);
				}
			}

			// Make sure inbounds to the right
			if (col + 1 >= size) {
			} else {

				// If a mine is above and right adds to the count
				if (board[row - 1][col + 1].isMine()) {
					board[row][col]
							.setMineCount(board[row][col].getMineCount() + 1);
				}
			}
		}
	}

	/*******************************************************************
	 * Method that resets the game to the same or new size depending on what the
	 * user has chosen.
	 ******************************************************************/
	public void reset() {

		flags = totalMineCount;
		board = new Cell[size][size];

		// sets all the cells back to original settings
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				board[row][col] = new Cell();

		status = GameStatus.NotOverYet;

		// sets the mines
		setMines();
	}

	/*******************************************************************
	 * Method that returns the status of the game.
	 * 
	 * @return the status of the game
	 ******************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}

	/*******************************************************************
	 * Method that sends a cell's information that has been asked for.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 * @return the cell that was selected
	 ******************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/*******************************************************************
	 * Method that returns if a selected cell is a mine or not.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 * @return if the cell contains a mine or not
	 ******************************************************************/
	public boolean getMine(int row, int col) {

		// looks for a mine in that cell
		if (board[row][col].isMine())
			return true;
		else
			return false;
	}

	/*******************************************************************
	 * Method that returns the mine count for a selected cell.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 * @return number of mines in the selected cell
	 ******************************************************************/
	public int getMineCount(int row, int col) {
		return board[row][col].getMineCount();
	}

	/*******************************************************************
	 * Method that toggles between if the selected cell is flagged or not.
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 ******************************************************************/
	public void setFlag(int row, int col) {

		// toggles between flagged and not flagged
		if (board[row][col].isFlagged()) {
			board[row][col].setFlagged(false);
			flags++;
		} else {
			board[row][col].setFlagged(true);
			flags--;
		}
	}

	/*******************************************************************
	 * Method that changes the size of the board.
	 * 
	 * @param size
	 *            the new size of board the user wants
	 ******************************************************************/
	public void newBoard(int size, int totalMineCount) {
		this.size = size;
		this.totalMineCount = totalMineCount;

		// resets the game
		reset();
	}

	/*******************************************************************
	 * Method that returns number of times the game has been won.
	 * 
	 * @return number of times game has been won
	 ******************************************************************/
	public int getWin() {
		return win;
	}

	/*******************************************************************
	 * Method that returns number of times the game has been lost.
	 * 
	 * @return number of times game has been lost
	 ******************************************************************/
	public int getLoss() {
		return loss;
	}

	/*******************************************************************
	 * Method that returns how many flags the user has left, although mine
	 * sweeper allows for more flags then mines, so mine does too.
	 * 
	 * @return number of flags left
	 ******************************************************************/
	public int getFlagCount() {
		return flags;
	}

	/*******************************************************************
	 * Method that returns if a selected cell is flagged or not
	 * 
	 * @param row
	 *            the row the selected cell is in
	 * @param col
	 *            the column the selected cell is in
	 * @return
	 ******************************************************************/
	public boolean isFlaged(int row, int col) {

		// is the cell a flag?
		if (board[row][col].isFlagged())
			return true;
		else
			return false;
	}
}
