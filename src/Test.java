import org.apache.commons.math3.distribution.*;

public abstract class Test {
	//Instance Fields
	private String name;
	private double alpha;
	
	//Constructors
	public Test() {
		this.name = "Undefined Name";
		this.alpha = 0.05;
	}
	
	public Test(String name, double alpha) {
		this.name = name;
		this.alpha = alpha;
	}
	
	//Helper Methods
	
	/* 
	 * Sorts an array of doubles in increasing order
	 * 
	 * Preconditions: 
	 * 		arr is not null
	 * 		arr has at least one element
	 * Postconditions:
	 * 		arr is sorted in increasing order
	 * 
	 * @param arr
	 */
	protected static void sort(double[] arr) {
		int min = 0;
		double temp = 0;
		for(int i = 0; i < arr.length; i++) {
			min = i;
			for(int j = i+1; j < arr.length; j++) {
				if(arr[j] < arr[min]) {
					min = j;
				}
			}
			temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
	}
	
	/*
	 * Finds the expected counts array from the 2D array of counts
	 * 
	 * Preconditions:
	 * 		data is not null
	 * 		all values in data are at least 0
	 * 		data is a rectangular matrix
	 * 		data is at least of length and width 2
	 * Postconditions:
	 * 		an array of doubles is returned
	 * 		the array returned has the same dimensions as data
	 * 
	 * @param data
	 */
	protected static double[][] findExp(int[][] data) {
		double total = 0;
		double[][] exp = new double[data.length][data[0].length];
		for(int[] r : data) {
			for(int c : r) {
				total += c;
			}
		}
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				exp[i][j] = (calcRTotal(data,i) * calcCTotal(data,j))/total;
			}
		}
		return exp;
	}
	
	/*
	 * Calculates the row total of a given 2D array and a specified row
	 * 
	 * Preconditions:
	 * 		arr is not null
	 * 		arr is a rectangular matrix
	 * 		arr is at least of length and width 2
	 * 		0 <= r < arr.length
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is nonnegative
	 * 
	 * @param arr
	 * @param r
	 */
	private static double calcRTotal(int[][] arr, int r) {
		double sum = 0;
		for(int i = 0; i < arr[r].length; i++) {
			sum += arr[r][i];
		}
		return sum;
	}
	
	/*
	 * Calculates the column total of a given 2D array and a specified column
	 * 
	 * Preconditions:
	 * 		arr is not null
	 * 		arr is a rectangular matrix
	 * 		arr is at least of length and width 2
	 * 		0 <= c < arr[0].length
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is nonnegative
	 * 
	 * @param arr
	 * @param c
	 */
	private static double calcCTotal(int[][] arr, int c) {
		double sum = 0;
		for(int i = 0; i < arr.length; i++) {
			sum += arr[i][c];
		}
		return sum;
	}
	
	/*
	 * Calculates the cumulative probability under a standard Normal curve (left) for a given z value
	 * 
	 * Preconditions:
	 * 		z is defined
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is between 0 and 1
	 */
	protected static double normalCDF(double z) {
		NormalDistribution N = new NormalDistribution();
		return N.cumulativeProbability(z);
	}
	
	/*
	 * Calculates the cumulative probability under a Student's T Distribution (left) for a given t value
	 * 
	 * Preconditions:
	 * 		t is defined
	 * 		df > 0
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is between 0 and 1
	 */
	protected static double tcdf(double t, double df) {
		TDistribution T = new TDistribution(df);
		return T.cumulativeProbability(t);
	}
	
	/*
	 * Calculates the mean value of a set of data
	 * 
	 * Preconditions:
	 * 		data is not null
	 * 		data has a length of at least 1
	 * Postconditions:
	 * 		a double is returned
	 */
	protected static double findMean(double[] data) {
		double avg = 0.0;
		for(double d : data) {
			avg += d;
		}
		return avg / data.length;
	}
	
	/*
	 * Calculates the standard deviation of a set of data
	 * 
	 * Preconditions:
	 * 		data is not null
	 * 		data has a length of at least 1
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is greater than or equal to 0
	 */
	protected static double findStDev(double[] data) {
		double avg = findMean(data);
		double sum = 0.0;
		for(double d : data) {
			sum += Math.pow(d - avg, 2);
		}
		return Math.sqrt(sum / data.length);
	}
	
	/*
	 * Finds whether there are outliers in a set of data
	 * 
	 * Preconditions:
	 * 		data is not null
	 * 		data has a length of at least 1
	 * Postconditions:
	 * 		a boolean is returned
	 * 		the returned value indicates whether the outlier test found outliers in the set of data
	 */
	protected static boolean areOutliers(double[] data) {
		sort(data);
		boolean outliers = false;
		int idxQ1 = (int) Math.ceil(0.25 * data.length) - 1;
		int idxQ3 = (int) Math.ceil(0.75 * data.length) - 1;
		double Q1 = data[idxQ1], Q3 = data[idxQ3], IQR = Q3 - Q1;
		for(int i = 0; i < data.length; i++) {
			if(data[i] < Q1 - 1.5*IQR || data[i] > Q3 + 1.5*IQR) {
				outliers = true;
				break;
			}
		}
		return outliers;
	}
	
	//Accessor Methods
	public String getName() {return this.name;}
	public double getAlpha() {return this.alpha;}
	
	public void setName(String name) {this.name = name;}
	public void setAlpha(double alpha) {this.alpha = alpha;}
	
	//Inherited Test Methods
	
	/*
	 * Finds and evaluates conditions for conducting the relevant significance test
	 * 
	 * Postconditions:
	 * 		a reference to a String object is returned
	 * 		all relevant conditions have been checked
	 */
	abstract String checkConditions();
	
	/*
	 * Creates the correct hypothesis for the test of significance, taking into account the correct directions as well
	 * 
	 * Postconditions:
	 * 		a reference to a String object is returned
	 * 		both the null and alternative hypothesis have been created and are within the String object
	 */
	abstract String createHypothesis();
	
	/*
	 * Calculates and returns the relevant test statistic
	 * 
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is within the domain of the test's relevant probability distribution
	 */
	abstract double testStatistic();
	
	/*
	 * Calculates and returns the P-Value of the test
	 * 
	 * Postconditions:
	 * 		a double is returned
	 * 		the returned value is between 0 and 1
	 */
	abstract double pValue();
	
	/*
	 * Writes the conclusion of the test, stating the P-value, the alpha value, and any relevant context
	 * 
	 * Postconditions:
	 * 		a reference to a String object is returned
	 * 		the String consists of the comparison of the P-value to alpha, the rejection or not of the null hypothesis, and the final contextual concluding statement
	 */
	abstract String conclusion();
	
	/*
	 * Returns all parts of the relevant test
	 * 
	 * Postconditions:
	 * 		a reference to a String object is returned
	 * 		the String consists of 
	 * 			the name of the test
	 * 			the hypothesis
	 * 			the conditions
	 * 			the test statistic
	 * 			the P-value
	 * 			the conclusion
	 */
	public String toString() {
		return "-----------------------------------------------------\n" + this.getName() + "\n" + this.createHypothesis() + "\n" + this.checkConditions() + "\n" + "test Statistic = " + this.testStatistic() + "\n P-value = " + this.pValue() + "\n" + this.conclusion() + "\n-----------------------------------------------------\n";
	}
}
