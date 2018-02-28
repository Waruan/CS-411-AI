
public class Tnode {
	
	int state;
	String action ;
	Double probability;
	int nextState ;
	public Tnode(int s,String a, Double p, int n)
	{
		state = s;
		action = a;
		probability = p;
		nextState = n;
	}	
	
	public boolean find(int s,String a){
		if(state == s && action.equals(a) ){
			return true;
		}
		
		return false;
	}
	
	public void print(){
		System.out.println(state);
		System.out.println(action);
		System.out.println(probability);
	}
	
}
