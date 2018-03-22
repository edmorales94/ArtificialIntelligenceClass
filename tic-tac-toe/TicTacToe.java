package tic_tac_toe;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	
	public static Random random = new Random();
	static PrintWriter writerForFile = null;
	
/*******************************************************************************************************************
 * This method will print the board to the file 
 * @param board
 */
	public static void printTheBoardToFile(Board board) {
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 3; col++) {
				String value = "?";
				if(board.board[row][col] == board.player_X) {
					value = "X";
				}
				else if(board.board[row][col] == board.player_O) {
					value = "O";
				}
				writerForFile.print(value + " ");
			}
			writerForFile.println();
		}
		writerForFile.println();
	}
	
//---------- main --------------------------------------------------------------------------------------------------
	public static void main(String[] args) {
		try {
			writerForFile = new PrintWriter("result.txt", "UTF-8");
			writerForFile.println("Tic-Tac-Toe");
			writerForFile.println();
			
			Board board = new Board();
			Scanner keyboard = new Scanner(System.in);
			printTheBoardToFile(board);
			board.displayBoard();
			System.out.println("Select turn:\n#1 computer goes first(player X) or #2 user goes first(player O)");
			int choice = keyboard.nextInt();
			
			if(choice == 1) {//if the computer goes first, choose a random cell
				int x = random.nextInt(3);
				int y = random.nextInt(3);
				Point point = new Point(x, y);
				board.placeAMove(point, board.player_X);//place the computer's move
				writerForFile.println("Computer chose position(Randomly): [" + x + ", " + y + "]");
				printTheBoardToFile(board);
				board.displayBoard();	
			}
			
			while(!board.isGameOver()) {//while the game hasn't ended
				boolean isMoveAllowed = true;//this will make sure we don't overlap a cell
				do {
					if(!isMoveAllowed) {//the cell was used already
						System.out.println("Cell is already occupied");
					}
					System.out.println("Your turn: ");//it's the users turn now
					board.minmaxUser(0, board.player_O);//get the best movement for the computer
					writerForFile.println("You should choose position(minmaxUser): " + board.userMove);
					System.out.println("You should choose position: " + board.userMove);
					Point userMove = new Point(keyboard.nextInt(), keyboard.nextInt());//get the coordinates from the user
					isMoveAllowed = board.placeAMove(userMove, board.player_O);//if move was successful, we return true
					writerForFile.println("User chose position: " + userMove);
				}
				while(!isMoveAllowed);//if user's movement is not valid, ask for the coordinates again
				
				printTheBoardToFile(board);
				board.displayBoard();//print the board after a successful movement
				if(board.isGameOver()) {//if the game is over
					break;//there's no need to get a movement from the computer
				}
				
				board.minmax(0, board.player_X);//get the best movement for the computer
				System.out.println("Computer chose position: " + board.computerMove);
				board.placeAMove(board.computerMove, board.player_X);//place the computer's move
				writerForFile.println("Computer chose position(using minmax): " + board.computerMove);
				printTheBoardToFile(board);
				board.displayBoard();	
			}//game is over 
			
			
			if(board.hasThisPlayerWon(board.player_X)) {
				writerForFile.print("Computer won! you lost");
				System.out.print("You lost");
			}
			else if(board.hasThisPlayerWon(board.player_O)){
				writerForFile.print("You won! computer lost");
				System.out.print("You won!");
			}
			else {
				writerForFile.print("It was a draw");
				System.out.print("It was a draw");
			}
			keyboard.close();
			writerForFile.close();
		} 
		
		catch (FileNotFoundException e) {
			System.out.println("No such file exists.");
		} 
		catch (UnsupportedEncodingException e) {
			System.out.println("Couldn't create file");
		}
	}
}
