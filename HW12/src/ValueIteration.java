import java.util.ArrayList;

import java.util.Scanner;

public class ValueIteration {
	
	static ArrayList<Tnode> transition;
	static int state = 0;
	static ArrayList<String> action;
	static ArrayList<Double> reward;
	static ArrayList<Double> Utility;

	static ArrayList<String> performAction;
	static double maxError;
	static double gamma;
	
	public ValueIteration(){
		
		
	}
	
	// Run value iteration algorithm 
	public void VI(){
		
		boolean repeat = true;
		double maxChanges = 0;
		
		while(repeat){
			
			for(int i = 0 ;i <state;i++ ){
				
				double bestactionResult = 0;
				for(int j = 0;j< action.size();j++){
					
					double result = 0;
					ArrayList<Tnode> t = getTmodel(i,action.get(j));
					for(int k = 0 ;k < t.size() ; k++){
						Tnode current  = t.get(k);
					
						result += current.probability * Utility.get(current.nextState);
						
					}
					
					if(result > bestactionResult){
						bestactionResult = result;
						performAction.set(i, action.get(j)); 
					}
					
					
				}
				
				Utility.set(i, bestactionResult + reward.get(i));
		
				
				if( Math.abs(bestactionResult - Utility.get(i)) > maxChanges ){
					maxChanges = Math.abs(bestactionResult - Utility.get(i));
				}
				
			}
			
			if(maxChanges < maxError*(1- gamma)/ gamma);
		}
		
	}
	

	// get all Transition that related to a given state and action
	public static ArrayList<Tnode> getTmodel (int s,String a){
		 ArrayList<Tnode> t = new  ArrayList<Tnode>();
		 
		 for(int i = 0; i < transition.size();i++ ){
			 
			 if((transition.get(i)).find(s,a)){
				 t.add(transition.get(i));
			 }
		 }
		 
		 return t;
	}
	
	// setup the unitilyList
	public static ArrayList<Double> setupUnility() {
		
		for(int i = 0 ;i <state;i++ ){
					
			Utility.add((double) 0);		
		}
		
		return Utility;
		
	}

	//Get the Markov Decision Process Info
	public void getMDP(){
		transition = new ArrayList<Tnode>();
		transition = getTransition(transition);
		

		state = getState();
		
		action = new ArrayList<String>();
		action = getAction(action);
		reward = new ArrayList<Double>();
		reward = getReward(reward,state);
		maxError = getMaxError();
		gamma = getGamma();
		Utility = new ArrayList<Double>();
		Utility = setupUnility();
		performAction = new ArrayList<String>();
		performAction  = setupStateAction();
		
	}
	
	//get max error value
	public double getMaxError(){
		System.out.println("Enter Maxumum Error Allowed in the Utility of any State"); 
		Scanner in = new Scanner(System.in);
		
		double e = in.nextDouble();
		
		return e;
	}
	
	//get gamma value 
	public double getGamma(){
		System.out.println("Enter Gamma/Discount"); 
		Scanner in = new Scanner(System.in);
		
		double e = in.nextDouble();
		
		in.close();
		return e;
	}
	
	
	// set up so that default is no move update as the ValueIteration find better path
	public ArrayList<String> setupStateAction() {
		
		for(int i = 0 ;i <state;i++ ){
			performAction.add("None");
		}
		return performAction;
	}

	//Get Reward for each state
	public static ArrayList<Double> getReward(ArrayList<Double> r , int n) {

		Scanner in = new Scanner(System.in);
		
		
		System.out.println("Enter Reward for each state"); 
		double reward;
		for(int i = 0;i< n;i++){
		
			reward = in.nextDouble();
			r.add(reward);

		}
		
		return r;
	}
	
	//Get action list
	public static ArrayList<String> getAction(ArrayList<String> a){
		System.out.println("Enter Actions Enter done when complete "); 
		Scanner in = new Scanner(System.in);
		String action;
		while(in.hasNext()){
			
			action = in.next();
			if(action.equals("done")){
				break;
				
			}
			a.add(action);


			
		}

		return a;
	}
	
	// get number of number of states and each action to next state information
	public static int getState(){
		
		System.out.println("Enter number of States"); 
		Scanner in = new Scanner(System.in);
		int state;
		state = in.nextInt();
		
		
		return state;
	}
	
	// get Transistion model
	public static ArrayList<Tnode> getTransition(ArrayList<Tnode> t){
		System.out.println("Enter Transition for each state. Enter -1 to quit"); 
		Scanner in = new Scanner(System.in);
		int s;
		String a;
		Double p;
		int n;
		while(in.hasNext()) {
			
			s = in.nextInt();
			if(s == -1){
				break;
			}
			a = in.next();
			p = in.nextDouble();
			n = in.nextInt();
			t.add(new Tnode(s,a,p,n));
			

		}
		//in.close();
		return t;
	}
	
	 public static void main(String args[]) {
		 ValueIteration v = new ValueIteration();
		 v.getMDP();
		 v.VI();
		 
	 }
	
}
