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
			/*for(int i = 0; i < 4; i++){
				for(int j = 0; j <4; j++){
				System.out.print(childBoard[i][j] + " ");
				}
			System.out.println();
			}*/
		}
	}
	
	public static void main(String[] args){
		int[][]board = {{1,2,3,4},
						{5,6,7,8},
						{9,10,11,12},
						{13,14,15,16}};
		Node root = new Node(board);
		root.moveRight(board, 1, 2);
		System.out.println("\n"+root.children.get(0).direction);
	}
}
