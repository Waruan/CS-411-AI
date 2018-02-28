

public class stateNode {
	
	int currentState;
	String action;
	int nextState;
	
	public stateNode(int c, String a, int n){
		currentState = c;
		action = a;
		nextState= n;
		
	}
	
	public boolean find(int c,String a){
		
		if(c == currentState && action.equals(a)){
			return true;
		}
		
		return false;
	}
	
}
