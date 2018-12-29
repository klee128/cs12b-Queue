package que;
import java.io.*;
import java.util.*;

public class Simulation{
	public static void main(String[] args) throws IOException{
		
		//check the command line for one argument
		if(args.length != 1) {
			throw new IOException("Number of arguments is incorrect");
		}
		
		
		
		//open input files and create two output fiels
		Scanner FileScan = new Scanner(new File(args[0]));
		PrintWriter report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
		PrintWriter trace = new PrintWriter(new FileWriter(args[0] + ".trc"));
		
		//crete all the variables you will need here
		int NumJob = Integer.parseInt(FileScan.nextLine());
		int time = 0;
		Queue legit = new Queue();
		Queue backup = new Queue();
		
		while(FileScan.hasNextLine()) {
			Job temp = getJob(FileScan);
			legit.enqueue(temp);
			backup.enqueue(temp);
		}
		
		trace.println("Trace File: " + args[0] + ".trc");
		trace.println(NumJob + " Jobs:");
		trace.println(backup);
		trace.println("");
		
		report.println("Report File: " + args[0] + ".rpt");
		report.println(NumJob + " Jobs:");
		report.println(backup);
		report.println("");
		report.println("***********************************************************");
	//create a for loop that simulates # processors 1->m-1
		for( int n = 1; n < NumJob; n++) {
			//more variables
			int TWait = 0;
			int MWait = 0;
			double AWait = 0.0;
			//use n for number of processors
			Queue[] processors = new Queue[n];
			for(int i = 0; i<n; i++) {
				processors[i] = new Queue();
			}
			
			trace.println("*****************************");
			if(n == 1)
				trace.println(n + " processor:");
			else
				trace.println(n + " processors:");
			trace.println("*****************************");

			trace.println("time=" + time);
			trace.println("0: " + backup);
			for(int i = 1; i< n+1; i++) {
				trace.println(i + ": ");
			
			}
			trace.println("");
		//create a while loop that checks if last Job is finished
			int numofFinishedJobs = 0;
			while(numofFinishedJobs != NumJob) {
				int firstArrive = 1000;
				int firstFinish = 1000;
				int mintime = 1000; //detirmine mintime based on the first arrival and the next finish time
				if(!legit.isEmpty()) { //if legit is not empty(and all the jobs are in processors queues)
					if(((Job)legit.peek()).getFinish() == -1){ //if the head of legit does not have a finish time (aka it is not finished)
						firstArrive = ((Job)legit.peek()).getArrival(); //set first arrival to legit's first arrival
					}
				}
				
				if(!processors[0].isEmpty()){ //if the first processor queue is not empty
					if(((Job)processors[0].peek()).getFinish() != -1){ //if the first processor queue's first job has a finish time
						firstFinish = ((Job)processors[0].peek()).getFinish(); // set initial mintime to the first finish time 
					}	//(might change later if another processor queue's job has an earlier finish time.)	
				}
				
				for(int i =0; i< n; i++) { //checks rest of processor queues for earlier finish times
					if(!processors[i].isEmpty()){
						if(((Job)processors[i].peek()).getFinish() != -1){
							if(((Job)processors[i].peek()).getFinish() < firstFinish) {
								firstFinish = ((Job)processors[i].peek()).getFinish();
							}
						}
					}
				}
				
				if(firstFinish <= firstArrive) { //if the finish is first or they are the same
					mintime = firstFinish;
				}
				else if(firstFinish > firstArrive) { // if the arrive is first
					mintime = firstArrive;
				}
				if(mintime != 1000) {
					time = mintime;
				}
				trace.println("time=" + time);
								
				// when time = finish, dequeue job out of processor + save finish time
				int ff = firstFinish; 
				int t = time;
				int len = processors.length;
				if(ff == t) {
					for(int i = 0; i < processors.length; i++) { //for all processors
						if(!processors[i].isEmpty()) { //if the processor has jobs being processed
						 //if the first job's finish time is the current time 
							if(((Job)processors[i].peek()).getFinish() == time) {
								legit.enqueue(processors[i].peek()); // put the job back onto legit
							processors[i].dequeue(); // take the job off of the processor
							trace.println("0: "  + legit); // prints the updated time and info to trace file
							for(int i1 = 1; i1<n+1;i1++) {
								trace.println(i1 + ": " + processors[i1-1]);
							}
							trace.println("");
							numofFinishedJobs++;
							}	
						}
					}
				}
				
				//when time = arrival, enqueue job into processor
				int minindex = 0;
				if(!legit.isEmpty()) { //if legit is not empty(and all the jobs are in processors queues)
					if(((Job)legit.peek()).getFinish() == -1){ //if the head of legit does not have a finish time (aka it is not finished)
						if(time == ((Job)legit.peek()).getArrival()) {// if time is the same as the first arrival
							for(int i = 1; i < processors.length; i++) { //find smallest processor queue
								if(processors[i].length()<processors[minindex].length())
									minindex = i;
							}
							
							((Job)legit.peek()).computeFinishTime(time); //compute the finish time for the arriving job
							processors[minindex].enqueue(legit.peek()); // places legit's first job onto the smallest processor queue
							legit.dequeue(); // take the job off of legit
							trace.println("0: "  + legit); // prints the updated time and info to trace file
							for(int i = 1; i<n+1;i++) {
								trace.println(i + ": " + processors[i-1]);
							}
							trace.println("");
						}
					}
				}
				
			
			
			//increment time
			}//end while loop
		//compute max ave total wait time
		
		//reset time to 0 for next loop with +1 processor
		time = 0;
		
		Queue finishtemp = new Queue();
		for(int i = 0; i < NumJob; i++) {
			((Job)backup.peek()).resetFinishTime();
			finishtemp.enqueue(((Job)backup.peek()));
			backup.dequeue();
		}
		System.out.println(backup);
		backup = finishtemp;
		legit = backup;
		
		}//end for loop
		
	//close input and output files
		FileScan.close();
		report.close();
		trace.close();
		
	}
	
	public static Job getJob(Scanner in) {
	      String[] s = in.nextLine().split(" ");
	      int a = Integer.parseInt(s[0]);
	      int d = Integer.parseInt(s[1]);
	      return new Job(a, d);
	   }
}