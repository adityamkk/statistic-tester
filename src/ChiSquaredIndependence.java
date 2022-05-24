import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class ChiSquaredIndependence extends Test {
	//Instance Fields
	private int[][] data;
	private double[][] exp;
	private double df;
	
	//Constructors
	public ChiSquaredIndependence() {
		super("Chi-Squared Test of Independence", 0.05);
		this.data = new int[2][2];
		this.exp = new double[2][2];
		this.df = 1;
	}
	
	public ChiSquaredIndependence(int[][] data, double alpha) {
		super("Chi-Squared Test of Independence", alpha);
		this.data = data;
		this.exp = findExp(data);
		this.df = (data.length-1)*(data[0].length-1);
	}
	
	//Accessor Methods
	public int[][] getData() {return this.data;}
	public double[][] getExp() {return this.exp;}
	public double getDF() {return this.df;}

	@Override
	String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: A random sample of size " + this.getDF()+1 + " was taken.\n";
		conditions += "2. 10% Rule: The random sample is less than 10% of the total population, which demonstrates independence of sample (but not relative population distributions).\n";
		for(double[] r : this.getExp()) {
			for(double c : r) {
				if(c <= 5) {
					conditions += "3. Expected counts are not all greater than 5. Therefore, our results are suspect";
					return conditions;
				}
			}
		}
		conditions += "3. Expected counts are all greater than 5.";
		return conditions;
	}

	@Override
	String createHypothesis() {
		return "H0: There is no association between the different variables \nHa: There is an association between the different variables";
	}

	@Override
	double testStatistic() {
		double sum = 0;
		for(int i = 0; i < this.getData().length; i++) {
			for(int j = 0; j < this.getData()[i].length; j++) {
				sum += Math.pow(this.getData()[i][j] - this.getExp()[i][j], 2)/this.getExp()[i][j];
			}
		}
		return sum;
	}

	@Override
	double pValue() {
		ChiSquaredDistribution C = new ChiSquaredDistribution(this.getDF());
		return C.cumulativeProbability(this.testStatistic());
	}

	@Override
	String conclusion() {
		String s = "";
		if(this.pValue() < this.getAlpha()) {
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that there is an association between the different variables.";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that there is an association between the different variables.";
		}
		return s;
	}

}