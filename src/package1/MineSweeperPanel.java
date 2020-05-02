/***********************************************************************
 *Graphical representation of the game mine sweeper with a user beaing 
 *able to select how many cells there will be. The game starts with a 
 *10X10 board.
 *
 * @author Nicholas Reitz
 * @version February 10 2014
 ***********************************************************************/

package package1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.*;

public class MineSweeperPanel extends JPanel {

	/** placing down a flag */
	private JRadioButton flag;

	/** buttons array for game cells */
	private JButton[][] board;

	/** temp cell for placement */
	private Cell iCell;

	/** button for quitting the game */
	private JButton quitButton;

	/** button for new game board */
	private JButton newSize;

	/** label for the wins */
	private JLabel winLabel;

	/** label for the losses */
	private JLabel lossLabel;

	/** label for the amount of flags */
	private JLabel flagLabel;

	/** the game rules */
	private MineSweeperGame game;

	/** size of the board */
	private int size;

	/** handiling clicking */
	private ButtonListener listener;

	/** holds stats of the game */
	private JPanel topPanel;

	/** holds buttons for the game */
	private JPanel gamePanel;

	/** holds quit and newsize options */
	private JPanel botomPanel;

	/** an empty icon */
	private ImageIcon emptyIcon;

	/** holds the flag */
	private ImageIcon flagIcon;

	/** holds the bomb */
	private ImageIcon bombIcon;

	/*******************************************************************
	 * Panel constructor that creates the board and whole graphical part of the
	 * game.
	 ******************************************************************/
	public MineSweeperPanel() {

		// start size of the board
		size = 10;

		// ceates a ButtonListener
		listener = new ButtonListener();

		// creates a new MneSweeperGame
		game = new MineSweeperGame();

		// sets the ImageIcons to there images
		flagIcon = new ImageIcon("Flag50.png");
		bombIcon = new ImageIcon("Mine50.png");

		// creates the panels and settings for how the board looks
		this.setLayout(new BorderLayout());
		gamePanel = new JPanel(new GridLayout(size, size, 0, 0));
		gamePanel.setBorder(new EmptyBorder(10, 20, 0, 20));
		botomPanel = new JPanel();
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

		// creates modification options buttons and adds a ActionListener
		flag = new JRadioButton("Add/Remove Flag");
		quitButton = new JButton("Quit");
		newSize = new JButton("New Board");
		quitButton.addActionListener(listener);
		newSize.addActionListener(listener);

		// sets the JLabels for the status
		winLabel = new JLabel("Wins: " + game.getWin());
		winLabel.setFont(new Font("Serif", Font.PLAIN, 21));
		lossLabel = new JLabel("Losses: " + game.getLoss());
		lossLabel.setFont(new Font("Serif", Font.PLAIN, 21));
		flagLabel = new JLabel("Flags: " + game.getFlagCount());
		flagLabel.setFont(new Font("Serif", Font.PLAIN, 18));
		

		// adds modification options to botomPanel
		botomPanel.add(quitButton);
		botomPanel.add(flag);
		botomPanel.add(newSize);

		// adds status to topPanel
		topPanel.add(winLabel, BorderLayout.EAST);
		topPanel.add(flagLabel, BorderLayout.SOUTH);
		topPanel.add(lossLabel, BorderLayout.WEST);

		// adds all the panels to the board
		add(topPanel, BorderLayout.NORTH);
		add(gamePanel, BorderLayout.CENTER);
		add(botomPanel, BorderLayout.SOUTH);

		// creates the buttons for the gamePanel
		board = new JButton[size][size];

		// makes and puts the buttons for the gamePanel in the correct spots
		creatBoard();
	}

	/*******************************************************************
	 * Method that exposes the cell, sets flags, and sets the numbers inside of
	 * the button when clicked
	 ******************************************************************/
	private void displayBoard() {

		// loops through the board and resets it
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) {
				iCell = game.getCell(row, col);

				// exposes the button if it is clicked
				if (iCell.isExposed())
					board[row][col].setEnabled(false);
				else
					board[row][col].setEnabled(true);

				// sets button to nothing
				board[row][col].setIcon(emptyIcon);
				board[row][col].setText("");

				// adds an '*' icon to the button if it is a mine
				if (game.getMine(row, col))
					board[row][col].setText("*");

				// adds a flag to the button if it is flagged
				if (iCell.isFlagged()) {
					board[row][col].setIcon(flagIcon);
					board[row][col].setText("");
				}

				// adds the numbers to a cell if it is exposed
				if (iCell.isExposed()) {

					// does nothing if the cell had zero mines around it
					if (game.getMineCount(row, col) == 0) {

						// sets the mine count if more then a zero
					} else {
						board[row][col].setText(""
								+ game.getMineCount(row, col));
					}
				}
			}

		// stats of the games to the labels
		lossLabel.setText("Losses: " + game.getLoss());
		winLabel.setText("Wins: " + game.getWin());
		flagLabel.setText("Flags: " + game.getFlagCount());

		// adds the stats to the board
		topPanel.removeAll();
		topPanel.add(winLabel, BorderLayout.EAST);
		topPanel.add(flagLabel, BorderLayout.SOUTH);
		topPanel.add(lossLabel, BorderLayout.WEST);
	}

	/*******************************************************************
	 * Method that creates the number of buttons the user has entered, puts them
	 * into the correct spot on the board, and adds the ActionListener.
	 ******************************************************************/
	private void creatBoard() {

		// resets the gmaePanel
		gamePanel.removeAll();
		gamePanel.revalidate();
		gamePanel.setLayout(new GridLayout(size, size));
		board = new JButton[size][size];
		emptyIcon = new ImageIcon("");

		// creates the buttons
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = new JButton("", emptyIcon);
				board[row][col].setSize(20, 20);
				board[row][col].addActionListener(listener);
				gamePanel.add(board[row][col]);
			}
		}

		// sets a '*' in the button if it is a mine
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
				if (game.getMine(row, col))
					board[row][col].setText("*");

		// repaints the gamePanel
		gamePanel.repaint();
	}

	/*******************************************************************
	 * a ButtonListener class that implements an ActionListener. This class
	 * handles all of the controls to the game.
	 ******************************************************************/
	private class ButtonListener implements ActionListener {

		/***************************************************************
		 * Method that receives what button has been pressed and takes apropiat
		 * action to what should happen.
		 * 
		 * @param e
		 *            what button has been pressed
		 **************************************************************/
		public void actionPerformed(ActionEvent e) {

			// checks if any of the buttons were clicked
			for (int row = 0; row < size; row++)
				for (int col = 0; col < size; col++)

					// decides what button has been pressed
					if (board[row][col] == e.getSource()) {

						// sets a flag if the radio button is clicked
						if (flag.isSelected())
							game.setFlag(row, col);

						// selects it if not
						else
							game.select(row, col);
					}

			// if the game status is lost
			if (game.getGameStatus() == GameStatus.Lost) {

				// runs through the board
				for (int row = 0; row < size; row++)
					for (int col = 0; col < size; col++)

						// puts the bomb the buttons that are bombs
						if (game.getMine(row, col) && !game.isFlaged(row, col)) {
							board[row][col].setText("");
							board[row][col].setIcon(bombIcon);
						}

				JOptionPane.showMessageDialog(null, "You Lose");

				// resets the game
				game.reset();

				// makes and puts the buttons for the gamePanel in the correct
				// spots
				creatBoard();
			}

			// if the game status is won
			if (game.getGameStatus() == GameStatus.Won) {
				JOptionPane.showMessageDialog(null, "You Win!");

				// resets the game
				game.reset();

				// makes and puts the buttons for the gamePanel in the correct
				// spots
				creatBoard();
			}

			// if quit was selected
			if (quitButton == e.getSource()) {

				// exits the program
				System.exit(0);
			}

			// if new board button was selected
			if (e.getSource() == newSize) {

				// creates two JTextFields and an object holding them
				JTextField field1 = new JTextField();
				JTextField field2 = new JTextField();
				Object[] message = { "Size:", field1, "Mines:", field2 };

				// puts the user into having to push the cancel button
				int x = 0;
				while (x < 1) {

					// make sure OK_OPTION dose'nt get trpiped by errors
					int isFirst = 0;

					// displays the option pane so the user can make a selection
					int option = JOptionPane.showConfirmDialog(null, message,
							"Enter New Values", JOptionPane.OK_CANCEL_OPTION);

					try {

						// if the user has selected the ok button
						if (option == JOptionPane.OK_OPTION && isFirst == 0) {

							// puts what the user has entered into a int
							String text1 = field1.getText();
							String text2 = field2.getText();
							int newSize = Integer.parseInt(text1);
							int newMines = Integer.parseInt(text2);

							// make sure the new size is between 4 and 30
							if (newSize > 3 && newSize < 31) {

								// make sure mines don't exceed number of cells
								if (newMines < newSize * newSize) {
									size = newSize;
									x = 2;

									// resets the game with new information
									game.newBoard(newSize, newMines);

									// creates the buttons and gamePanel
									creatBoard();

									// shows error if to many mines
								} else {
									isFirst = 1;
									JOptionPane.showMessageDialog(null,
											"Please pick a smaller mine size");
								}

								// shows an error if to big or small of a size
							} else {
								isFirst = 1;
								JOptionPane.showMessageDialog(null,
										"Please pick a size between 4 and 30.");
							}
						}

						// shows an error if anything other then an int
					} catch (NumberFormatException fmt) {
						isFirst = 1;
						JOptionPane.showMessageDialog(null,
								"Please only enter integers.");
					}

					// gets the user out of the options
					if (option == JOptionPane.CANCEL_OPTION) {
						x = 2;
					}

				}
			}

			// reprints the board correctly
			displayBoard();
		}

	}
}
