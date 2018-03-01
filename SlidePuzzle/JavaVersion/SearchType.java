package SlidePuzzleClasses;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Queue;

public class SearchType {
	Node rootBFS = null;
	Node rootDFS = null;
	PrintWriter writerForFile = null;
	
	public SearchType(Node root){
		this.rootBFS = root;
		this.rootDFS = root;
		try {
			writerForFile = new PrintWriter("result.txt", "UTF-8");
			writerForFile.println("Puzzle blocks:\n");
			for(int i = 0; i < 16; i++){
				writerForFile.print(Integer.toString(root.puzzleBoard[i]) + " ");
			}
			writerForFile.println();
		} 
		catch (FileNotFoundException e) {
			System.out.println("No such file exists.");
		} 
		catch (UnsupportedEncodingException e) {
			System.out.println("Couldn't create file");
		}
	}
	
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
 *Apply the Depth First Search method to find the path to the solution 	
 */
	public void DFS(){
		LinkedList<Node> pathToSolution = new LinkedList<Node>();
		LinkedList<Node> nodesNotVisited = new LinkedList<Node>();
		LinkedList<Node> nodesVisited = new LinkedList<Node>();
		
		nodesNotVisited.push(rootDFS);
		boolean goalFound = false;
		int steps = 0;
		start:{
			while(nodesNotVisited.size() > 0 && !goalFound && steps < 12){
				Node currentNode = nodesNotVisited.pop();
				currentNode.expandNode();
				for(int i = 0; i < currentNode.children.size(); i ++){
					Node currentChild = currentNode.children.get(i);
					if(currentChild.isGoalReached()){
						System.out.println("Goal reached! DFS");
						goalFound = true;
						tracePath(pathToSolution, currentChild);
						break start;
					}
					if(!contains(nodesNotVisited, currentChild) && !contains(nodesVisited, currentChild)){
						nodesNotVisited.push(currentChild);
					}
				}
				nodesVisited.add(currentNode);
				steps++;
			}
		}
		
		System.out.println("\nSteps: " + steps);
		if(pathToSolution.size() > 0){
			writerForFile.println();
			writerForFile.println("Depth First Search");
			writerForFile.print("Directions to move the empty space:");
			for(int i = pathToSolution.size()-1; i >=0; i--){
				if(!pathToSolution.get(i).direction.equalsIgnoreCase("")){
					//System.out.print(pathToSolution.get(i).direction+" ");
					//pathToSolution.get(i).printPuzzle();
					writerForFile.write(pathToSolution.get(i).direction+ " ");
				}
			}
		}
		writerForFile.close();
	}
/**************************************************************************************************
 * Apply the Breadth First Search method to find the path to the solution
 * @param rootBFS
 */
	public void BFS(){
		LinkedList<Node> pathToSolution = new LinkedList<Node>();
		Queue<Node> nodesNotVisited = new LinkedList<Node>();
		LinkedList<Node> nodesVisited = new LinkedList<Node>();
		
		nodesNotVisited.add(rootBFS);
		boolean goalFound = false;
		int steps = 0;
		while(nodesNotVisited.size() > 0 && !goalFound && steps < 18){
			Node currentNode = nodesNotVisited.remove();
			nodesVisited.add(currentNode);
			
			currentNode.expandNode();
			for(int i = 0; i < currentNode.children.size(); i++){
				Node currentChild = currentNode.children.get(i);
				if(currentChild.isGoalReached()){
					System.out.println("Goal reached!BFS");
					goalFound = true;
					tracePath(pathToSolution, currentChild);
				}
				if(!contains(nodesVisited, currentChild)&& !contains((LinkedList<Node>) nodesNotVisited, currentChild)){
					nodesNotVisited.add(currentChild);
				}
			}
			steps++;
		}
		System.out.println("steps: " + steps);
		if(pathToSolution.size() > 0 && goalFound){
			writerForFile.println();
			writerForFile.println("Breadth First Search");
			writerForFile.print("Directions to move the empty space:");
			for(int i = pathToSolution.size()-1; i >=0; i--){
				if(!pathToSolution.get(i).direction.equalsIgnoreCase("")){
					//System.out.print(pathToSolution.get(i).direction+" ");
					//pathToSolution.get(i).printPuzzle();
					writerForFile.write(pathToSolution.get(i).direction+ " ");
				}
			}
		}
		
		writerForFile.println();
		writerForFile.println();
	}
}
