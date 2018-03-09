package SlidePuzzle2;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Node {
	Node parent = null;
	ArrayList<Node> children = new ArrayList<Node>();
	int[] puzzleBoard = new int[16];
	int[] goalBoard = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
	int blankSpaceCoordinates = -1;
	String direction = "";
	int depthLevel = 0;//used for the IDDFS method
	int nodeScore = 0;
	
/**************************************************************************************************
 * Constructor takes a board as a parameter 
 * @param board
 */
	public Node(int[] board){
		//copy the board given to this node's puzzle
		for(int index = 0; index < puzzleBoard.length; index++){
			puzzleBoard[index] = board[index];
		}
	}
	
/**************************************************************************************************
 * isGoardReached() will determine if this current node is the end goal of the game
 * @return True if all the current elements in the puzzle are in ascending order from 1-16.
 * False if the one element is out of its place.
 */
	public boolean isGoalReached(){
		for(int index = 0; index < puzzleBoard.length; index++){
			//if the current node is not the goal, we return false
			if(this.puzzleBoard[index] != this.goalBoard[index]){
				return false;
			}
		}
		return true;
	}
	
/**************************************************************************************************
 * This method will copy the originalBoard to copyTo board
 * @param copyTo
 * @param originalBoard
 */
	public void copyPuzzle(int[]copyTo, int[]originalBoard){
		for(int index = 0; index < originalBoard.length; index++){
			copyTo[index] = originalBoard[index];
		}
	}
	
/**************************************************************************************************
 * moveUp will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param blankBlockIndex
 */
	public void moveUp(int[] board, int blankBlockIndex){
		if(blankBlockIndex - 4 >= 0){
			int[] childBoard = new int[16];
			copyPuzzle(childBoard, board);
			int temp = board[blankBlockIndex-4];
			childBoard[blankBlockIndex-4] = childBoard[blankBlockIndex];
			childBoard[blankBlockIndex] = temp;
			
			Node child = new Node(childBoard);
			child.parent = this;
			child.direction = "N";
			//f(n) = g(n) + h(n) -> g(n) = distanceFromPreviousNode = 1
			//h(n) = manhattan distance of each node 
			child.nodeScore = 1 + manhattanDistance(child.puzzleBoard);
			children.add(child);
		}
	}
	
/**************************************************************************************************
 * moveRight will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveRight(int[]board, int blankBlockIndex){
		if(blankBlockIndex%4 < 3){
			int[] childBoard = new int[16];
			copyPuzzle(childBoard, board);
			int temp = board[blankBlockIndex + 1];
			childBoard[blankBlockIndex + 1] = childBoard[blankBlockIndex];
			childBoard[blankBlockIndex] = temp;
			
			Node child = new Node(childBoard);
			child.parent = this;
			child.direction = "E";
			//f(n) = g(n) + h(n) -> g(n) = distanceFromPreviousNode = 1
			//h(n) = manhattan distance of each node 
			child.nodeScore = 1 + manhattanDistance(child.puzzleBoard);
			children.add(child);
			}
	}
	
/**************************************************************************************************
 * moveRight will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveDown(int[] board, int blankBlockIndex){
		if(blankBlockIndex + 4 <= 15){
			int[] childBoard = new int[16];
			copyPuzzle(childBoard, board);
			int temp = board[blankBlockIndex + 4];
			childBoard[blankBlockIndex + 4] = childBoard[blankBlockIndex];
			childBoard[blankBlockIndex] = temp;
			
			Node child = new Node(childBoard);
			child.parent = this;
			child.direction = "S";
			//f(n) = g(n) + h(n) -> g(n) = distanceFromPreviousNode = 1
			//h(n) = manhattan distance of each node 
			child.nodeScore = 1 + manhattanDistance(child.puzzleBoard);
			children.add(child);
		}
	}
	
/**************************************************************************************************
 * moveLeft will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveLeft(int[] board, int blankBlockIndex){
		if(blankBlockIndex%4 > 0){
			int[] childBoard = new int[16];
			copyPuzzle(childBoard, board);
			int temp = board[blankBlockIndex-1];
			childBoard[blankBlockIndex-1] = childBoard[blankBlockIndex];
			childBoard[blankBlockIndex] = temp;
			
			Node child = new Node(childBoard);
			child.parent = this;
			child.direction = "W";
			//f(n) = g(n) + h(n) -> g(n) = distanceFromPreviousNode = 1
			//h(n) = manhattan distance of each node 
			child.nodeScore = 1 + manhattanDistance(child.puzzleBoard);
			children.add(child);
		}
	}
	
/*************************************************************************************************
 * printPuzzle method will print the puzzle in this current node
 */
	public void printPuzzle(){
		String board = "";
		for(int i = 0; i < puzzleBoard.length; i++){
			String element = Integer.toString(puzzleBoard[i]);
			if(element.length()==1){
				board += "  " + element;
			}
			else if(element.length() >1){
				board += " " + element;
			}
			
			if(i %4 == 3){
				board += "\n";
			}
		}
		System.out.println(board+"\n");
	}
		
/*************************************************************************************************
 * This method checks whether the current node is the same as another. This method will help us
 * in the search method. We will avoid an endless loop by adding a node similar to a previous one.
 * @param board
 * @return
 */
	public boolean isSamePuzzle(int[] board){
		for (int i = 0; i < puzzleBoard.length; i++) {
			if(puzzleBoard[i] != board[i]){
				return false;
			}
		}
		return true;
	}
	
/*************************************************************************************************
 * expandNode will call the move methods, each move method creates a child node which copies its 
 * parent's puzzle and then the movement is done to each child respectively	
 */
	public void expandNode(){
		for (int i = 0; i < puzzleBoard.length; i++) {
			if(puzzleBoard[i] == 16){
				this.blankSpaceCoordinates = i;
				break;
			}
		}
		moveUp(puzzleBoard, blankSpaceCoordinates);
		moveRight(puzzleBoard, blankSpaceCoordinates);
		moveDown(puzzleBoard, blankSpaceCoordinates);
		moveLeft(puzzleBoard, blankSpaceCoordinates);
	}
	
/************************************************************************************************
 * This method will count how many positions a tile is away from its goal position. We ignore
 * the empty space(16)
 * @param tile
 * @param itsRow
 * @param itsCol
 * @return
 */
	public int individualTileDistance(int tile, int itsRow, int itsCol){
		if(tile != 16){
			int row = -1;
			int col = -1;
			int[][] goal = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
			start:{
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 4; j++){
						if(goal[i][j] == tile){
							row = i;
							col = j;
							break start;
						}
					}
				}
			}
			return Math.abs(row - itsRow) + Math.abs(col - itsCol);
		}
		return 0;
	}
	
/************************************************************************************************
 * This method will add all the individual distances together and return the final Manhattan
 * distance of the whole current puzzle
 * @param thePuzzle
 * @return
 */
	public int manhattanDistance(int[] thePuzzle){
		int puzzleDistance = 0;
		for(int i = 0; i < thePuzzle.length; i++){
			int currentTile = thePuzzle[i];
			int itsRow = -1;
			int itsColumn = -1;
			if(i%4 == 0){//first column
				if(i == 0){
					itsRow = 0;
					itsColumn = 0;
				}
				else if(i == 4){
					itsRow = 1;
					itsColumn = 0;
				}
				else if(i == 8){
					itsRow = 2;
					itsColumn = 0;
				}
				else if( i == 12){
					itsRow = 3;
					itsColumn = 0;
				}
			}
			
			else if(i%4 == 1){//second column
				if(i == 1){
					itsRow = 0;
					itsColumn = 1;
				}
				else if(i == 5){
					itsRow = 1;
					itsColumn = 1;
				}
				else if( i == 9){
					itsRow = 2;
					itsColumn = 1;
				}
				else if(i == 13){
					itsRow = 3;
					itsColumn = 1;
				}
			}
			
			else if(i%4 == 2){//third column
				if(i == 2){
					itsRow = 0;
					itsColumn = 2;
				}
				else if(i == 6){
					itsRow = 1;
					itsColumn = 2;
				}
				else if( i == 10){
					itsRow = 2;
					itsColumn = 2;
				}
				else if(i == 14){
					itsRow = 3;
					itsColumn = 2;
				}
			}
			
			else if(i%4 == 3){//forth column
				if(i == 3){
					itsRow = 0;
					itsColumn = 3;
				}
				else if(i == 7){
					itsRow = 1;
					itsColumn = 3;
				}
				else if( i == 11){
					itsRow = 2;
					itsColumn = 3;
				}
				else if(i == 15){
					itsRow = 3;
					itsColumn = 3;
				}
			}	
			puzzleDistance += individualTileDistance(currentTile, itsRow, itsColumn);
		}
		return puzzleDistance;
	}
	
//---------- main method ------------------------------------------------------------------------
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		int[] board = {1,2,3,4,
					   5,6,7,8,
					   9,10,11,12,
					   13,14,15,16};
		
		board = Shuffle.shuffleBoard(board);
		Node root = new Node(board);
		root.printPuzzle();
		SearchType use = new SearchType(root);
		use.BFS();
		use.AStarSearch();
	}
}
