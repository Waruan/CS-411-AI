import java.util.ArrayList;

public class node {
	String branch;
	String result;
	
	public node(String r){
		result = r;
	}
	
	public String getResult(){
		return result;
	}
	
	public String getBranch(){
		return branch;
	}
	
	public void setBranch(String b){
		branch = b;
	}

}
