

public class OneMeanTTest extends Test {
	//Instance Fields
	private double xBar;
	private double x0;
	private int direction;
	private int n;
	private double stdev;
	
	private double[] data = null;
	
	//Constructors
	public OneMeanTTest() {
		super("One-sample t-test for a mean",0.05);
		this.xBar = 0;
		this.x0 = 0;
		this.direction = 0;
		this.n = 2;
		this.stdev = 1;
	}
	
	public OneMeanTTest(double[] data, double x0, int direction, double alpha) {
		super("One-sample t-test for a mean", alpha);
		this.data = data;
		this.xBar = findMean(data);
		this.x0 = x0;
		this.direction = direction;
		this.n = data.length;
		this.stdev = findStDev(data);
	}
	
	public OneMeanTTest(double xBar, double stdev, int n, double x0, int direction, double alpha) {
		super("One-sample t-test for a mean",alpha);
		this.xBar = xBar;
		this.x0 = x0;
		this.direction = direction;
		this.n = n;
		this.stdev = stdev;
	}
	
	//Accessor Methods
	public double getXBar() {return this.xBar;}
	public double getX0() {return this.x0;}
	public int getDir() {return this.direction;}
	public int getN() {return this.n;}
	public double getDF() {return this.n -1;}
	public double getStDev() {return this.stdev;}

	@Override
	String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: A random sample of size " + this.getN() + " was taken.\n";
		conditions += "2. 10% Rule: The random sample is less than 10% of the total population, which demonstrates independence of sample.\n";
		if(this.getN() >= 30) {
			conditions += "3. Normality: We can assume normality for the sampling distribution of sample means because the sample size is greater than 30, so therefore the CLT is met";
		} else if(this.data != null && !areOutliers(this.data)) {
			conditions += "3. Normality: We can assume normality for the sampling distribution of sample means because the distribution of the sample is plausibly normal (has no unusual features)";
		} else {
			conditions += "3. Normality: We cannot assume normality for the sampling distribution of sample means; therefore, our results are suspect.";
		}
		return conditions;
	}

	@Override
	String createHypothesis() {
		String hypothesis = "H0: mu = " + this.getX0() + "\n";
		hypothesis += "Ha: mu ";
		if(this.getDir() > 0) {hypothesis += ">";} else if(this.getDir() < 0) {hypothesis += "<";} else {hypothesis += "not equal to";}
		hypothesis += " " + this.getX0();
		return hypothesis;
	}

	@Override
	double testStatistic() {
		return (this.xBar - this.x0)/this.stdev;
	}

	@Override
	double pValue() {
		double t = this.testStatistic();
		if(this.getDir() > 0) {return 1-tcdf(t,this.getDF());}
		else if(this.getDir() < 0) {return tcdf(t,this.getDF());}
		else return (t > 0) ? ((1-tcdf(t,this.getDF()))*2) : (tcdf(t,this.getDF())*2);
	}

	@Override
	String conclusion() {
		String s = "";
		if(this.pValue() < this.getAlpha()) {
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that the true mean ___ is ";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that the true mean ___ is ";
		}
		if(this.getDir() > 0) {s += "greater than";}
		else if(this.getDir() < 0) {s += "less than";}
		else {s += "different from";}
		s += " " + this.getX0() + ".";
		return s;
	}

}
