/***********************************************************************
 *The rules of how a single cell works and values it can hold. 
 *
 * @author Nicholas Reitz
 * @version February 10 2014
 **********************************************************************/
package package1;

public class Cell {

	/** number of mines around a cell */
	private int mineCount;

	/** if a cell is flagged */
	private boolean isFlagged;

	/** if a cell is exposed */
	private boolean isExposed;

	/** if a cell is a mine */
	private boolean isMine;

	/*******************************************************************
	 * Constructor that creates a cell when status have been picked.
	 * 
	 * @param mineCount
	 *            number of mines around the cell
	 * @param isFlagged
	 *            if the cell is flagged or not
	 * @param isExposed
	 *            if the cell is exposed or not
	 * @param isMine
	 *            if the cell is a mine or not
	 ******************************************************************/
	public Cell(int mineCount, boolean isFlagged, boolean isExposed,
			boolean isMine) {
		this.mineCount = mineCount;
		this.isFlagged = isFlagged;
		this.isExposed = isExposed;
		this.isMine = isMine;
	}

	/*******************************************************************
	 * Constructor that creates a cell with starting status
	 ******************************************************************/
	public Cell() {
		this.mineCount = 0;
		this.isFlagged = false;
		this.isExposed = false;
		this.isMine = false;
	}

	/*******************************************************************
	 * Method that returns number of mines around the cell
	 * 
	 * @return mineCount number of mines around the cell
	 ******************************************************************/
	public int getMineCount() {
		return mineCount;
	}

	/*******************************************************************
	 * Method that sets the number of mines around the cell
	 * 
	 * @param mineCount
	 *            number of mines around the cell
	 ******************************************************************/
	public void setMineCount(int mineCount) {
		this.mineCount = mineCount;
	}

	/*******************************************************************
	 * Method that returns if the cell is flagged
	 * 
	 * @return if the cell is flagged
	 ******************************************************************/
	public boolean isFlagged() {
		return isFlagged;
	}

	/*******************************************************************
	 * Method that sets the cell being flagged or not
	 * 
	 * @param isFlagged
	 *            if the cell is flagged
	 ******************************************************************/
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}

	/*******************************************************************
	 * Method that returns if a cell is exposed or not
	 * 
	 * @return if the cell exposed
	 ******************************************************************/
	public boolean isExposed() {
		return isExposed;
	}

	/*******************************************************************
	 * Method that sets the cell being exposed or not
	 * 
	 * @param isExposed
	 *            is the cell exposed
	 ******************************************************************/
	public void setExposed(boolean isExposed) {
		this.isExposed = isExposed;
	}

	/*******************************************************************
	 * Method that returns if a cell is a mine or not
	 * 
	 * @return is the cell a mine
	 ******************************************************************/
	public boolean isMine() {
		return isMine;
	}

	/*******************************************************************
	 * Method that sets the cell a mine or not
	 * 
	 * @param isMine
	 *            is the cell a mine
	 ******************************************************************/
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
}
