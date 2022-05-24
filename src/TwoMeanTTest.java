
public class TwoMeanTTest extends Test {
	//Instance Fields
	private double xBar1, xBar2;
	private int direction;
	private int n1, n2;
	private double stdev1, stdev2;
	
	private double[] data1 = null;
	private double[] data2 = null;
	
	//Constructors
	public TwoMeanTTest() {
		super("Two-sample t-test for a difference of means", 0.05);
		this.xBar1 = 0; this.xBar2 = 0;
		this.n1 = 2; this.n2 = 2;
		this.stdev1 = 1; this.stdev2 = 1;
		this.direction = 0;
	}
	
	public TwoMeanTTest(double[] data1, double[] data2, int direction, double alpha) {
		super("Two-sample t-test for a difference of means", alpha);
		this.data1 = data1; this.data2 = data2;
		this.xBar1 = findMean(data1); this.n1 = data1.length; this.stdev1 = findStDev(data1);
		this.xBar2 = findMean(data2); this.n2 = data2.length; this.stdev2 = findStDev(data2);
		this.direction = direction;
	}
	
	public TwoMeanTTest(double xBar1, int n1, double stdev1, double xBar2, int n2, double stdev2, int direction, double alpha) {
		super("Two-sample t-test for a difference of means", alpha);
		this.xBar1 = xBar1; this.n1 = n1; this.stdev1 = stdev1;
		this.xBar2 = xBar2; this.n2 = n2; this.stdev2 = stdev2;
		this.direction = direction;
	}
	
	//Accessor Methods
	public double getXBar1() {return this.xBar1;} public double getXBar2() {return this.xBar2;}
	public int getDir() {return this.direction;}
	public int getN1() {return this.n1;} public int getN2() {return this.n2;}
	public double getStDev1() {return this.stdev1;} public double getStDev2() {return this.stdev2;}
	
	//https://www.statsdirect.co.uk/help/parametric_methods/utt.htm
	public double getDF() {
		double df = Math.pow(Math.pow(this.getStDev1(), 2)/this.getN1() + Math.pow(this.getStDev2(), 2), 2);
		df /= Math.pow(Math.pow(this.getStDev1(), 2)/this.getN1(),2)/(this.getN1()-1) + Math.pow(Math.pow(this.getStDev2(), 2)/this.getN2(),2)/(this.getN2()-1);
		return df;
	}
	
	@Override
	String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: Random samples of sizes " + this.getN1() + " and " + this.getN2() + " was taken.\n";
		conditions += "2. 10% Rule: The random samples are less than 10% of the total populations, which demonstrates independence of samples.\n";
		if(this.getN1() >= 30 && this.getN2() >= 30) {
			conditions += "3. Normality: We can assume normality for the sampling distribution of sample means because the sample sizes are greater than 30, so therefore the CLT is met";
		} else if(this.data1 != null && this.data2 != null && !areOutliers(this.data1) && !areOutliers(this.data2)) {
			conditions += "3. Normality: We can assume normality for the sampling distributions of sample means because the distributions of both samples are plausibly normal (have no unusual features)";
		} else {
			conditions += "3. Normality: We cannot assume normality for the sampling distributions of sample means; therefore, our results are suspect.";
		}
		return conditions;
	}

	@Override
	String createHypothesis() {
		String hypothesis = "H0: mu1 = mu2\n";
		hypothesis += "Ha: mu1 ";
		if(this.getDir() > 0) {hypothesis += ">";} else if(this.getDir() < 0) {hypothesis += "<";} else {hypothesis += "not equal to";}
		hypothesis += " mu2";
		return hypothesis;
	}

	@Override
	double testStatistic() {
		return (this.getXBar1() - this.getXBar2())/Math.sqrt(Math.pow(this.getStDev1(), 2)/this.getN1() + Math.pow(this.getStDev2(), 2)/this.getN2());
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
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that the true mean ___(mu1) is ";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that the true mean ___(mu1) is ";
		}
		if(this.getDir() > 0) {s += "greater than";}
		else if(this.getDir() < 0) {s += "less than";}
		else {s += "different from";}
		s += " the true mean ___(mu2).";
		return s;
	}

}
