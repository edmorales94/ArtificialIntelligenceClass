package SlidePuzzleClasses;
import java.util.ArrayList;

public class Node {
	Node parent = null;
	ArrayList<Node> children = new ArrayList<Node>();
	int[][] puzzleBoard = new int[4][4];
	int[][] goalBoard = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
	int[] blankSpaceCoordinates = new int[2];
	String direction = "";
	
/**************************************************************************************************
 * Constructor takes a board as a parameter 
 * @param board
 */
	public Node(int[][] board){
		//copy the board given to this node's puzzle
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				puzzleBoard[row][col] = board[row][col];
			}
		}
	}
	
/**************************************************************************************************
 * isGoardReached() will determine if this current node is the end goal of the game
 * @return
 */
	public boolean isGoalReached(){
		boolean goalReached = true;
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				//if the current node is not the goal, we return false
				if(this.puzzleBoard[row][col] != this.goalBoard[row][col]){
					goalReached = false;
					return goalReached;
				}
			}
		}
		return goalReached;
	}
	
/**************************************************************************************************
 * This method will copy the originalBoard to copyTo board
 * @param copyTo
 * @param originalBoard
 */
	public void copyPuzzle(int[][]copyTo, int[][]originalBoard){
		for(int row = 0; row < 4; row++){
			for(int col = 0; col < 4; col++){
				copyTo[row][col] = originalBoard[row][col];
			}
		}
	}
	
/**************************************************************************************************
 * moveUp will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveUp(int[][]board, int spaceRow, int spaceColumn){
		if(spaceRow > 0){
			int[][] childBoard = new int[4][4];
			copyPuzzle(childBoard, board);
			int temp = board[spaceRow-1][spaceColumn];
			childBoard[spaceRow-1][spaceColumn] = childBoard[spaceRow][spaceColumn];
			childBoard[spaceRow][spaceColumn] = temp;
			
			Node child = new Node(childBoard);
			children.add(child);
			child.parent = this;
			child.direction = "U";
		}
	}
	
/**************************************************************************************************
 * moveRight will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveRight(int[][]board, int spaceRow, int spaceColumn){
		if(spaceColumn < 3){
			int[][] childBoard = new int[4][4];
			copyPuzzle(childBoard, board);
			int temp = board[spaceRow][spaceColumn+1];
			childBoard[spaceRow][spaceColumn+1] = childBoard[spaceRow][spaceColumn];
			childBoard[spaceRow][spaceColumn] = temp;
			
			Node child = new Node(childBoard);
			children.add(child);
			child.parent = this;
			child.direction = "R";
		}
	}
	
/**************************************************************************************************
 * moveRight will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveDown(int[][]board, int spaceRow, int spaceColumn){
		if(spaceRow < 3){
			int[][] childBoard = new int[4][4];
			copyPuzzle(childBoard, board);
			int temp = board[spaceRow+1][spaceColumn];
			childBoard[spaceRow+1][spaceColumn] = childBoard[spaceRow][spaceColumn];
			childBoard[spaceRow][spaceColumn] = temp;
			
			Node child = new Node(childBoard);
			children.add(child);
			child.parent = this;
			child.direction = "D";
		}
	}
	
/**************************************************************************************************
 * moveLeft will copy the given board to a childBoard, then perform the movement in the child board
 * so we can preserve the parent
 * @param board
 * @param spaceRow
 * @param spaceColumn
 */
	public void moveLeft(int[][]board, int spaceRow, int spaceColumn){
		if(spaceColumn > 0){
			int[][] childBoard = new int[4][4];
			copyPuzzle(childBoard, board);
			int temp = board[spaceRow][spaceColumn-1];
			childBoard[spaceRow][spaceColumn-1] = childBoard[spaceRow][spaceColumn];
			childBoard[spaceRow][spaceColumn] = temp;
			
			Node child = new Node(childBoard);
			children.add(child);
			child.parent = this;
			child.direction = "L";
		}
	}
	
/*************************************************************************************************
 * printPuzzle method will print the puzzle in this current node
 */
	public void printPuzzle(){
		String board = "";
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				String element = Integer.toString(puzzleBoard[i][j]);
				if(element.length()==1){
					board += "  " + element;
				}
				else if(element.length() >1){
					board += " " + element;
				}
			}
			board += "\n";
		}
		System.out.print(board+"\n");
	}
		
/*************************************************************************************************
 * This method checks whether the current node is the same as another. This method will help us
 * in the search method. We will avoid an endless loop by adding a node similar to a previous one.
 * @param board
 * @return
 */
	public boolean isSamePuzzle(int[][] board){
		boolean isSame = true;
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(puzzleBoard[i][j] != board[i][j]){
					return false;
				}
			}
		}
		return isSame;
	}
	
/*************************************************************************************************
 * expandNode will call the move methods, each move method creates a child node which copies its 
 * parent's puzzle and then the movement is done to each child respectively	
 */
	public void expandNode(){
		start:{
			for(int row = 0; row < 4; row++){
				for(int col = 0; col < 4; col++){
					if(puzzleBoard[row][col] == 16){
						blankSpaceCoordinates[0] = row;
						blankSpaceCoordinates[1] = col;
						break start;
					}
				}
			}
		}
		moveUp(puzzleBoard, blankSpaceCoordinates[0], blankSpaceCoordinates[1]);
		moveRight(puzzleBoard, blankSpaceCoordinates[0], blankSpaceCoordinates[1]);
		moveDown(puzzleBoard, blankSpaceCoordinates[0], blankSpaceCoordinates[1]);
		moveLeft(puzzleBoard, blankSpaceCoordinates[0], blankSpaceCoordinates[1]);
	}
	
	public static void main(String[] args){
		int[][]board = {{1,2,3,4},
						{5,6,7,8},
						{9,10,12,15},
						{13,14,11,16}};
		Node root = new Node(board);
		SearchType use = new SearchType();
		use.BFS(root);
	}
}
