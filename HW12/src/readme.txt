Main is in ValueIteration

Transition input format

//State(int) Action(String) Possaibility(double) NextState(int)

For each given state and action the possability that it will be in a different state.
Enter -1 when complete input the transition model

Ex.

	1,left,0.8,2
    -1
	In state 1 if agent attempt to move left there a 80% percent chance that it will be in state 2




Total number of states 

Ex. 

	2


Reward input must be order by the state (0 to n)

Ex. 
	-0.1 (for state 1)
	2	(for state 2)

Enter All possible action then type "done" when complete

Ex. 
	left 
	right
	up
	down
	done

Enter MAxumum error

Enter Gamma value