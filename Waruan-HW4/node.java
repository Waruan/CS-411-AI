
public class node {
	
	
	int[][] puzzle = new int[4][4];
	int x;
	int y;
	String direction = "";
	public node(int[][] input,int i,int j ){
		puzzle = input;
		x = i;
		y = j;
	}
	
	public node(int[][] input,int i,int j,String d){
		puzzle = input;
		x = i;
		y = j;
		direction = d;
	}
	
	public void puzzlePrint(){
		//Print out the matrix
		  
	      for(int row = 0; row< 4; row++){
	    	  for(int col = 0 ;col< 4; col++){ 
	           System.out.print(puzzle[row][col]);
	           System.out.print(" ");
	     	} 
	    	System.out.println(); 
		 }
	}
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	public void setDirection(String d){
		direction = d;
		
	}
}
