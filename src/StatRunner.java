public class StatRunner {

	public static void main(String[] args) {
		// ONE-SAMPLE Z-TEST FOR A PROPORTION
		
		Test prop1samp = new OnePropZTest(0.5,110,200,1,0.05);
		System.out.print(prop1samp);
		
		// TWO-SAMPLE Z-TEST FOR A DIFFERENCE IN PROPORTIONS
		
		Test prop2samp = new TwoPropZTest(150,200,250,300,-1,0.05);
		System.out.print(prop2samp.toString());
		
		// ONE-SAMPLE T-TEST FOR A MEAN

		double[] means = {5.9,4.2,4.6,5.3,5.1,4.2,4.5,4.9,5.0,5.6};
		Test mean1samp = new OneMeanTTest(means, 5, 0, 0.05);
		System.out.print(mean1samp);
		 
		// TWO-SAMPLE T-TEST FOR A DIFFERENCE OF MEANS
		
		double[] means1 = {5.9,4.2,4.6,5.3,5.1,4.2,4.5,4.9,5.0,5.6};
		double[] means2 = {3.9,4.2,4.6,5.1,4.3,4.7,4.9};
		Test mean2samp = new TwoMeanTTest(means1, means2, -1, 0.05);
		System.out.print(mean2samp);

		// CHI-SQUARED GOODNESS OF FIT TEST
 
		int[] counts = {14,23,22,15,18};
		double[] exp = {18.4,18.4,18.4,18.4,18.4};
		Test cGOF = new ChiSquaredGOF(counts,exp,0.05);
		System.out.print(cGOF);
		
		// CHI-SQUARED TEST OF HOMOGENEITY
		
		int[][] counts2 = {{14,23,22,15,18},{12,25,23,17,20}};
		Test cHo = new ChiSquaredHomogeneity(counts2, 0.05);
		System.out.print(cHo);
		
		// CHI-SQUARED TEST OF INDEPENDENCE
		
		int[][] counts3 = {{14,23,22,15,18},{12,25,23,17,20}};
		Test cI = new ChiSquaredIndependence(counts3, 0.05);
		System.out.print(cI);
		
		
		
	}

}
