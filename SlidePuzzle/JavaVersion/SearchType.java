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
	
//---------- Constructor -------------------------------------------------------------------------	
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
		LinkedList<Node> pathToSolution = new LinkedList<Node>();//list to store the ancestor nodes
		LinkedList<Node> nodesNotVisited = new LinkedList<Node>();//nodes to visit
		LinkedList<Node> nodesVisited = new LinkedList<Node>();//nodes visited
		
		nodesNotVisited.push(rootDFS);//insert the root to the nodes to visit list
		boolean goalFound = false;//variable to break the while loop
		int steps = 0;//limit for the levels to search through
		
		start:{
			while(nodesNotVisited.size() > 0 && !goalFound && steps < 500){
				Node currentNode = nodesNotVisited.pop();//get the current element at the front of the list
				if(currentNode.isGoalReached()){//if the current node is the goal
					System.out.println("Goal reached! DFS");
					goalFound = true;//update the state
					tracePath(pathToSolution, currentNode);//give the list so we can trace back
					break start;//break the whole while loop
				}
				currentNode.expandNode();//if the current node isnt the goal, create its children
				for(int i = 0; i < currentNode.children.size(); i ++){//go through all its children
					Node currentChild = currentNode.children.get(i);//get each one at a time
					if(currentChild.isGoalReached()){//if the current child is the goal
						System.out.println("Goal reached! DFS");
						goalFound = true;//update the condition
						tracePath(pathToSolution, currentChild);//give the list so we can backtrack
						break start;//break the whole while loop
					}
					//if the current child isn't the goal, check we haven't visited or its not waiting
					if(!contains(nodesNotVisited, currentChild) && !contains(nodesVisited, currentChild)){
						nodesNotVisited.push(currentChild);//if it hasn't been visited or isn't waiting
						//we added to the list of nodes to visit
					}
				}
				nodesVisited.add(currentNode);//since the current node is not the goal, we marked it as visited
				steps++;//increment the steps taken so far
			}
		}
		
		if(steps >= 500 && !goalFound){//if we didn't find the goal within the steps allowed
			writerForFile.println();//we write to the file that we needed more steps
			writerForFile.println("Depth First Search");
			writerForFile.println(steps + " steps were not enough to find a solution");
		}
		
		else if(pathToSolution.size() > 0 && goalFound){//if the goal was reached
			writerForFile.println();//we write to the file the instructions to solve the puzzle
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
		LinkedList<Node> pathToSolution = new LinkedList<Node>();//list to store the ancestor nodes
		Queue<Node> nodesNotVisited = new LinkedList<Node>();//nodes to visit
		LinkedList<Node> nodesVisited = new LinkedList<Node>();//nodes visited
		
		nodesNotVisited.add(rootBFS);//insert the root to the nodes to visit list
		boolean goalFound = false;//variable to break the while loop
		int steps = 0;//limit for the levels to search through
		
		while(nodesNotVisited.size() > 0 && !goalFound && steps < 1500){
			Node currentNode = nodesNotVisited.remove();//remove the first element in the queue
			if(currentNode.isGoalReached()){//if the current node is the goal
				System.out.println("Goal reached! BFS");
				goalFound = true;//update the state
				tracePath(pathToSolution, currentNode);//give the list so we can trace back
				break;//break the whole while loop
			}
			nodesVisited.add(currentNode);//since the current node is not the goal, we marked it as visited
			currentNode.expandNode();//if the current node isnt the goal, create its children
			for(int i = 0; i < currentNode.children.size(); i++){//go through all its children
				Node currentChild = currentNode.children.get(i);//get each one at a time
				if(currentChild.isGoalReached()){//if the current child is the goal
					System.out.println("Goal reached!BFS");
					goalFound = true;//update the condition
					tracePath(pathToSolution, currentChild);//give the list so we can backtrack
				}
				//if the current child isn't the goal, check we haven't visited or it's not waiting
				if(!contains(nodesVisited, currentChild)&& !contains((LinkedList<Node>) nodesNotVisited, currentChild)){
					nodesNotVisited.add(currentChild);//if it hasn't been visited or isn't waiting
					//we added to the list of nodes to visit
				}
			}
			steps++;//increment the steps taken so far
		}
		System.out.println(steps);
		
		if(steps >= 1500 && !goalFound){//if we didn't find the goal within the steps allowed
			writerForFile.println();//we write to the file that we needed more steps
			writerForFile.println("Breadth First Search");
			writerForFile.println(steps + " steps were not enough to find a solution");
		}
		
		else if(pathToSolution.size() > 0 && goalFound){//if the goal was reached
			writerForFile.println();//we write to the file the instructions to solve the puzzle
			writerForFile.println("Breadth First Search");
			writerForFile.println("It took: " + steps + " steps to find a solution");
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
	}
}
