
public class tally {
	int result;
	int total = 0;
	
	public tally(int r){
		result = r;
		
	}
	
	public void addOne(){
		total++;
	}
	
	public int getTotal(){
		return total;
	}
}
