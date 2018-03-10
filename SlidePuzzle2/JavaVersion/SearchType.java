package SlidePuzzle2;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SearchType {
	Node rootBFS = null;
	Node starNode = null;
	PrintWriter writerForFile = null;
	
//---------- Constructor -------------------------------------------------------------------------	
	public SearchType(Node root){
		this.rootBFS = root;
		this.starNode = root;
		try {
			writerForFile = new PrintWriter("result2.txt", "UTF-8");
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
 * Apply the Breadth First Search method to find the path to the solution
 * @param rootBFS
 */
	public void BFS(){
		LinkedList<Node> pathToSolution = new LinkedList<Node>();//list to store the ancestor nodes
		Queue<Node> nodesNotVisited = new LinkedList<Node>();//nodes to visit
		LinkedList<Node> nodesVisited = new LinkedList<Node>();//nodes visited
		
		nodesNotVisited.add(rootBFS);//insert the root to the nodes to visit list
		boolean goalFound = false;//variable to break the while loop
		int maxGraphDepth = 15;
		int currentDepth = 0;
		int nodesGenerated = 1;//the root is the default node
		start:{
			while(nodesNotVisited.size() > 0 && !goalFound && currentDepth < maxGraphDepth){
				Node currentNode = nodesNotVisited.remove();//remove the first element in the queue
				currentDepth = currentNode.depthLevel;
				if(currentNode.isGoalReached()){//if the current node is the goal
					System.out.println("Goal reached! BFS");
					goalFound = true;//update the state
					tracePath(pathToSolution, currentNode);//give the list so we can trace back
					break start;//break the whole while loop
				}
				nodesVisited.add(currentNode);//since the current node is not the goal, we marked it as visited
				currentNode.expandNode();//and create its children
				for(int i = 0; i < currentNode.children.size(); i++){//go through all its children
					Node currentChild = currentNode.children.get(i);//get each one at a time
					//if the currentChild hasn't visited or it's not waiting
					if(!contains(nodesVisited, currentChild)&& !contains((LinkedList<Node>) nodesNotVisited, currentChild)){
						currentChild.depthLevel = currentNode.depthLevel + 1;
						nodesGenerated++;
						nodesNotVisited.add(currentChild);//if it hasn't been visited or isn't waiting
						//we added to the list of nodes to visit
					}
				}
			}
		}
		
		if(currentDepth >= maxGraphDepth && !goalFound){//if we didn't find the goal within the steps allowed
			System.out.println("BFS couldn't find a solution");
			writerForFile.println();//we write to the file that we needed more steps
			writerForFile.println("Breadth First Search");
			writerForFile.println(currentDepth + " levels depth was not enough to find a solution");
			writerForFile.println("Try running the program again to get a different puzzle board");
		}
		
		else if(pathToSolution.size() > 0 && goalFound){//if the goal was reached
			writerForFile.println();//we write to the file the instructions to solve the puzzle
			writerForFile.println("Breadth First Search");
			writerForFile.println("It took: " + currentDepth + " levels depth to find a solution");
			writerForFile.println("Nodes generated: " + nodesGenerated);
			writerForFile.print("Directions to move the empty space:");
			writerForFile.println();
			for(int i = pathToSolution.size()-1; i >=0; i--){
				if(!pathToSolution.get(i).direction.equalsIgnoreCase("")){
					//System.out.print(pathToSolution.get(i).direction+" ");
					//pathToSolution.get(i).printPuzzle();
					writerForFile.write(pathToSolution.get(i).direction+ " ");
				}
			}
			writerForFile.println();
		}
	}
	
	
	/**
	 * 
	 */
	public void AStarSearch(){
		LinkedList<Node> pathToSolution = new LinkedList<Node>();//list to store the ancestor nodes
		LinkedList<Node> nodesVisited = new LinkedList<Node>();//nodes visited
		Comparator<Node> comparator = new Comparator<Node>(){
			@Override
			public int compare(Node node1, Node node2) {
				return (int)(node1.nodeScore - node2.nodeScore);
			}
		};
		PriorityQueue<Node> nodesNotVisited = new PriorityQueue<Node>(100, comparator);
		
		nodesNotVisited.add(starNode);//insert the root to the nodes to visit list
		boolean goalFound = false;//variable to break the while loop
		int maxGraphDepth = 15;
		int currentDepth = 0;
		int nodesGenerated = 1;//the root is the default node
		start:{
			while(nodesNotVisited.size() > 0 && !goalFound && currentDepth < maxGraphDepth){
				Node currentNode = nodesNotVisited.remove();//remove the first element in the queue
				currentDepth = currentNode.depthLevel;
				if(currentNode.isGoalReached()){//if the current node is the goal
					System.out.println("Goal reached! A* Search");
					goalFound = true;//update the state
					tracePath(pathToSolution, currentNode);//give the list so we can trace back
					break start;//break the whole while loop
				}
				nodesVisited.add(currentNode);//since the current node is not the goal, we marked it as visited
				currentNode.expandNode();//and create its children
				for(int i = 0; i < currentNode.children.size(); i++){//go through all its children
					Node currentChild = currentNode.children.get(i);//get each one at a time
					//if the currentChild hasn't visited or it's not waiting
					if(contains(nodesVisited, currentChild) && currentNode.nodeScore >= currentChild.nodeScore){
						continue;
					}
					
					else if(!contains(nodesVisited, currentChild)&& !contains(nodesNotVisited, currentChild)){
						currentChild.depthLevel = currentNode.depthLevel + 1;
						nodesGenerated++;
						nodesNotVisited.add(currentChild);//if it hasn't been visited or isn't waiting
						//we added to the list of nodes to visit
					}
				}
			}
		}
		
		if(currentDepth >= maxGraphDepth && !goalFound){//if we didn't find the goal within the steps allowed
			System.out.println("A* Search couldn't find a solution");
			writerForFile.println();//we write to the file that we needed more steps
			writerForFile.println("A* Search");
			writerForFile.println(currentDepth + " levels depth was not enough to find a solution");
			writerForFile.println("Try running the program again to get a different puzzle board");
		}
		
		else if(pathToSolution.size() > 0 && goalFound){//if the goal was reached
			writerForFile.println();//we write to the file the instructions to solve the puzzle
			writerForFile.println("A* Search");
			writerForFile.println("It took: " + currentDepth + " levels depth to find a solution");
			writerForFile.println("Nodes generated: " + nodesGenerated);
			writerForFile.print("Directions to move the empty space:");
			writerForFile.println();
			for(int i = pathToSolution.size()-1; i >=0; i--){
				if(!pathToSolution.get(i).direction.equalsIgnoreCase("")){
					//System.out.print(pathToSolution.get(i).direction+" ");
					//pathToSolution.get(i).printPuzzle();
					writerForFile.write(pathToSolution.get(i).direction+ " ");
				}
			}
			writerForFile.println();
		}
		writerForFile.close();
	}
	

	private boolean contains(PriorityQueue<Node> notExplored, Node currentChild) {
		PriorityQueue<Node> temp = notExplored;
		Node tempNode = null;
		for(int i = 0; i < notExplored.size(); i++){
			tempNode = temp.poll();
			if(tempNode.isSamePuzzle(currentChild.puzzleBoard)){
				return true;
			}
		}
		return false;
	}
}
