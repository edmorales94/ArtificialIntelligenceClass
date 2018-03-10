package SlidePuzzle2;

import java.util.ArrayList;
import java.util.Random;

public class Shuffle {
	static int emptyBlockIndex;
	static int lastStep = 10;
	static ArrayList<Integer> exclude = new ArrayList<Integer>();
	
/*************************************************************************************************
 * This method will return a random number from start to end inclusively, but will exclude the 
 * numbers inside the ArrayList exclude. For example, if 2 is excluded, then 0,1, or 3 can be 
 * return only.
 * @param rnd
 * @param start
 * @param end
 * @param exclude
 * @return An int that's not in the excluded list
 */
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
	
/*************************************************************************************************
 * This method will go through the board array and return the index where the empty space is 
 * located
 * @param board
 * @return Index of the empty space in the array
 */
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
	
/*************************************************************************************************
 * This method will fing the empty space position and find out all the place where it could move.
 * If the empty espace can't go to the left then the number 3, representing the left movement, 
 * will be place in the excluded list so that when we shuffle, we won't take into consideration
 * the left movement
 * @param board
 */
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
	
/*************************************************************************************************
 * This method will get the intial goal(in the goal state) and move the empty space randomly
 * avoiding cancelling out movements. If last movement was up, the next movements to considered 
 * will be up again(if allowed), right(if allowed), and left(if allowed). Down was excluded 
 * because if we go down, we will cancel out the previous movement and return to the initial 
 * location
 * @param board
 * @return The board shuffled 10 times
 */
	public static int[] shuffleBoard(int[] board){
		Random rnd = new Random();
		for(int i = 0; i < 7; i++){
			findIllegalMovements(board);//exclude the movements we shouldn't consider
			int direction = generateRandom(rnd, 0, 3, exclude);//get a random number from 0 to 3 
														//excluding the movements in the list
			
			if(direction == 0){//move up if allowed
				moveUp(board, emptyBlockIndex);
			}
			else if(direction == 1){//move to the right if allowed
				moveRight(board, emptyBlockIndex);
			}
			else if(direction == 2){//move down if allwed
				moveDown(board, emptyBlockIndex);
			}
			else if(direction == 3){//move to the left if allowed
				moveLeft(board, emptyBlockIndex);
			}
		}
		return board;
	}
	
/*************************************************************************************************
 * This method will swap the empty space with the block above it
 * @param board
 * @param blankBlockIndex
 */
	public static void moveUp(int[] board, int blankBlockIndex){
		int temp = board[blankBlockIndex-4];
		board[blankBlockIndex-4] = board[blankBlockIndex];
		board[blankBlockIndex] = temp;
		lastStep = 0;
		//System.out.println("Moved up");
	}
	
/*************************************************************************************************
 * This method will swap the empty space with the block to its right
 * @param board
 * @param blankBlockIndex
 */
	public static void moveRight(int[]board, int blankBlockIndex){
		int temp = board[blankBlockIndex + 1];
		board[blankBlockIndex + 1] = board[blankBlockIndex];
		board[blankBlockIndex] = temp;
		lastStep = 1;
		//System.out.println("Moved right");
	}
	
/*************************************************************************************************
 * This method will swap the empty space with the block below it
 * @param board
 * @param blankBlockIndex
 */
	public static void moveDown(int [] board, int blankBlockIndex){
		int temp = board[blankBlockIndex + 4];
		board[blankBlockIndex + 4] = board[blankBlockIndex];
		board[blankBlockIndex] = temp;
		lastStep = 2;
		//System.out.println("Moved down");
	}
	
/*************************************************************************************************
 * This method will swap the empty space with the block to its left
 * @param board
 * @param blankBlockIndex
 */
	public static void moveLeft(int[] board, int blankBlockIndex){
		int temp = board[blankBlockIndex-1];
		board[blankBlockIndex-1] = board[blankBlockIndex];
		board[blankBlockIndex] = temp;
		lastStep = 3;
		//System.out.println("Moved left");
	}
}
