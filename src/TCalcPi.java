import java.util.ArrayList;


public class TCalcPi implements Runnable {
	private String gThreadName;
	private double gStart, gFinish = 0;
	private double gSum = 0;
	public TCalcPi(String threadName, double start, double finish){
		gThreadName = threadName;
		gStart      = start;
		gFinish     = finish;
	}

	//@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(gThreadName);
		
		for	(double n = gStart; n <= gFinish; n++) {
			gSum += (Math.pow((-1), n)) / (2 * n + 1);
		}
		
	}
	public double getSum(){
		return gSum;
	}	
}
