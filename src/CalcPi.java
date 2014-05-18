import java.util.ArrayList;

public class CalcPi extends Thread{
	static int gQtdThreads = 1;
	static double gTotIterations = 100000000;
	
	public static boolean TestThreadsRunning(ArrayList<Thread> lThreads){
    //Method to test if all threads already has fisinhed
		for(int i = 0; i < lThreads.size(); i++){
			if(!lThreads.get(i).getState().toString().equals("TERMINATED")){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args){
		//Parsing the arguments
		//Use the --threads to specify the number of "jobs" to calculate
		//Using the --Iterations to specify the size of calc
		if (args.length > 0) {
			for(int i = 0;i < args.length;i++){
				String arguments = args[i];
				if (arguments.contains("--threads")){
					try{
						gQtdThreads = Integer.parseInt(args[i+1]);
					}catch(NumberFormatException e){
						System.out.println("The number is not valid...");
					}
				}
				if (arguments.equals("--Iterations")){
					try{
						gTotIterations = Double.parseDouble(args[i+1]);
					}catch(NumberFormatException e){
						System.out.println("The number is not valid...");
					}
				}
			}
		}
		
		System.out.println("Starting the jobs...");
		System.out.println("Total of process...: "+gQtdThreads);
		System.out.println("Total of iterations...: "+gTotIterations);
		//Defining and initializing the vars.
		double  vIterator, vAcumulator = 0; 
		//Divide the qtd of process by qtd interactions to construct the interator
		vIterator = gTotIterations / gQtdThreads;
		//Attrib. the first acumulator
		vAcumulator = vIterator;
		
		//Creates the array list for objects
		ArrayList<TCalcPi> calcs = new ArrayList<TCalcPi>();
		double vPartial = 0;
		for (int i = 0; i < gQtdThreads; i++){
			String jobName = "JOB--"+i;
			calcs.add(new TCalcPi(jobName, vPartial, vAcumulator));
			vPartial = vAcumulator;
			vAcumulator += vIterator;
		}
		
		ArrayList<Thread> vThreads = new ArrayList<Thread>();
		
		//Loop to add the threads.
		for(int i = 0; i < gQtdThreads; i++){
			vThreads.add(new Thread(calcs.get(i)));
		}
		
		//Date vStartDate = new Date();
		long vStartDate = 0;
		vStartDate = System.currentTimeMillis();
		
		//Loop to start the threads
		for(int i = 0; i < gQtdThreads; i++){
			vThreads.get(i).start();
		}
		System.out.println("Waiting for all threads...");
		
		//Waiting fo all threads...
		while(!TestThreadsRunning(vThreads)){}
		
		//The final time
		long vFinishDate = System.currentTimeMillis();
		//The amount of time to run the threads
		double vProcessTime = vFinishDate - vStartDate;
		
		//Variable to acumulate the results
		double vSum = 0;		
		//Loop to get the values and sum
		for(int i = 0; i < gQtdThreads; i++){
			vSum += calcs.get(i).getSum();
		}
				
		System.out.println("Total:"+vSum*4);		
		//Time spentto process in seconds
		System.out.println("Total time to process: "+vProcessTime/1000+"sec");
	}

}
