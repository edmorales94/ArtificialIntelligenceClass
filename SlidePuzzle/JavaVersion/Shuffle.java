package SlidePuzzleClasses;

import java.util.ArrayList;
import java.util.Random;

public class Shuffle {
	static int emptyBlockIndex;
	static int lastStep = 10;
	static ArrayList<Integer> exclude = new ArrayList<Integer>();
	
	public static int generateRandom(Random rnd, int start, int end, ArrayList<Integer> exclude){
		int random  = start + rnd.nextInt(end - start + 1 - exclude.size());
		for(int ex: exclude){
			if( random < ex){
				break;
			}
			random++;
		}
		return random;
	}
	
	public static int findBlankSpace(int[] board){
		int index = 0;
		for (int i = 0; i < board.length; i++) {
			if(board[i] == 16){
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static void findIllegalMovements(int[] board){
		exclude.clear();
		emptyBlockIndex = findBlankSpace(board);
		
		//--------possibilities of going up----------------------------
		if(emptyBlockIndex - 4 >= 0){//if we can go up
			if(lastStep == 2){// if last step was down
				exclude.add(0);//we shouldn't go up
			}
		}
		if(emptyBlockIndex - 4 < 0){//if we can't go up
			exclude.add(0);//exclude it from our choices
		}
		
		//--------possibilities of going to the right -----------------
		if(emptyBlockIndex%4 < 3){//if we can go right
			if(lastStep == 3){//if last step was left
				exclude.add(1);//we should go to the right
			}
		}
		
		if(emptyBlockIndex%4 == 3){//if we can't go to the right
			exclude.add(1);//exclude it from the options
		}
		
		//---------possibilities of going down ------------------------
		if(emptyBlockIndex + 4 <= 15){//if we can go down
			if(lastStep == 0){//but last step was up
				exclude.add(2);//we shouldn't go up
			}
		}
		
		if(emptyBlockIndex + 4 > 15){//if we can't go down
			exclude.add(2);//exclude it from the options
		}
		
		//---------possibilities of going to the left -----------------
		if(emptyBlockIndex%4 > 0){//if we can go left
			if(lastStep == 1){//but last step was to the right
				exclude.add(3);//we shouldn't go to the left
			}
		}
		
		if(emptyBlockIndex%4 == 0){// if we can't go left
			exclude.add(3);//excluded it from the options
		}
	}
	
	public static int[] shuffleBoard(int[] board){
		Random rnd = new Random();
		for(int i = 0; i < 10; i++){
			findIllegalMovements(board);
			int direction = generateRandom(rnd, 0, 3, exclude);
			
			if(direction == 0){
				moveUp(board, emptyBlockIndex);
			}
			else if(direction == 1){
				moveRight(board, emptyBlockIndex);
			}
			else if(direction == 2){
				moveDown(board, emptyBlockIndex);
			}
			else if(direction == 3){
				moveLeft(board, emptyBlockIndex);
			}
		}
		return board;
	}
	
	public static void moveUp(int[] board, int blankBlockIndex){
		if(blankBlockIndex - 4 >= 0){
			int temp = board[blankBlockIndex-4];
			board[blankBlockIndex-4] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			lastStep = 0;
			System.out.println("Moved up");
		}
	}
	
	public static void moveRight(int[]board, int blankBlockIndex){
		if(blankBlockIndex%4 < 3){
			int temp = board[blankBlockIndex + 1];
			board[blankBlockIndex + 1] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			lastStep = 1;
			System.out.println("Moved right");
		}
	}
	
	public static void moveDown(int [] board, int blankBlockIndex){
		if(blankBlockIndex + 4 <= 15){
			int temp = board[blankBlockIndex + 4];
			board[blankBlockIndex + 4] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			lastStep = 2;
			System.out.println("Moved down");
		}
	}
	
	public static void moveLeft(int[] board, int blankBlockIndex){
		if(blankBlockIndex%4 > 0){
			int temp = board[blankBlockIndex-1];
			board[blankBlockIndex-1] = board[blankBlockIndex];
			board[blankBlockIndex] = temp;
			lastStep = 3;
			System.out.println("Moved left");
		}
	}
}
