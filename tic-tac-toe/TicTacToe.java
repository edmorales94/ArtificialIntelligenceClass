package tic_tac_toe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
	
	public static Random random = new Random();
	
	public static void main(String[] args) {
		Board board = new Board();
		Scanner keyboard = new Scanner(System.in);
		board.displayBoard();
		System.out.println("Select turn:\n#1 computer goes first(player X) or #2 user goes first(player O)");
		int choice = keyboard.nextInt();
		
		if(choice == 1) {//if the computer goes first, choose a random cell
			board.minmax(0, board.player_X);//get the best movement for the computer
			System.out.println("Computer chose position: " + board.computerMove);
			board.placeAMove(board.computerMove, board.player_X);//place the computer's move
			board.displayBoard();	
		}
		
		while(!board.isGameOver()) {//while the game hasn't ended
			boolean isMoveAllowed = true;//this will make sure we don't overlap a cell
			do {
				if(!isMoveAllowed) {//the cell was used already
					System.out.println("Cell is already occupied");
				}
				System.out.println("Your turn: ");//it's the users turn now
				Point userMove = new Point(keyboard.nextInt(), keyboard.nextInt());//get the coordinates from the user
				isMoveAllowed = board.placeAMove(userMove, board.player_O);//if move was successful, we return true
			}
			while(!isMoveAllowed);//if user's movement is not valid, ask for the coordinates again
			
			board.displayBoard();//print the board after a successful movement
			if(board.isGameOver()) {//if the game is over
				break;//there's no need to get a movement from the computer
			}
			
			board.minmax(0, board.player_X);//get the best movement for the computer
			System.out.println("Computer chose position: " + board.computerMove);
			board.placeAMove(board.computerMove, board.player_X);//place the computer's move
			board.displayBoard();	
		}//game is over 
		
		
		if(board.hasThisPlayerWon(board.player_X)) {
			System.out.println("You lost");
		}
		else if(board.hasThisPlayerWon(board.player_O)){
			System.out.println("You won!");
		}
		else {
			System.out.println("It was a draw");
		}
		keyboard.close();
	}
}
