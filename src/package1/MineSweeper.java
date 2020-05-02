/***********************************************************************
 *The start to the game mine sweeper.
 *
 * @author Nicholas Reitz
 * @version February 10 2014
 **********************************************************************/
package package1;

import javax.swing.JFrame;

public class MineSweeper {

	/*******************************************************************
	 * Main method that creates the a MineSweeperPanel and adds it to a frame.
	 ******************************************************************/
	public static void main(String[] args) {
		JFrame frame = new JFrame("MineSweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MineSweeperPanel p = new MineSweeperPanel();
		frame.getContentPane().add(p);

		frame.setSize(600, 600);

		frame.setVisible(true);
	}
}
