import java.util.ArrayList;

public class example {
	
	// number of Attributes
	int numAttributes;
	
	/*array of the Attributes 
	 * including goal
	 */
	
	ArrayList<String> attributeVal;
	
	
	// take input number of Attributes, Attributes array, and the result of example
	public example(int n, ArrayList<String> atVal){
		
		numAttributes = n;
		attributeVal = atVal;
	}
	
	public String getValueAt(int n){
		if(n> numAttributes){
			return null;
		}
		if(n<0){
			return null;
		}
		
		return attributeVal.get(n);
		
	}
	
	public int getIndexOf(String input){
		return attributeVal.indexOf(input);
	}
	
	public void printExample(){
		System.out.println("");
		
		for(int i=0;i< numAttributes;i++){
			System.out.print(attributeVal.get(i)+ " ");
		}
		System.out.println("");
	}
	
	public String getGoal(){
		
		return attributeVal.get(attributeVal.size()-1);
	}
	
}
