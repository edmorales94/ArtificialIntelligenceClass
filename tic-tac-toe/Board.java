package tic_tac_toe;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	public static final int no_player = 0;
	public static final int player_X = 1;
	public static final int player_O = 2;
	
	private int[][] board = new int[3][3];
	public Point computerMove;
	
/********************************************************************************************************************
 * This method checks the diagonals, rows, and columns
 * @param player
 * @return True if a diagonal, row, or a column is already full with the player's symbol
 */
	public boolean hasThisPlayerWon(int player) {
		if((board[0][0] == board[1][1] && board[0][0]== board[2][2] && board[0][0] == player)//diagonal left-right
				|| 
		   (board[0][2] == board[1][1] && board[0][2]== board[2][0] && board[0][2] == player)) {//diagonal right-left
			return true;//true if either diagonal has been fullfilled by a single player
		}
		
		for(int index = 0; index < 3; index++) {
			//this if statement checks for the rows
			if((board[index][0] == board[index][1] && board[index][0] == board[index][2] && board[index][0] == player)
				||
			//this if statement checks for the columns
			(board[0][index] == board[1][index] && board[0][index] == board[2][index] && board[0][index] == player)){
				return true;//true if either a row or a column has been fullfilled by a single player
			}
		}
		return false;//false otherwise
	}
	
	
/********************************************************************************************************************
 * This method will get the cells(positions) that will be evaluated by the minmax algorithm
 * @return A list with the position of available blocks in the board
 */
	public List<Point> getAvailableBlocks(){
		List<Point> blocksAvailable = new ArrayList<Point>();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == no_player) {//if the block hasn't been used by a player
					blocksAvailable.add(new Point(i,j));//add the coordinates
				}
			}
		}
		return blocksAvailable;//return all the blocks that are available/empty
	}
	
	
/********************************************************************************************************************
 * This method will determine if the game is over by checking the rows, columns, or diagonals
 * @return True if player X or player O have won, or if the board is full and no one won
 */
	public boolean isGameOver() {
		return hasThisPlayerWon(player_X) || hasThisPlayerWon(player_O) || getAvailableBlocks().isEmpty();
	}
	
	
/********************************************************************************************************************
 * This method will put the player's move into the board
 * @param point
 * @param player
 * @return True if the block where we want to place our move is available. False if is occupied.
 */
	public boolean placeAMove(Point point, int player) {
		if(board[point.x][point.y] != no_player) {//if the cell is not available
			return false;
		}
		
		board[point.x][point.y] = player;//the cell was available
		return true;
	}
	
	
/********************************************************************************************************************
 * This method will print out the value(X or O) of the board
 */
	public void displayBoard() {
		System.out.println();
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				String value = "?";
				if(board[row][col] == player_X) {
					value = "X";
				}
				else if(board[row][col] == player_O) {
					value = "O";
				}
				System.out.println(value + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
