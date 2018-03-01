package SlidePuzzleClasses;

import java.util.Random;

public class Shuffle {
	static int emptyBlockIndex = 15;
	static int lastStep = 10;
	
	public static int[] shuffleBoard(int[] board){
		Random randomNumber = new Random();
		int lastStep = 10;
		for(int i = 0; i < 10; i++){
			int direction = randomNumber.nextInt(4);//numbers from 0 to 3
			
			if(direction == 0){
				if(lastStep != 2){
					moveUp(board, emptyBlockIndex);
				}
				else{
					moveLeft(board, emptyBlockIndex);
				}
			}
			else if(direction == 1){
				if(lastStep != 3){
					moveRight(board, emptyBlockIndex);
				}
				else{
					moveDown(board, emptyBlockIndex);
				}
			}
			else if(direction == 2){
				if(lastStep != 0){
					moveDown(board, emptyBlockIndex);
				}
				else{
					moveRight(board, emptyBlockIndex);
				}
			}
			else if(direction == 3){
				if(lastStep != 1){
					moveLeft(board, emptyBlockIndex);
				}
				else{
					moveUp(board, emptyBlockIndex);
				}
			}
		}
		return board;
	}
	
	public static void moveUp(int[] board, int blankBlockIndex){
		if(blankBlockIndex - 4 >= 0){
			int temp = board[blankBlockIndex-4];
			board[blankBlockIndex-4] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			emptyBlockIndex = blankBlockIndex - 4;
			lastStep = 0;
		}
	}
	
	public static void moveRight(int[]board, int blankBlockIndex){
		if(blankBlockIndex%4 < 3){
			int temp = board[blankBlockIndex + 1];
			board[blankBlockIndex + 1] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			emptyBlockIndex = blankBlockIndex + 1;
			lastStep = 1;
		}
	}
	
	public static void moveDown(int [] board, int blankBlockIndex){
		if(blankBlockIndex + 4 <= 15){
			int temp = board[blankBlockIndex + 4];
			board[blankBlockIndex + 4] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			emptyBlockIndex = blankBlockIndex + 4;
			lastStep = 2;
		}
	}
	
	public static void moveLeft(int[] board, int blankBlockIndex){
		if(blankBlockIndex%4 > 0){
			int temp = board[blankBlockIndex-1];
			board[blankBlockIndex-1] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			emptyBlockIndex = blankBlockIndex - 1;
			lastStep = 3;
		}
	}
}
