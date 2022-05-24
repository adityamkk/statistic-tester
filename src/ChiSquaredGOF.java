import org.apache.commons.math3.distribution.ChiSquaredDistribution;

public class ChiSquaredGOF extends Test {
	//Instance Fields
	private int[] data;
	private double[] exp;
	private double df;
	
	//Constructors
	public ChiSquaredGOF() {
		super("Chi-Squared Goodness of Fit Test", 0.05);
		this.data = new int[2];
		this.exp = new double[2];
		this.df = 1;
	}
	
	public ChiSquaredGOF(int[] data, double[] exp, double alpha) {
		super("Chi-Squared Goodness of Fit Test", alpha);
		this.data = data;
		this.exp = exp;
		this.df = data.length - 1;
	}
	
	//Accessor Methods
	public int[] getData() {return this.data;}
	public double[] getExp() {return this.exp;}
	public double getDF() {return this.df;}
	
	@Override
	String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: A random sample of size " + this.getDF()+1 + " was taken.\n";
		conditions += "2. 10% Rule: The random sample is less than 10% of the total population, which demonstrates independence of sample.\n";
		for(double d : this.getExp()) {
			if(d <= 5) {
				conditions += "3. Expected counts are not all greater than 5. Therefore, our results are suspect";
				return conditions;
			}
		}
		conditions += "3. Expected counts are all greater than 5.";
		return conditions;
	}

	@Override
	String createHypothesis() {
		return "H0: The distribution of ___ is equal to the expected distribution \nHa: The distribution of ___ is not equal to the expected distribution";
	}

	@Override
	double testStatistic() {
		double sum = 0.0;
		for(int i = 0; i < data.length; i++) {
			sum += Math.pow(this.getData()[i]-this.getExp()[i], 2) / this.getExp()[i];
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
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that the distribution of ___ is not equal to the expected distribution.";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that the distribution of ___ is not equal to the expected distribution.";
		}
		return s;
	}

}
