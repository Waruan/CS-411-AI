
import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class AStar extends JFrame {

  //JTree tree;

  DefaultTreeModel treeModel;
  // 0 for displacement 
  // 1 for MD
  int heuristics = 1;

  public AStar() {
    //super("Tree Test Example");
    setSize(400, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    

  }
  
  // Displacement
  public static int displacement(node n){
	  int numberOfDisplacement = 0;
	  int[][] currentPuzzle = n.puzzle;
	  int[][] goalState = new int[][]{
		  {1,2,3,4},
		  {5,6,7,8},
		  {9,10,11,12},
		  {13,14,15,0}
	  }; 
	  for(int i = 0;i<4; i++){
		  for(int j = 0; j<4;j++){
			  if(currentPuzzle[i][j] != goalState[i][j]){
				  numberOfDisplacement++;
			  }
		  }
	  }
	  
	  return numberOfDisplacement; 
  }
  
  //Manhattan Distance
  public static int manhattanDistance(node n){
	  int distance = 0;

	  int[][] goalState = new int[][]{
		  {1,2,3,4},
		  {5,6,7,8},
		  {9,10,11,12},
		  {13,14,15,0}
	  }; 
	  for(int i = 0;i<4; i++){
		  for(int j = 0; j<4;j++){
			  if(goalState[i][j] != 0){
				  int[] c = findCoordinate(n,goalState[i][j]);
				  if(c == null){
					  System.out.println("error in puzzle STATE");
					  return -1;
				  }
				  distance += Math.abs(i-c[0]) + Math.abs(j-c[1]);
			  }
		  }
	  }
	  return distance;
  }
  
  public static int[] findCoordinate(node n,int goal){
	  int[] coordinate  = new int[]{0,0};
	  int[][] currentPuzzle = n.puzzle;
	  for(int i = 0;i<4; i++){
		  for(int j = 0; j<4;j++){
			  if(currentPuzzle[i][j] == goal){
				 coordinate[0] = i;
				 coordinate[1] = j;
				 return coordinate;
			  }
		  }
	  }
	  
	  
	  return null;
  }
  
  // move the blank piece up
  public DefaultMutableTreeNode moveUp(node n){
	if(n.x == 0){
		//System.out.println("Debug moveUP");
		//System.out.println(n.x);
		return null;
	}
	else{
		int[][] nextStep = new int[4][4];
		for(int i = 0; i < (n.puzzle).length; i++)
			nextStep[i] = (n.puzzle)[i].clone();
		//safe the value of the tile above the empty tile
		int temp = nextStep[(n.x)-1][n.y];
		nextStep[(n.x)-1][n.y] = 0;
		nextStep[n.x][n.y] = temp;
		node t = new node(nextStep,(n.x)-1,n.y,"Up");
		if(heuristics == 0){
			t.setDistance(displacement(t));
		}
		else{
			t.setDistance(manhattanDistance(t));
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
		return newNode;
		
	}
  }
  //move the blank piece right
  public DefaultMutableTreeNode moveRight(node n){
	 if(n.y == 3){
		 
		 return null;
	 }
	 else{
		 int[][] nextStep = new int[4][4];
			for(int i = 0; i < (n.puzzle).length; i++)
				nextStep[i] = (n.puzzle)[i].clone();
		 
		 int temp = nextStep[n.x][(n.y)+1];
		 nextStep[n.x][(n.y)+1] = 0;
		 nextStep[n.x][n.y] = temp;
		 
		node t = new node(nextStep,n.x,(n.y)+1,"Right");
		if(heuristics == 0){
			t.setDistance(displacement(t));
		}
		else{
			t.setDistance(manhattanDistance(t));
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
		return newNode;
		 
		 
	 }
  }
  //move the blank piece down
  public DefaultMutableTreeNode moveDown(node n){
	if(n.x == 3){
			return null;
	}
	else{
		int[][] nextStep = new int[4][4];
		for(int i = 0; i < (n.puzzle).length; i++)
			nextStep[i] = (n.puzzle)[i].clone();
		
		//safe the value of the tile above the empty tile
		int temp = nextStep[(n.x)+1][n.y];
		nextStep[(n.x)+1][n.y] = 0;
		nextStep[n.x][n.y] = temp;
		
		node t = new node(nextStep,(n.x)+1,n.y,"Down");
		if(heuristics == 0){
			t.setDistance(displacement(t));
		}
		else{
			t.setDistance(manhattanDistance(t));
		}
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
		
		return newNode;	
	}
  }
  
  //move the blank piece left
  public DefaultMutableTreeNode moveLeft(node n){
	  if(n.y == 0){
		  return null;
	  }
	 else{
		 int[][] nextStep = new int[4][4];
			for(int i = 0; i < (n.puzzle).length; i++)
				nextStep[i] = (n.puzzle)[i].clone();
		 
		 int temp = nextStep[n.x][(n.y)-1];
		 nextStep[n.x][(n.y)-1] = 0;
		 nextStep[n.x][n.y] = temp;
		 node t = new node(nextStep,n.x,(n.y)-1,"Left");
		 if(heuristics == 0){
			t.setDistance(displacement(t));
		 }
		 else{
			t.setDistance(manhattanDistance(t));
		 }
		 DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(t);
	 
		 return newNode;
		 
	 }
  }
  // create tree and start search
  public void init(node r) {

	long startTime   = System.currentTimeMillis();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(r);
    
    treeModel = new DefaultTreeModel(root);
    DefaultMutableTreeNode goal = AStarSearch(root);
    
    long endTime   = System.currentTimeMillis();
    long totalTime = endTime - startTime;
    
    System.out.println("Time Used in millseconds");
	System.out.println(totalTime);
    
	System.out.println("Memory Used");
	long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	System.out.println(used);
	
    //If search return null there is no possible solution to the input puzzle
    if(goal == null){
    	System.out.println("No possible Solution");
    	return;
    }
    
    //Print out the solution to the puzzle
    System.out.println("Solution Found");
    Object[] a = goal.getUserObjectPath();

    for(int i = 0;i< a.length;i++){
    	node path = (node)a[i];
    	System.out.println(path.direction);
    }

  }
  
  // Check if the current of the queue is the goal state 
  public static boolean checkgoal(node qhead){
	  int[][] n = qhead.puzzle;
	  int[][] goal = new int[][]{
		  {1,2,3,4},
		  {5,6,7,8},
		  {9,10,11,12},
		  {13,14,15,0}
	  };
	  return Arrays.deepEquals(goal, n);
  }
  

  
  public DefaultMutableTreeNode AStarSearch(DefaultMutableTreeNode root){
	  LinkedList<DefaultMutableTreeNode> queue = new LinkedList<DefaultMutableTreeNode>();
	  DefaultMutableTreeNode head = root;
	  node currentNode;
	  queue.add(root);
	  while(queue.size() != 0){
		  head = queue.poll();
		  currentNode = (node) head.getUserObject();
		  if(checkgoal(currentNode)){
			  return head;
		  }
		  //add new node to the tree(moving the blank tile up)
		  DefaultMutableTreeNode upLeaf =  moveUp(currentNode);
		  if(upLeaf != null ){
			  head.add(upLeaf);
			  int index = findInsertIndex(queue,((node)(upLeaf).getUserObject()).distance);
			  queue.add(index,upLeaf);
		  }
		  
		  DefaultMutableTreeNode rightLeaf =  moveRight(currentNode);
		  if(rightLeaf != null ){
			  head.add(rightLeaf);
			  int index = findInsertIndex(queue,((node)(rightLeaf).getUserObject()).distance);
			  queue.add(index,rightLeaf);
		  }
		  
		  DefaultMutableTreeNode downLeaf =  moveDown(currentNode);
		  if(downLeaf != null ){
			  head.add(downLeaf);
			  int index = findInsertIndex(queue,((node)(downLeaf).getUserObject()).distance);
			  queue.add(index,downLeaf);
		  }
		  
		  DefaultMutableTreeNode leftLeaf =  moveLeft(currentNode);
		  if(leftLeaf != null ){
			  head.add(leftLeaf);
			  int index = findInsertIndex(queue,((node)(leftLeaf).getUserObject()).distance);
			  queue.add(index,leftLeaf);
		  }
		  
	  }
	  return null;
  } 
  // Find index to insert node into queue such that the node before it has a small or equal value in 
  // either displacement or Manhattan Distance and the node after it has a greater value
  public int findInsertIndex(LinkedList<DefaultMutableTreeNode> q, int n){
	  int index = -1;
	  if(q.size() == 0){
		  return 0;
	  }
	  int temp = ((node)((q.get(0)).getUserObject())).distance;
	  if(temp > n){
		  return 0;
	  }
	  
	  for(int i = 0;i<q.size();i++){
		if(i == q.size()-1){
			return i;
		}
		DefaultMutableTreeNode treeNode = q.get(i);
		node t = (node) treeNode.getUserObject();
		int distance = t.distance;
		
		DefaultMutableTreeNode nexttreeNode = q.get(i+1);
		node nextt = (node) nexttreeNode.getUserObject();
		int nextdistance = nextt.distance;
		
		if(n >= distance && n<nextdistance ){
			/*System.out.println("Value ");
			System.out.println(n);
			System.out.println("Current ");
			System.out.println(distance);
			System.out.println("Next ");
			System.out.println(nextdistance);
			System.out.println();
			System.out.println();*/
			return i+1;
		}
	  }
	  System.out.println("Error");
	  return index;
  }
  // compare the state of two node 
  public boolean compareNode(DefaultMutableTreeNode head,DefaultMutableTreeNode checkNode ){
	  node oldNode = (node)head.getUserObject();
	  node newNode = (node)checkNode.getUserObject();
	  
	 
	  return Arrays.deepEquals(oldNode.puzzle,newNode.puzzle);
	  
  }
  
  //create the root node of the tree with input from user
  public static node fill(){ 
	  int x = -1;
	  int y = -1;
      int[][] data = new int[4][4]; 
      Scanner in = new Scanner(System.in);
      System.out.println("Enter the elements(1-15 no repeated number and 0 for blank) for the Matrix"); 
      for(int row = 0; row< 4; row++){ 
    	  for(int col = 0 ;col< 4; col++){ 
              data[row][col] = in.nextInt(); 
              if(data[row][col] > 15){
            	  System.out.println("Input incorrect. Element greater than 15");
            	  in.close();
            	  return null;
              }
              if(data[row][col] < 0){
            	  System.out.println("Input incorrect. Element less than 0");
            	  in.close();
            	  return null;
              }
              if(data[row][col] == 0){
    			  x = row;
    			  y = col;
    		  }
    	  } 
      } 
     in.close();
      
      if(x == -1 || y == -1){
    	  System.out.println("No blank tile in puzzle");
    	  return null;
      }
      node root = new node(data,x,y);
      return root;
  }
  
  //print out puzzle state
  public static void puzzlePrint(DefaultMutableTreeNode n){
	  node printNode = (node) n.getUserObject();
      for(int row = 0; row< 4; row++){
    	  for(int col = 0 ;col< 4; col++){ 
           System.out.print(printNode.puzzle[row][col]);
           System.out.print(" ");
     	} 
    	System.out.println(); 
	 }
  }
  
  public static void main(String args[]) {
    
	  
	AStar tt = new AStar();
    node root = fill();
    if(root == null){
    	System.out.println("Error in Input");
    	return;
    }
    
    System.out.println("A* Search");
    root.setDistance(displacement(root));
    tt.init(root);
    
    System.out.println();
    
    System.out.println("Iterative Deepening Search");
    IDDFS tt2 = new IDDFS();
    tt2.init(root);
    
    System.out.println();
    
    System.out.println("Breadth-first Search ");
    BFS tt3 = new BFS();
    tt3.init(root);
    
    
    
  }
}

