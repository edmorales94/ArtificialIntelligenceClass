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
		
		if(choice == 1) {
			Point p = new Point(random.nextInt(3), random.nextInt(3));
			board.placeAMove(p, board.player_X);
			board.displayBoard();
		}
		
		while(!board.isGameOver()) {
			boolean isMoveAllowed = true;
			do {
				if(!isMoveAllowed) {
					System.out.println("Cell is already occupied");
				}
				System.out.println("Your turn: ");
				Point userMove = new Point(keyboard.nextInt(), keyboard.nextInt());
				isMoveAllowed = board.placeAMove(userMove, board.player_O);
			}
			while(!isMoveAllowed);
			board.displayBoard();
			if(board.isGameOver()) {
				break;
			}
			
			board.minmax(0, board.player_X);
			System.out.println("Computer chose position: " + board.computerMove);
		}
	}
}
