import java.util.ArrayList;

public class attribute {
	int numPossibleValue;
	String attributeName;
	ArrayList<String> possibleValue;
	int index;
	public attribute(int n, String name, ArrayList<String> b,int i){
		numPossibleValue = n;
		attributeName = name;
		possibleValue = b;
		index = i;
	}
	
	public int getNumberOfValue(){
		return numPossibleValue;
	}
	
	public int indexOfValue(String input){
		
		return possibleValue.indexOf(input);
		
	}
	
	public String getValueAt(int index){
		return possibleValue.get(index);
	}
}
