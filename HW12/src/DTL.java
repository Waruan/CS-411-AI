import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.swing.tree.DefaultMutableTreeNode;

public class DTL {
	ArrayList<attribute> attributeList;
	int numAttributes;
	int numExample;
	ArrayList<example> ex;
	//DefaultMutableTreeNode tree;
	
	public DTL(){
		ex =  new ArrayList<example>();
		attributeList = new ArrayList<attribute>();
	}
	
	public void getInput(){
		
		// get number of Attributes
		System.out.println("Enter Number of Attributes including Target/Goal" );
		System.out.println("Target/Goal must be inputed last" );
		Scanner in = new Scanner(System.in);
		numAttributes = in.nextInt();
		
		// get Attributes list 
		for(int i=0;i< numAttributes;i++){
			System.out.println("Enter Attributes name of Attributes number " + (i+1));
			String name = in.next();
			System.out.println("Number of possible Value for Attributes " + name );
			int n = in.nextInt();
			ArrayList<String> branch = new ArrayList<String>();
			for(int j=0;j<n;j++){
				System.out.println("Enter possible Value");
				String v = in.next();
				branch.add(v);
			}
			attribute a = new attribute(n,name,branch,i);
			attributeList.add(a);
		}
		//get number of examples
		System.out.println("Enter the number of Examples");
		numExample = in.nextInt();
		
		//get examples
		
		for(int i=0;i<numExample;i++){
			ArrayList<String> attributeVal = new ArrayList<String>() ;
			System.out.println("Enter example number " + (i+1));
			for(int j=0; j< numAttributes; j++){
				String input = in.next();
				attributeVal.add(input);
			}
			example x =  new example(numAttributes,attributeVal);
			ex.add(x);
		}
		
		in.close();
		
	}
	
	// get the classification
	public String compare(ArrayList<example> exList){
		
		String firstExampleGoal = exList.get(0).getGoal();
		for(int i=1;i<exList.size();i++){
			
			//not all the classification are the same
			if(firstExampleGoal.equals(exList.get(i).getGoal())){
				return null;
			}
		}
		// all classification are the same
		return firstExampleGoal;
	}
	
	
	public String plurality_value(ArrayList<example> e){
		
		
		int size = attributeList.get(attributeList.size()-1).getNumberOfValue();
		
		int[] tally = new int[size];
		
		for(int i=0; i< e.size();i++){
			String v = e.get(i).getGoal();
			int index = attributeList.get(attributeList.size()-1).indexOfValue(v);
			
			tally[index]++;
		}
		
		int plurality = tally[0];
		int index2 = 0;
		
		for(int i=0;i<size;i++){
			if(plurality< tally[i]){
				plurality = tally[i];
				index2 = i;
			}
		}
		
		return attributeList.get(attributeList.size()-1).getValueAt(index2);
		
		
	}
	
	public int chooseAttribute(ArrayList<attribute> a, ArrayList<example> e){
		
		int best = 0;
		// index of the best attribute
		int bestIndex = 0;
		
		for(int i=0;i<a.size();i++){
			
			int tally[] = new int[a.get(i).getNumberOfValue()];
			
			for(int j=0 ;j<e.size();j++){
				
				String v = e.get(j).getValueAt(a.get(i).index);
				int index = a.get(i).indexOfValue(v);
				if(index == -1 ){
					System.out.println("attribute");
					System.out.println(a.get(i).attributeName);
					System.out.println("Example");
					System.out.println(j);
					System.out.println(v);
				}
				tally[index]++;
				
			}
			int localbest = tally[0];

			for(int j = 0;j<a.get(i).getNumberOfValue();j++){
				if(localbest < tally[j]){
					localbest  = tally[j];
				}
			}
			
			if(localbest > best){
				best = localbest;
				bestIndex = i;
			}
			
		}
		
		return bestIndex;
	}
	// get the example where the value of e.A = vk
	public ArrayList<example>  getSubExampleList(int attributeIndex,String attributeValue , ArrayList<example> exampleList){
		ArrayList<example> exs = new ArrayList<example>();
		
		for(int i = 0;i<exampleList.size();i++){
			if(exampleList.get(i).getValueAt(attributeIndex).equals(attributeValue)){
				exs.add(exampleList.get(i));
			}
		}
		
		
		return exs;
		
	}
	
	public DefaultMutableTreeNode dtlAlgorithm (ArrayList<example> examples, ArrayList<attribute> attributes ,  ArrayList<example> parent_example){
		if(examples.isEmpty()){
			node r = new node(plurality_value(parent_example));
			DefaultMutableTreeNode root =  new DefaultMutableTreeNode(r);
			return root;
		}
		String classification = compare(examples);
		if(!(classification == null) ){
			node r = new node(classification);
			DefaultMutableTreeNode root =  new DefaultMutableTreeNode(r);
			return root;
		}
		if(attributes.isEmpty()){
			node r = new node(plurality_value(examples));
			DefaultMutableTreeNode root =  new DefaultMutableTreeNode(r);
			return root;
		}
		else{
			int bestIndex  = chooseAttribute(attributes,examples);
			
			node root  = new node(attributes.get(bestIndex).attributeName);
			DefaultMutableTreeNode tree = new DefaultMutableTreeNode(root);
			for(int i=0;i<attributes.get(bestIndex).numPossibleValue;i++){
				ArrayList<example> exs = getSubExampleList(bestIndex,attributes.get(bestIndex).getValueAt(i),examples);
				ArrayList<attribute> attributesMinusA = (ArrayList<attribute>) attributes.clone();
				attributesMinusA.remove(bestIndex);
				
				DefaultMutableTreeNode subtree = dtlAlgorithm(exs,attributesMinusA,examples);
				node r = (node) subtree.getUserObject();
				r.setBranch(attributes.get(bestIndex).attributeName);
				tree.add(subtree);
			}
			
			return tree;
		}
	
	}

	 public static void main(String args[]) {
		 DTL test =  new DTL();
		 test.getInput();
		 DefaultMutableTreeNode rTree =  test.dtlAlgorithm(test.ex, test.attributeList, test.ex);
		
	 }
	
	
}
