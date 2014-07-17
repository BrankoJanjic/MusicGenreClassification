package is.fon.rs.classification;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;

public class GanreClassifier {

	private Classifier classifier;

	public Classifier getClassifier() {
		return classifier;
	}

	public void setClassifier(Classifier classifier) {
		this.classifier = classifier;
	}

	public GanreClassifier(Classifier classifier) {

		this.classifier = classifier;

		if (classifier instanceof IBk) {
			buildKNN_L1();
		}

		if (classifier instanceof NaiveBayes) {
			buildNaiveBayes();
		}

		if (classifier instanceof RandomForest) {
			buildRandomForest();
		}
		if (classifier instanceof SMO) {
			buildSMO();
		}
		
		if (classifier instanceof J48) {
			buildJ48();
		}
		
	}
	
	public GanreClassifier(Classifier classifier, boolean useRBFKernel) {

		boolean build = false;
		if (!(classifier instanceof SMO)) {
			System.err.println("Classifier is not instance of SMO");
		}
		
		if (classifier instanceof SMO && useRBFKernel== true) {
			buildSMO();
			build = true;
		}
		
		if (classifier instanceof SMO && !build) {
			buildSMOwithRBF();
		}
		

	}


	public GanreClassifier(Classifier classifier, int euklidianDistance) {

		if (!(classifier instanceof IBk)) {
			System.err.println("Classifier is not instance of IBK");
		}
		boolean build = false;
		if (classifier instanceof IBk && euklidianDistance==2) {
			buildKNN_L2();
			build=true;
		}

		if (classifier instanceof IBk && !build) {
			buildKNN_L1();
		}
		
	
	}

	private void buildKNN_L1() {

		try {
			if (classifier != null) {
				classifier = new IBk(1);
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
	}

	private void buildKNN_L2() {

		try {
			if (classifier != null) {
				classifier = new IBk(2);
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
	}

	private void buildNaiveBayes() {

		try {
			if (classifier != null) {
				classifier = new NaiveBayes();
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
	}

	private void buildRandomForest() {

		try {
			if (classifier != null) {
				classifier = new RandomForest();
				((RandomForest) classifier).setNumTrees(10);
				((RandomForest) classifier).setNumFeatures(0);
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
		
		
	}
	
	private void buildSMO() {

		try {
			if (classifier != null) {
				classifier = new SMO();
				((SMO)classifier).setKernel(new PolyKernel());
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
		
		
	}
	
	private void buildSMOwithRBF() {

		try {
			if (classifier != null) {
				classifier = new SMO();
				((SMO)classifier).setKernel(new RBFKernel());
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
		
		
	}
	
	private void buildJ48() {

		try {
			if (classifier != null) {
				classifier = new J48();
				((J48)classifier).setConfidenceFactor((float) 0.25);
				((J48)classifier).setMinNumObj(2);
			}
		} catch (NullPointerException e) {
			System.err.println("The classifier is not initialized ! ");
			e.printStackTrace();
		}
		
		
	}
	
	

}
