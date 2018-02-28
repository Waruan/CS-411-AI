
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

public class BFS extends JFrame {

  //JTree tree;

  DefaultTreeModel treeModel;
  private long startTime;

  public BFS() {
    //super("Tree Test Example");
	//startTime = System.currentTimeMillis();
    setSize(400, 300);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
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
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new node(nextStep,(n.x)-1,n.y,"Up"));
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
		 DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new node(nextStep,n.x,(n.y)+1,"Right"));
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
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new node(nextStep,(n.x)+1,n.y,"Down"));
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
			 DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new node(nextStep,n.x,(n.y)-1,"Left"));
			return newNode;
			 
		 }
  }
  // create tree and start search
  public void init(node r) {
	long startTime   = System.currentTimeMillis();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(r);
    //puzzlePrint(root);
    
    treeModel = new DefaultTreeModel(root);
    DefaultMutableTreeNode goal = search(root);
    
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
  
  //BFS 
  // Check each head node if it the goal state. If not add it child to the queue and loop until the queue is empty 
  public DefaultMutableTreeNode search(DefaultMutableTreeNode root){
	  
	  LinkedList<DefaultMutableTreeNode> queue = new LinkedList<DefaultMutableTreeNode>();
	  queue.add(root);
	  DefaultMutableTreeNode head = root;
	  node currentNode;
	  while(queue.size() != 0){
		  
		  head = queue.poll();
		  currentNode = (node) head.getUserObject();
		  if(checkgoal(currentNode)){
			  return head;
		  }

		  //currentNode.puzzlePrint();
		  //System.out.println("DEBUG 0");
		  
		  //add new node to the tree(moving the blank tile up)
		  DefaultMutableTreeNode upLeaf =  moveUp(currentNode);
		  if(upLeaf != null && findLoop(root,upLeaf) == false ){
			  head.add(upLeaf);
			  queue.add(upLeaf);
			  
			  
			  //System.out.println("DEBUG 1");
			  //puzzlePrint(upLeaf);
		  }
		  
		  //System.out.println("DEBUG 2");
		  
		  //add new node to the tree(moving the blank tile Right)
		  DefaultMutableTreeNode rightLeaf =  moveRight(currentNode);
		  if(rightLeaf != null && findLoop(root,rightLeaf) == false){
			  head.add(rightLeaf);
			  queue.add(rightLeaf);
			  
			  
			  //System.out.println("DEBUG 3");
			  //puzzlePrint(rightLeaf);
		  }
		  
		  //System.out.println("DEBUG 4");
		  
		  //add new node to the tree(moving the blank tile Left)
		  DefaultMutableTreeNode downLeaf =  moveDown(currentNode);
		  if(downLeaf != null && findLoop(root,downLeaf) == false){
			  head.add(downLeaf);
			  queue.add(downLeaf);
			  
			  //System.out.println("DEBUG 5");
			  //puzzlePrint(downLeaf);
			  
		  }
		  
		  //System.out.println("DEBUG 6");
		  
		  DefaultMutableTreeNode leftLeaf =  moveLeft(currentNode);
		  if(leftLeaf != null && findLoop(root,leftLeaf) == false){
			  head.add(leftLeaf);
			  queue.add(leftLeaf);
			  
			  //System.out.println("DEBUG 7");
			  //puzzlePrint(leftLeaf);
			  
		  }
		  
		  //print out the item left in queue and the memory used
/*		  System.out.println("Memory Used");
		  System.out.println(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		  System.out.println("Items left in queue");
		  System.out.println(queue.size());*/
		  
		  
		  //System.out.println("DEBUG 8");
		  
		
	  }
	return null;
  }
  
  // Using the DefaultMutableTreeNode library BFS to find if there is repeated states
  public boolean findLoop(DefaultMutableTreeNode root ,DefaultMutableTreeNode checkNode ){
	  
	 
	  Enumeration en = root.breadthFirstEnumeration();
	  while (en.hasMoreElements()) {
		  DefaultMutableTreeNode node = (DefaultMutableTreeNode) en.nextElement();
		  if(compareNode(node,checkNode)){
			  
			  /*System.out.println("Repeated Loop");
			  puzzlePrint(node);
			  puzzlePrint(checkNode);
			  System.out.println("\n\n");
			  */
			  
			  return true;
			  
		  }
	  }
	  return false;
	 
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
            	  return null;
              }
              if(data[row][col] < 0){
            	  System.out.println("Input incorrect. Element less than 0");
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
      //System.out.println("debug blank location");
      //System.out.println(x);
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
    BFS tt = new BFS();
    node root = fill();
    if(root == null){
    	System.out.println("Error in Input");
    	return;
    }
    tt.init(root);
    
    /*log memory usage
    long total = Runtime.getRuntime().totalMemory();
    long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    System.out.println(total);
    System.out.println(used);*/
    
    /*long endTime   = System.currentTimeMillis();
    System.out.println("Time Used in millseconds");
    System.out.println(totalTime);*/
  }
  
  
}
