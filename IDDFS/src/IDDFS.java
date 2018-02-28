
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

public class IDDFS extends JFrame {

  //JTree tree;

  DefaultTreeModel treeModel;


  public IDDFS() {
    //super("Tree Test Example");
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
    
    treeModel = new DefaultTreeModel(root);
    DefaultMutableTreeNode goal = IDS(root);
    
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
  
  
  // Depth Limited Search
  // Recursive call
  // Remove node from tree when the goal does not exist on that path
  public DefaultMutableTreeNode DLS(DefaultMutableTreeNode head,int maxDepth){
	  if(checkgoal((node) head.getUserObject()) == true){
		  return head;
	  }
	  else if (maxDepth == 0){
		  
		  return null;

	  }
	  
	  node currentNode =  (node)head.getUserObject();
	  
	  DefaultMutableTreeNode upLeaf =  moveUp(currentNode);
	  if(upLeaf != null ){
		  head.add(upLeaf);
		  DefaultMutableTreeNode result = DLS(upLeaf,maxDepth-1);
		  if(result == null){
			  upLeaf.removeFromParent();
			  upLeaf = null;
		  }
		  else{
			  return result;
		  }
	  }
	  
	  
	  //add new node to the tree(moving the blank tile Right)
	  DefaultMutableTreeNode rightLeaf =  moveRight(currentNode);
	  if(rightLeaf != null ){
		  head.add(rightLeaf);
		  DefaultMutableTreeNode result = DLS(rightLeaf,maxDepth-1);
		  if(result == null){
			  rightLeaf.removeFromParent();
			  rightLeaf = null;
		  }
		  else{
			  return result;
		  }
	  }
	  
	  
	  //add new node to the tree(moving the blank tile Down)
	  DefaultMutableTreeNode downLeaf =  moveDown(currentNode);
	  if(downLeaf != null){
		  head.add(downLeaf);
		  DefaultMutableTreeNode result = DLS(downLeaf,maxDepth-1);
		  if(result == null){
			  downLeaf.removeFromParent();
			  downLeaf = null;
		  }
		  else{
			  return result;
		  }
		  
	  }
	  
	  
	//add new node to the tree(moving the blank tile Left)
	  DefaultMutableTreeNode leftLeaf =  moveLeft(currentNode);
	  if(leftLeaf != null){
		  head.add(leftLeaf);
		  DefaultMutableTreeNode result = DLS(leftLeaf,maxDepth-1);
		  if(result == null){
			  leftLeaf.removeFromParent();
			  leftLeaf = null;
		  }
		  else{
			  return result;
		  }
	  }
	  return null;
	
	  
  }
  
  // Iterative Deepening search
  public DefaultMutableTreeNode IDS(DefaultMutableTreeNode root){
	   int depth = 0;
	   while(true){
		   //long startTime   = System.currentTimeMillis();
		   
		   root.removeAllChildren();
		   DefaultMutableTreeNode solution = DLS(root , depth);
		
		   if(solution != null){
			   return solution;
		   }
		   
		   /*//log time
		   long endTime   = System.currentTimeMillis();
		   long totalTime = endTime - startTime;
		   System.out.print("Depth of ");
		   System.out.println(depth);
		   System.out.println("Time Used in millseconds");
		   System.out.println(totalTime);
		   
		   //log memory usage
		   System.out.println("Memory Used");
		   long used  = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		   System.out.println(used);
		   
		   System.out.println();*/
		   
		   depth++;
	   }
	  
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
    IDDFS tt = new IDDFS();
    node root = fill();
    if(root == null){
    	System.out.println("Error in Input");
    	return;
    }
    tt.init(root);
    
  }
}
