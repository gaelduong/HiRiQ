import java.util.ArrayList;
import java.util.Arrays;

public class Solve {
	public static void main(String[] args) 
	{
		// list of triplets
		final byte[][] triplets = {{0,1,2},{3,4,5},	{6,7,8},{7,8,9},{8,9,10},{9,10,11},{10,11,12},{13,14,15},{14,15,16},{15,16,17},{16,17,18},{17,18,19},
							{20,21,22},{21,22,23},{22,23,24},{23,24,25},{24,25,26},{27,28,29},{30,31,32},{12,19,26},{11,18,25},
							{2,5,10},{5,10,17},{10,17,24},{17,24,29},{24,29,32},{1,4,9},{4,9,16},{9,16,23},{16,23,28},{23,28,31},
							{0,3,8},{3,8,15},{8,15,22},{15,22,27},{22,27,30},{7,14,21},{6,13,20}};
					
				
//--------------------------------------------------------------------------------------------------
	
		//I DID NOT USE TREE TO SOLVE THIS PROBLEM. I ONLY USED RECURSION. IT CAN SOLVE UP TO 7 STEPS AWAY UNDER 5 MINUTES
		// SINCE MY CODE MIGH BE A BIT HARD TO UNDERSTAND, PLEASE TAKE A LOOK AT THE WORD DOCUMENT TO GET MY GENERAL IDEA
		
		class HiRiQ
	{
		public int config;
		public byte weight;

		//initialize the configuration to one of 4 START setups n=0,1,2,3
		HiRiQ(byte n)
		{
			if (n==0)
			{config=65536/2;weight=1;}
			else
				if (n==1)
				{config=146964992;weight=6;}
				else
					if (n==2)
					{config=401608; weight=6;}
					else
					{config=-2147450879; weight=32;}
		}		

		boolean IsSolved()
		{
			return( (config==65536/2) && (weight==1) );
		}

		//transforms the array of 33 booleans to an (int) config and a (byte) weight.
		public void store(boolean[] B)
		{
			int a=1;
			config=0;
			weight=(byte) 0;
			if (B[0]) {weight++;}
			for (int i=1; i<32; i++)
			{
				if (B[i]) {config=config+a;weight++;}
				a=2*a;
			}
			if (B[32]) {config=-config;weight++;}
		}
		
		//transform the int representation to an array of booleans.
		//the weight (byte) is necessary because only 32 bits are memorized
		//and so the 33rd is decided based on the fact that the config has the
		//correct weight or not.
		public boolean[] load(boolean[] B)
		{
			byte count=0;
			int fig=config;
			B[32]=fig<0;
			if (B[32]) {
				fig=-fig;
				count++;
				}
			int a=2;
			for (int i=1; i<32; i++)
			{
				B[i]= fig%a>0;
				if (B[i]) {
					fig=fig-a/2;
					count++;
					}
				a=2*a;
			}
			B[0]= count<weight;
			return(B);
		}
// NEW METHOD! ATTENTION! ATTENTION! ATTENTION! ATTENTION! ATTENTION!
		//coolStore will store int[] I (holding 1's OR 0's) array instead of boolean[] B (TRUE's OR FALSE's)
		//1 is true, 0 is false
		//it's almost the same as method "store" 
		public void coolStore(int[] I)
		{
			int a=1;
			config=0;
			weight=(byte) 0;
			if (I[0] == 1) {weight++;}
			for (int i=1; i<32; i++)
			{
				if (I[i] == 1) {config=config+a;weight++;}
				a=2*a;
			}
			if (I[32] == 1) {config=-config;weight++;}
		}

// NEW METHOD! ATTENTION! ATTENTION! ATTENTION! ATTENTION! ATTENTION!
		//coolLoad will transform int to int[] I (holding 1's OR 0's) array instead of boolean[] B (TRUE's OR FALSE's)
		//1 is true, 0 is false
		//it's almost the same as method "store" 
		
		public int[] coolLoad(int[] I)
		{
			byte count=0;
			int fig=config;
			
			if(fig<0) {I[32] = 1;} else{I[32] = 0;}
			
			if (I[32] == 1) {
				fig=-fig;
				count++;
				}
			int a=2;
			for (int i=1; i<32; i++)
			{
				if(fig%a>0) {I[i] = 1;} else{I[i] = 0;}
				
				if (I[i] == 1) {
					fig=fig-a/2;
					count++;
					}
				a=2*a;
			}
			if(count<weight) {I[0] = 1;} else{I[0] = 0;}
			return(I);
		}
		
		//prints the int representation to an array of booleans.
		//the weight (byte) is necessary because only 32 bits are memorized
		//and so the 33rd is decided based on the fact that the config has the
		//correct weight or not.
		public void printB(boolean Z)
		{if (Z) {System.out.print("[ ]");} else {System.out.print("[@]");}}

		public void print()
		{
			byte count=0;
			int fig=config;
			boolean next,last=fig<0;
			if (last) {fig=-fig;count++;}
			int a=2;
			for (int i=1; i<32; i++)
			{
				next= fig%a>0;
				if (next) {fig=fig-a/2;count++;}
				a=2*a;
			}
			next= count<weight;

			count=0;
			fig=config;
			if (last) {fig=-fig;count++;}
			a=2;

			System.out.print("      ") ; printB(next);
			for (int i=1; i<32; i++)
			{
				next= fig%a>0;
				if (next) {fig=fig-a/2;count++;}
				a=2*a;
				printB(next);
				if (i==2 || i==5 || i==12 || i==19 || i==26 || i==29) {System.out.println() ;}
				if (i==2 || i==26 || i==29) {System.out.print("      ") ;};
			}
			printB(last); System.out.println() ;

		}
		
		
//--------------------------------------------------------------------------------------------------	
		//METHOD THAT SWITCHES THE TRIPLET (EITHER BLACK OF WHITE-SUB)
		public void switchTrip (int[] hiQ, byte [] trip){
			
			boolean caseOne = (hiQ[trip[0]] == hiQ[trip[1]]);
			boolean caseTwo = (hiQ[trip[1]] == hiQ[trip[2]]);
			
			if(caseOne)
			{
				hiQ[trip[0]]= hiQ[trip[2]];
				hiQ[trip[2]]= hiQ[trip[1]];
				hiQ[trip[1]]= hiQ[trip[0]];
			}
			else if(caseTwo)
			{
				hiQ[trip[2]]= hiQ[trip[0]];
				hiQ[trip[0]]= hiQ[trip[1]];
				hiQ[trip[1]]= hiQ[trip[2]];
			}		
			
		}
		
//--------------------------------------------------------------------------------------------------
		//METHOD finalSolve TAKES INPUT FROM TA
		//WHAT THIS METHOD DOES:
		//- TRANSFORM BOOLEAN[] HQ (TRUE/FALSE) (input) TO INT[] (1,0) size of 43 (Why 43? Explained in Word doc)
		//- CALLS THE RECURSIVE METHOD "solve" below
		
		public String finalSolve(boolean[] HiRiQBoard, int currentLevel)
		{
			HiRiQ info = new HiRiQ((byte)1);
			//Now transform puzzle to int[]
			int[] numbers = new int[43]; 
			for(int i = 0; i < HiRiQBoard.length; i++)
			{
				if(HiRiQBoard[i] == true){numbers[i] = 1;}
				else numbers[i] = 0;
			}
			
			ArrayList<int []> ListOfNumbers = new ArrayList<int[]>();
			ListOfNumbers.add(numbers);
			return info.solve(ListOfNumbers, currentLevel);
		}
//--------------------------------------------------------------------------------------------------
		//HERE IS THE MOST IMPORTANT CODE
		//RECURSIVE METHOD solve WILL RETURN THE SEQUENCE OF MOVES
		//INPUT: ARRAY LIST OF INT[] and currentLevel (starting from 0 , +1 for each recursion)
		public String solve(ArrayList<int []> childrenList, int currentLevel){ 
			// the arrayList will first contain the given config

			int counter=1;
			
			//now we create ANOTHER arrayList that will, (at currentLevel=0), contain the children of the given config 
			ArrayList<int []> newChildrenList = new ArrayList<int []>();
			
			//initialize object HiRiQ info
			HiRiQ info= new HiRiQ ((byte)1);
			
			//go through childrenList to check each int[]
			//so at the beginning (currentLevel = 0), thechildrenList.size is 1
			for(int i = 0; i < childrenList.size(); i++){
				//now for each int[], check 38 triplets to see if
				for(int j = 0; j < 38; j++)
				{
					if(childrenList.get(i)[triplets[j][0]]!=childrenList.get(i)[triplets[j][2]]) //check if there is a valid sub
					{ 
						//so if i = 3; j = 2. That means it's checking the 4th int[] in ChildrenList and check the 3th triplet.
						//System.out.println(counter);
						int [] temp = new int [43]; // create int [] temp of size 43
						
						info.coolStore(childrenList.get(i)); // this is where info comes to play, it will transform the int[] to config/weight, so info will represent int[]
						int numWhites = info.weight;
						
						temp = info.coolLoad(temp); //now it transforms (config/weight) info to int[] temp, which is identical to childrenList.get(i)
						
						info.switchTrip(temp, triplets[j]); //now we apply switch to temp, and not to thechildrenList.get(i)
						info.coolStore(temp); //we store int[]temp into config/weight info
						
						
					
						//Here is the interesting part
						//First we copy from childrenList.get(i)[33] to childrenList.get(i)[33 + currentlevel], because if we don't, we'll lose elements in next recursion
						for(int h = 33; h < 34 + currentLevel; h++) {temp[h] = childrenList.get(i)[h];}
						
						//After the switch, we want to ADD THE MOVE to temp[] at position 33 or higher (from 33 to 33 + current)
						//(ex: 3@5, so we add 35 to temp. Temp will now look like this: {0,1,0,0,1,....0,1,35,...}) 
						
						//Let's say the triplet is {3,4,5}, we will make a String containing "35"
						String storeMoves = triplets[j][0] + "" + triplets[j][2];
						//and then we convert that String to int
						int moves = Integer.parseInt(storeMoves);
						//now we add that to temp[] at position 33 or higher (from 33 to 33+currentLevel)
						temp[33+currentLevel] = moves;
				
						//info.print();
						//Now we check if the child is solved
						if(info.IsSolved())
							
						{ //so after the switch, if info is solved then return, stop recursion
							System.out.println("Yes I can!");
							info.print(); //print the solved config		
							System.out.println();
							System.out.println("Follow these moves:");
							return getSequenceWithSymbol(temp);	 
							//this will get the moves from temp and return a sequence of moves
							//Please see method getSequenceWithSymbol down below
						}
						
						//info.print();						
						//System.out.println();
						
						//if is not solved then add the child to the newChildrenList array list, 
						newChildrenList.add(temp); //<child1, child2,child3,....>
						info.coolStore(childrenList.get(i));
						counter++;
						
					}
					else{continue;} // if there is no sub in int[] at ith triplets then continue check (i+1)th triplets
				}	
			}			
			return solve(newChildrenList, currentLevel+1);			
		}
		
	} //end HiRiQ class
	
//--------------------------------------------------------------------------------------------------	
	//TEST
	//Test finalSolve method
	boolean[] pleaseTest = new boolean[33];
	
		HiRiQ M = new HiRiQ((byte)1);
		
		
		//Example 1:
		pleaseTest = M.load(pleaseTest);		
		System.out.println("Hey you, can you solve this?");
		M.print();
		System.out.println();
		
		
		System.out.println(M.finalSolve(pleaseTest,0));
		
		//Example:2
		
		boolean[] wow = new boolean[33];
		wow[4] = wow[7] = wow[8] = wow[14] = wow[18] = wow[19] = true;
		
		M.store(wow);
		System.out.println("----------------------------------------------");
		System.out.println("Hey you, can you solve this?");
		M.print();
		System.out.println(M.finalSolve(wow,0));
		
		
}//end main
	
//--------------------------------------------------------------------------------------------------
	//WHAT THIS METHOD DOES?
	//GET THE MOVES FROM THE SOLVED CHILD (an int[]) at position i=33,34,.... and convert it to a sequence of Moves (String)
	//INPUT : int[] = {1,0,1,0,.....,0, 35, 911, 1417, 416,..} (35,911,1416,416 are the moves to solve puzzle that are store in each child)
	//OUTPUT: String = 3@5 9@11 14@17 4@16
	
	
	public static String getSequenceWithSymbol(int[] array) //in method "solve", array would be the child 
	{
		//first store int[] array to String[] list
		String[] list = new String[array.length-33]; 
		// we're only interested what's after position 33  because the moves are stored after 32th position
	    
		for(int i = 33; i < array.length; i++)
		{			
			list[i-33] = "" + array[i];
		}

		//Convert list of String to String
		String sequence = "";
		
		for(int i = 0; i < list.length; i++)
		{
			if(list[i].length() == 1)//ex: if 2 (string.length = 1) then output 0@2
			{
				if(list[i].charAt(0) != '0')
				sequence = sequence + '0' + "@" + list[i].charAt(0) + " ";
			}
			else if(list[i].length() == 2) //ex: if 35 (string.length = 2) then output 3@5
			{
				sequence = sequence + list[i].charAt(0) + "@" + list[i].charAt(1) +" ";
			}
			else if(list[i].length() == 3) //ex: if 911 (string.length = 3) then output 9@11 (and not 91@1)
			{
				sequence = sequence + list[i].charAt(0) + "@" + list[i].charAt(1) + "" + list[i].charAt(2) + " ";
			}
			else if(list[i].length() == 4) //ex: if 1416 (string.length = 4) then output 14@16
			{
				
				sequence = sequence + list[i].charAt(0) + "" + list[i].charAt(1) + "@" + list[i].charAt(2) + list[i].charAt(3) + " ";
			}
		
		}
		return sequence;
	}
}
