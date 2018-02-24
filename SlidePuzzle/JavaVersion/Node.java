import java.util.ArrayList;

public class Node {
	Node parent = null;
	ArrayList<Node> children = new ArrayList<Node>();
	int[][] puzzleBoard = new int[3][3];
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
}
