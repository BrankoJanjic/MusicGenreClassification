package is.fon.rs.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.functions.supportVector.PolyKernel;
import weka.classifiers.functions.supportVector.RBFKernel;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Discretize;

public class Test {
	static Instances dataSet;
	static FastVector attributes;

	@SuppressWarnings("unused")
	private static Instances kreirajTestInstancu(double[] niz, double niz1[])
			throws IOException {

		FastVector ganreNominalValues = new FastVector();
		// adding nominal values
		ganreNominalValues.addElement("classical");
		ganreNominalValues.addElement("jazz");
		ganreNominalValues.addElement("metal");
		ganreNominalValues.addElement("pop");
		ganreNominalValues.addElement("disco");
		ganreNominalValues.addElement("rock");
		ganreNominalValues.addElement("reggae");

		Attribute ganre = new Attribute("ganre", ganreNominalValues);

		Attribute mfcc1 = new Attribute("mfcc1");
		Attribute mfcc2 = new Attribute("mfcc2");
		Attribute mfcc3 = new Attribute("mfcc3");
		Attribute mfcc4 = new Attribute("mfcc4");
		Attribute mfcc5 = new Attribute("mfcc5");
		Attribute mfcc6 = new Attribute("mfcc6");
		Attribute mfcc7 = new Attribute("mfcc7");
		Attribute mfcc8 = new Attribute("mfcc8");
		Attribute mfcc9 = new Attribute("mfcc9");
		Attribute mfcc10 = new Attribute("mfcc10");
		Attribute mfcc11 = new Attribute("mfcc11");
		Attribute mfcc12 = new Attribute("mfcc12");
		Attribute t1 = new Attribute("t1");
		Attribute t2 = new Attribute("t2");
		Attribute t3 = new Attribute("t3");
		Attribute t4 = new Attribute("t4");
		Attribute t5 = new Attribute("t5");
		Attribute t6 = new Attribute("t6");
		Attribute t7 = new Attribute("t7");
		Attribute t8 = new Attribute("t8");
		Attribute t9 = new Attribute("t9");
		Attribute t10 = new Attribute("t10");
		Attribute t11 = new Attribute("t11");
		Attribute t12 = new Attribute("t12");
		FastVector attributes = new FastVector(25);
		attributes.addElement(mfcc1);
		attributes.addElement(mfcc2);
		attributes.addElement(mfcc3);
		attributes.addElement(mfcc4);
		attributes.addElement(mfcc5);
		attributes.addElement(mfcc6);
		attributes.addElement(mfcc7);
		attributes.addElement(mfcc8);
		attributes.addElement(mfcc9);
		attributes.addElement(mfcc10);
		attributes.addElement(mfcc11);
		attributes.addElement(mfcc12);
		attributes.addElement(t1);
		attributes.addElement(t2);
		attributes.addElement(t3);
		attributes.addElement(t4);
		attributes.addElement(t5);
		attributes.addElement(t6);
		attributes.addElement(t7);
		attributes.addElement(t8);
		attributes.addElement(t9);
		attributes.addElement(t10);
		attributes.addElement(t11);
		attributes.addElement(t12);
		attributes.addElement(ganre);
		Instances dataSet = new Instances("dataSet", attributes, 0);
		dataSet.setClass(ganre);

		Instance i1 = new Instance(25);
		i1.setDataset(dataSet);
		// konstruktor prima atribut i vrednost
		i1.setValue((Attribute) attributes.elementAt(0), niz[0]);
		i1.setValue((Attribute) attributes.elementAt(1), niz[1]);
		i1.setValue((Attribute) attributes.elementAt(2), niz[2]);
		i1.setValue((Attribute) attributes.elementAt(3), niz[3]);
		i1.setValue((Attribute) attributes.elementAt(4), niz[4]);
		i1.setValue((Attribute) attributes.elementAt(5), niz[5]);
		i1.setValue((Attribute) attributes.elementAt(6), niz[6]);
		i1.setValue((Attribute) attributes.elementAt(7), niz[7]);
		i1.setValue((Attribute) attributes.elementAt(8), niz[8]);
		i1.setValue((Attribute) attributes.elementAt(9), niz[9]);
		i1.setValue((Attribute) attributes.elementAt(10), niz[10]);
		i1.setValue((Attribute) attributes.elementAt(11), niz[11]);
		i1.setValue((Attribute) attributes.elementAt(12), niz1[0]);
		i1.setValue((Attribute) attributes.elementAt(13), niz1[1]);
		i1.setValue((Attribute) attributes.elementAt(14), niz1[2]);
		i1.setValue((Attribute) attributes.elementAt(15), niz1[3]);
		i1.setValue((Attribute) attributes.elementAt(16), niz1[4]);
		i1.setValue((Attribute) attributes.elementAt(17), niz1[5]);
		i1.setValue((Attribute) attributes.elementAt(18), niz1[6]);
		i1.setValue((Attribute) attributes.elementAt(19), niz1[7]);
		i1.setValue((Attribute) attributes.elementAt(20), niz1[8]);
		i1.setValue((Attribute) attributes.elementAt(21), niz1[9]);
		i1.setValue((Attribute) attributes.elementAt(22), niz1[10]);
		i1.setValue((Attribute) attributes.elementAt(23), niz1[11]);

		dataSet.add(i1);
		return dataSet;

	}

	public static Instance createInstance(double niz[], double niz1[],
			String ganre) {
		// kreiramo jednu instancu
		Instance i1 = new Instance(25);
		i1.setDataset(dataSet);
		// konstruktor prima atribut i vrednost
		i1.setValue((Attribute) attributes.elementAt(0), niz[0]);
		i1.setValue((Attribute) attributes.elementAt(1), niz[1]);
		i1.setValue((Attribute) attributes.elementAt(2), niz[2]);
		i1.setValue((Attribute) attributes.elementAt(3), niz[3]);
		i1.setValue((Attribute) attributes.elementAt(4), niz[4]);
		i1.setValue((Attribute) attributes.elementAt(5), niz[5]);
		i1.setValue((Attribute) attributes.elementAt(6), niz[6]);
		i1.setValue((Attribute) attributes.elementAt(7), niz[7]);
		i1.setValue((Attribute) attributes.elementAt(8), niz[8]);
		i1.setValue((Attribute) attributes.elementAt(9), niz[9]);
		i1.setValue((Attribute) attributes.elementAt(10), niz[10]);
		i1.setValue((Attribute) attributes.elementAt(11), niz[11]);
		i1.setValue((Attribute) attributes.elementAt(12), niz1[0]);
		i1.setValue((Attribute) attributes.elementAt(13), niz1[1]);
		i1.setValue((Attribute) attributes.elementAt(14), niz1[2]);
		i1.setValue((Attribute) attributes.elementAt(15), niz1[3]);
		i1.setValue((Attribute) attributes.elementAt(16), niz1[4]);
		i1.setValue((Attribute) attributes.elementAt(17), niz1[5]);
		i1.setValue((Attribute) attributes.elementAt(18), niz1[6]);
		i1.setValue((Attribute) attributes.elementAt(19), niz1[7]);
		i1.setValue((Attribute) attributes.elementAt(20), niz1[8]);
		i1.setValue((Attribute) attributes.elementAt(21), niz1[9]);
		i1.setValue((Attribute) attributes.elementAt(22), niz1[10]);
		i1.setValue((Attribute) attributes.elementAt(23), niz1[11]);
		i1.setValue((Attribute) attributes.elementAt(24), ganre);

		/*
		 * // drugi nacin i1.setValue(dataSet.attribute(0), v1);
		 * i1.setValue(dataSet.attribute(1), v2);
		 * i1.setValue(dataSet.attribute(2), v3);
		 * i1.setValue(dataSet.attribute(3), v4);
		 * i1.setValue(dataSet.attribute(4), v5);
		 */
		return i1;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {

		dataSet = DataSource.read("./both100.arff");
		dataSet.setClassIndex(dataSet.numAttributes() - 1);
		// String [] nominalValues =
		// {"classical","disco","jazz","metal","pop","reggae","rock"};
		// ClassificationController controller = new
		// ClassificationController(new GanreClassifier(new NaiveBayes()));
		/*
		 * controller.setDataSet(controller.createInstancesMFCCAndPitchModel(
		 * nominalValues)); controller.addSongToDataSet(new
		 * File("./genre_data/data/rock/rock.00003.au"), "rock");
		 * controller.saveInstancesToFile(controller.getDataSet(), "test1");
		 */

		Instances i = DataSource.read("rockneki.arff");
		i.setClassIndex(i.numAttributes() - 1);

		// controller.getGanreClassifier().getClassifier().buildClassifier(dataSet);

		Evaluation e = new Evaluation(dataSet);

		// K-Nearest Neighbors
		/*IBk ibk = new IBk();
		ibk.setKNN(2);

		RandomForest rf = new RandomForest();
		rf.setNumTrees(10);
		rf.setNumFeatures(0);

		// decision tree
		J48 j48 = new J48();
		j48.setConfidenceFactor((float) 0.25);
		j48.setMinNumObj(2);

		// Support Vector Machines
		SMO smo = new SMO();
		RBFKernel rbf = new RBFKernel();
		rbf.setGamma(0.01);
		PolyKernel poly = new PolyKernel();

		smo.setC(1.0);
		//smo.setKernel(rbf);

		NaiveBayes naiveBayes = new NaiveBayes();

		// inicijalizujemo filter za diskretizaciju
		Discretize filterZaDiskretizaciju = new Discretize();

		// uzimamo u obzir sve atribute, mogu da se navedu i redni brojevi
		filterZaDiskretizaciju.setAttributeIndices("first-last");

		// postavljamo broj opsega
		filterZaDiskretizaciju.setBins(10);

		// true ako se koristi diskretizacija sa jednakim pojavljivanjima u
		// opsezima, false
		// ako se koristi Diskretizacija sa jednakim �irinama opsega
		filterZaDiskretizaciju.setUseEqualFrequency(true);

		// prosledjujemo instance za treniranje
		filterZaDiskretizaciju.setInputFormat(dataSet);

		// testiramo filter i dobijamo nove podatke kao rezultat
		// Instances outputTrain = Filter.useFilter(dataSet,
		// filterZaDiskretizaciju);
		// Instances instances = Filter.useFilter(i, filterZaDiskretizaciju);
		
		naiveBayes.setUseSupervisedDiscretization(true);
		
		j48.buildClassifier(dataSet);
		
		SerializationHelper.write("./classifiers/j48.dat",
				j48);
		
		smo.buildClassifier(dataSet);
		SerializationHelper.write("./classifiers/smo.dat",
				smo);
			
		
		

		
		
		  
		/*  e.crossValidateModel((FilteredClassifier)SerializationHelper
					.read("./classifiers/naiveBayes.dat"), i, 10,
					new Random(1));*/

		
	/*	FilteredClassifier filteredClassifier = new FilteredClassifier();
		Discretize discretize = new Discretize();
		discretize.setAttributeIndices("first-last");
		discretize.setUseEqualFrequency(true);
		filteredClassifier.setFilter(discretize);
		filteredClassifier.setClassifier(new NaiveBayes());
		filteredClassifier.buildClassifier(dataSet);
		
		SerializationHelper.write("./classifiers/nbGZAN.dat",
				filteredClassifier);*/
		
		
		
		
		
		e.evaluateModel((FilteredClassifier)SerializationHelper
				.read("./classifiers/naiveBayes.dat"), i);
			System.out.println(e.toMatrixString());
			System.out.println(e.toSummaryString());
		  
		 // SerializationHelper.write("./classifiers/naiveBayes.dat",fk);
		 /*try {
			e.evaluateModel((RandomForest)SerializationHelper
						.read("./classifiers/rf.dat"),i);
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
			System.err.println("Classifier loading error...!!!");
		}
		  System.out.println(e.toMatrixString());
		  System.out.println(e.toSummaryString());*/

		

	}

}
