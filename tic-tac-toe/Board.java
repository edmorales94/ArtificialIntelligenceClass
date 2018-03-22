package tic_tac_toe;

import java.util.ArrayList;
import java.util.List;

public class Board {
	
	public final int no_player = 0;
	public final int player_X = 1;
	public final int player_O = 2;
	
	public int[][] board = new int[3][3];
	public Point computerMove;
	public Point userMove;
	
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
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				String value = "?";
				if(board[row][col] == player_X) {
					value = "X";
				}
				else if(board[row][col] == player_O) {
					value = "O";
				}
				System.out.print(value + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
/*******************************************************************************************************************
 * This method will run recursively to get the best move for the computer
 * @param depth
 * @param turn
 * @return The max value if it was the computer's turn, 
 */
	public int minmax(int depth, int turn) {
		if(hasThisPlayerWon(player_X)) {
			return 1;
		}
		if(hasThisPlayerWon(player_O)) {
			return -1;
		}
		
		List<Point> availableMoves = getAvailableBlocks();
		if(availableMoves.isEmpty()) {
			return 0;//the board is full, game over
		}
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for (Point point : availableMoves) {//get a list of available moves
			
			//---------- code for the X player ---------------------------------------------------------------------
			if(turn == player_X){ //if it's the computer turns
				placeAMove(point, player_X);//mark the cell with the X
				int currentScore = minmax(depth + 1, player_O);//do a recursion and check the next movement for O
				max = Math.max(currentScore, max);//update max with the highest int
				if(depth == 0) {
					//System.out.println("Computer score for position " + point + " = " + currentScore);
				}
				if(currentScore >= 0) {// we can win or get a draw 
					if(depth == 0) {
						computerMove = point;
					}
				}
				
				if(currentScore == 1) {//This player X has won 
					board[point.x][point.y] = no_player;//clean the board
					break;//stop exploring movements
				}
				
				//if there are no good movements, just take the last available move
				if(point.equals(availableMoves.get(availableMoves.size()-1)) && max < 0) {
					if(depth == 0) {
						computerMove = point;
					}
				}
			}
			
			
			//---------- code for the O player ---------------------------------------------------------------------
			else if(turn == player_O) {
				placeAMove(point, player_O);//place a possible move
				int currentScore = minmax(depth + 1, player_X);//get the minmax for the future movements of X
				min = Math.min(currentScore, min);//get the smallest value of those two
				
				if(min == -1) {//This player O has won
					board[point.x][point.y] = no_player;//clean the board
					break;//stop exploring movements
				}
			}
			board[point.x][point.y] = no_player;//cleaning the board again once everything is done
		}
		return turn == player_X ? max : min;//if it's player X's turn, return the max. Else, return the min
	}
	

/*******************************************************************************************************************
 * This method will run recursively to get the best move for the user
 * @param depth
 * @param turn
 * @return
 */
	public int minmaxUser(int depth, int turn) {
		if(hasThisPlayerWon(player_X)) {
			return -1;
		}
		if(hasThisPlayerWon(player_O)) {
			return 1;
		}
		
		List<Point> availableMoves = getAvailableBlocks();
		if(availableMoves.isEmpty()) {
			return 0;//the board is full, game over
		}
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		
		for (Point point : availableMoves) {//get a list of available moves
			
			//---------- code for the O player ---------------------------------------------------------------------
			if(turn == player_O){ //if it's the user's turns
				placeAMove(point, player_O);//mark the cell with the O
				int currentScore = minmaxUser(depth + 1, player_X);//do a recursion and check the next movement for X
				max = Math.max(currentScore, max);//update max with the highest int
				if(depth == 0) {
					//System.out.println("User score for position " + point + " = " + currentScore);
				}
				if(currentScore >= 0) {// we can win or get a draw 
					if(depth == 0) {
						userMove = point;
					}
				}
				
				if(currentScore == 1) {//This player O has won 
					board[point.x][point.y] = no_player;//clean the board
					break;//stop exploring movements
				}
				
				//if there are no good movements, just take the last available move
				if(point.equals(availableMoves.get(availableMoves.size()-1)) && max < 0) {
					if(depth == 0) {
						userMove = point;
					}
				}
			}
			
			
			//---------- code for the X player ---------------------------------------------------------------------
			else if(turn == player_X) {
				placeAMove(point, player_X);//place a possible move
				int currentScore = minmaxUser(depth + 1, player_O);//get the minmax for the future movements of O
				min = Math.min(currentScore, min);//get the smallest value of those two
				
				if(min == -1) {//This player X has won
					board[point.x][point.y] = no_player;//clean the board
					break;//stop exploring movements
				}
			}
			board[point.x][point.y] = no_player;//cleaning the board again once everything is done
		}
		return turn == player_O ? max : min;//if it's player X's turn, return the max. Else, return the min
	}
}
