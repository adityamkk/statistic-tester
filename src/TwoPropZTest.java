
public class TwoPropZTest extends Test {
	//Instance Fields
	private double p1Hat, p2Hat;
	private int direction;
	private int n1, n2;
	private double stDev1, stDev2;
	
	//Constructors
	public TwoPropZTest() {
		super("Two sample z-test for a difference in proportions",0.05);
		this.p1Hat = 0.5; this.p2Hat = 0.5;
		this.direction = 0;
		this.n1 = 2; this.n2 = 2;
		this.stDev1 = Math.sqrt(this.p1Hat*(1-this.p1Hat)/this.n1); this.stDev2 = Math.sqrt(this.p2Hat*(1-this.p2Hat)/this.n2);
	}
	
	public TwoPropZTest(int x1, int n1, int x2, int n2, int direction, double alpha) {
		super("Two sample z-test for a difference in proportions",alpha);
		this.p1Hat = (double)(x1)/n1; this.p2Hat = (double)(x2)/n2;
		this.direction = direction;
		this.n1 = n1; this.n2 = n2;
		this.stDev1 = Math.sqrt(this.p1Hat*(1-this.p1Hat)/this.n1); this.stDev2 = Math.sqrt(this.p2Hat*(1-this.p2Hat)/this.n2);
	}
	
	//Accessor Methods
	public double getp1Hat() {return this.p1Hat;}
	public double getq1Hat() {return 1-this.p1Hat;}
	public double getp2Hat() {return this.p2Hat;}
	public double getq2Hat() {return 1-this.p2Hat;}
	public int getDir() {return this.direction;}
	public int getN1() {return this.n1;}
	public int getN2() {return this.n2;}
	public double getStdev1() {return this.stDev1;}
	public double getStdev2() {return this.stDev2;}
	
	@Override
	public String checkConditions() {
		String conditions = "Conditions:\n";
		conditions += "1. Randomization: A random sample of size " + this.getN1() + " and size " + this.getN2() + " was taken.\n";
		conditions += "2. 10% Rule: The random samples are both less than 10% of the total population, which demonstrates independence of samples.\n";
		if(this.getN1()*this.getp1Hat() >= 10 && this.getN1()*this.getq1Hat() >= 10 && this.getN2()*this.getp2Hat() >= 10 && this.getN2()*this.getq2Hat() >= 10) {
			conditions += "3. Normality: We can assume plausible normality for the sampling distribution of the sample proportion because\n n1*p1Hat = " + this.getN1() + "*" + this.getp1Hat() + " = " + this.getN1()*this.getp1Hat() + " >= 10, n1*q1Hat = " + this.getN1() + "*" + this.getq1Hat() + " = " + this.getN1()*this.getq1Hat() + " >= 10, n2*p2Hat = " + this.getN2() + "*" + this.getp2Hat() + " = " + this.getN2()*this.getp2Hat() + " >= 10, n2*q2Hat = " + this.getN2() + "*" + this.getq2Hat() + " = " + this.getN2()*this.getq2Hat() + " >= 10,"; 
		} else {
			conditions += "3. Normality: We cannot assume plausible normality for the sampling distribution of the sample proportion because\n ";
			if(this.getN1()*this.getp1Hat() < 10) {conditions += "n1*p1Hat = " + this.getN1() + "*" + this.getp1Hat() + " = " + this.getN1()*this.getp1Hat() + " is not >= 10";}
			else if(this.getN1()*this.getq1Hat() < 10) {conditions += "n1*q1Hat = " + this.getN1() + "*" + this.getq1Hat() + " = " + this.getN1()*this.getq1Hat() + " is not >= 10";}
			else if(this.getN2()*this.getp1Hat() <10) {conditions += "n2*p2Hat = " + this.getN2() + "*" + this.getp2Hat() + " = " + this.getN2()*this.getp2Hat() + " is not >= 10";}
			else {conditions += "n2*q2Hat = " + this.getN2() + "*" + this.getq2Hat() + " = " + this.getN2()*this.getq2Hat() + " is not >= 10";}
		}
		return conditions;
	}

	@Override
	public String createHypothesis() {
		String hypothesis = "H0: p1 = p2\n";
		hypothesis += "Ha: p1 ";
		if(this.getDir() > 0) {hypothesis += ">";} else if(this.getDir() < 0) {hypothesis += "<";} else {hypothesis += "not equal to";}
		hypothesis += " p2";
		return hypothesis;
	}

	@Override
	public double testStatistic() {
		return ((this.getp1Hat()-this.getp2Hat()))/Math.sqrt(Math.pow(this.getStdev1(),2) + Math.pow(this.getStdev2(), 2));
	}

	@Override
	double pValue() {
		double z = this.testStatistic();
		if(this.getDir() > 0) {return 1-normalCDF(z);}
		else if(this.getDir() < 0) {return normalCDF(z);}
		else return (z > 0) ? ((1-normalCDF(z))*2) : (normalCDF(z)*2);
	}

	@Override
	String conclusion() {
		String s = "";
		if(this.pValue() < this.getAlpha()) {
			s = "Since the P-value is less than alpha, we reject the null hypothesis. \nWe have convincing statistical evidence that the true proportion of ___ is ";
		} else {
			s = "Since the P-value is greater than alpha, we fail to reject the null hypothesis. \nWe lack convincing statistical evidence that the true proportion of ___ (p1) is ";
		}
		if(this.getDir() > 0) {s += "greater than";}
		else if(this.getDir() < 0) {s += "less than";}
		else {s += "different from";}
		s += " the true proportion of ___ (p2).";
		return s;
	}

}
