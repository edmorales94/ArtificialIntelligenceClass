package SlidePuzzleClasses;

import java.util.LinkedList;
import java.util.Queue;

public class SearchType {
	
/**************************************************************************************************
 * This method will help us see if a node is in the visited list or not visisited list. 
 * If the node is in neither, then we can say this is a new node
 * @param list
 * @param currentNode
 * @return True if the node exists in the list. False if it doesn't
 */
	public static boolean contains(LinkedList<Node> list, Node currentNode){
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).isSamePuzzle(currentNode.puzzleBoard)){
				return true;
			}
		}
		return false;
	}
	
/**************************************************************************************************
 * This method will receive the last child in the "graph" and will get its parent, and 
 * all of its ancestors all the way to the root node
 * @param path
 * @param goalNode
 */
	public void tracePath(LinkedList<Node> path, Node goalNode){
		Node current = goalNode;
		path.add(current);
		while(current.parent != null){
			current = current.parent;
			path.add(current);
		}
	}
		
/**************************************************************************************************
 * Apply the Breadth First Search method to find the path to the solution
 * @param root
 */
	public void BFS(Node root){
		LinkedList<Node> pathToSolution = new LinkedList<Node>();
		Queue<Node> nodesNotVisited = new LinkedList<Node>();
		LinkedList<Node> nodesVisited = new LinkedList<Node>();
		
		nodesNotVisited.add(root);
		boolean goalFound = false;
		
		while(nodesNotVisited.size() > 0 && !goalFound){
			Node currentNode = nodesNotVisited.remove();
			nodesVisited.add(currentNode);
			
			currentNode.expandNode();
			for(int i = 0; i < currentNode.children.size(); i++){
				Node currentChild = currentNode.children.get(i);
				if(currentChild.isGoalReached()){
					System.out.println("Goal reached!");
					goalFound = true;
					tracePath(pathToSolution, currentChild);
				}
				if(!contains(nodesVisited, currentChild)&& !contains((LinkedList<Node>) nodesNotVisited, currentChild)){
					nodesNotVisited.add(currentChild);
				}
			}
		}
		if(pathToSolution.size() > 0){
			for(int i = pathToSolution.size()-1; i >=0; i--){
				if(!pathToSolution.get(i).direction.equalsIgnoreCase("")){
					System.out.print(pathToSolution.get(i).direction+" ");
					//pathToSolution.get(i).printPuzzle();
				}
			}
		}
	}
}
