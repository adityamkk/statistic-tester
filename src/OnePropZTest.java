

public class OnePropZTest extends Test{
	//Instance Fields
	private double pHat;
	private double p0;
	private int direction;
	private int n;
	private double stdev;
	
	//Constructors
	public OnePropZTest() {
		super("One sample z-test for a proportion",0.05);
		this.pHat = 1;
		this.n = 2;
		this.stdev = Math.sqrt(this.pHat*(1-this.pHat)/this.n);
	}
	
	public OnePropZTest(double p0, double x, int n, int direction, double alpha) {
		super("One sample z-test for a proportion", alpha);
		this.pHat = x/n;
		this.p0 = p0;
		this.n = n;
		this.stdev = Math.sqrt(this.pHat*(1-this.pHat)/n);
		this.direction = direction;
	}
	
	//Accessor Methods
	public double getpHat() {return this.pHat;}
	public double getqHat() {return 1-this.pHat;}
	public double getp0() {return p0;}
	public int getDir() {return this.direction;}
	public int getN() {return this.n;}
	public double getStdev() {return this.stdev;}
	
	@Override
	public String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: A random sample of size " + this.getN() + " was taken.\n";
		conditions += "2. 10% Rule: The random sample is less than 10% of the total population, which demonstrates independence of sample.\n";
		if(this.getN()*this.getpHat() >= 10 && this.getN()*this.getqHat() >= 10) {
			conditions += "3. Normality: We can assume plausible normality for the sampling distribution of the sample proportion because\n n*pHat = " + this.getN() + "*" + this.getpHat() + " = " + this.getN()*this.getpHat() + " >= 10 and n*qHat = " + this.getN() + "*" + this.getqHat() + " = " + this.getN()*this.getqHat() + " >= 10"; 
		} else {
			conditions += "3. Normality: We cannot assume plausible normality for the sampling distribution of the sample proportion because\n ";
			if(this.getN()*this.getpHat() < 10) {conditions += "n*pHat = " + this.getN() + "*" + this.getpHat() + " = " + this.getN()*this.getpHat() + " is not >= 10";}
			else {conditions += "n*qHat = " + this.getN() + "*" + this.getqHat() + " = " + this.getN()*this.getqHat() + " is not >= 10";}
		}
		return conditions;
	}

	@Override
	public String createHypothesis() {
		String hypothesis = "H0: p = " + this.getp0() + "\n";
		hypothesis += "Ha: p ";
		if(this.getDir() > 0) {hypothesis += ">";} else if(this.getDir() < 0) {hypothesis += "<";} else {hypothesis += "not equal to";}
		hypothesis += " " + this.getp0();
		return hypothesis;
	}

	@Override
	public double testStatistic() {
		return (this.getpHat()-this.getp0())/(this.getStdev());
	}

	@Override
	public double pValue() {
		double z = this.testStatistic();
		if(this.getDir() > 0) {return 1-normalCDF(z);}
		else if(this.getDir() < 0) {return normalCDF(z);}
		else return (z > 0) ? ((1-normalCDF(z))*2) : (normalCDF(z)*2);
	}

	@Override
	public String conclusion() {
		String s = "";
		if(this.pValue() < this.getAlpha()) {
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that the true proportion of ___ is ";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that the true proportion of ___ is ";
		}
		if(this.getDir() > 0) {s += "greater than";}
		else if(this.getDir() < 0) {s += "less than";}
		else {s += "different from";}
		s += " " + this.getp0() + ".";
		return s;
	}

}
