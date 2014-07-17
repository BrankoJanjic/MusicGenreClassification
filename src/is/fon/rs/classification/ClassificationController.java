package is.fon.rs.classification;

import is.fon.rs.restservice.Segment;
import is.fon.rs.restservice.ServiceException;
import is.fon.rs.restservice.ServiceExecutor;
import is.fon.rs.restservice.Track;
import is.fon.rs.restservice.TrackAnalysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassificationController {

	private Instances dataSet;
	private FastVector attributes;
	ServiceExecutor serviceExecutor;

	private GanreClassifier ganreClassifier;

	public GanreClassifier getGanreClassifier() {
		return ganreClassifier;
	}

	public void setGanreClassifier(GanreClassifier ganreClassifier) {
		this.ganreClassifier = ganreClassifier;
	}

	public ClassificationController(GanreClassifier ganreClassifier) {

		if (ganreClassifier != null) {
			this.ganreClassifier = ganreClassifier;
		} else {
			throw new NullPointerException();
		}
		serviceExecutor = new ServiceExecutor("XAP34IMQD2FGN1SCO");
	}

	public ClassificationController(GanreClassifier ganreClassifier,
			Instances dataSet) {

		if (ganreClassifier != null) {
			this.ganreClassifier = ganreClassifier;

			if (dataSet.classIndex() == -1) {
				dataSet.setClassIndex(dataSet.numAttributes() - 1);
			}

			this.dataSet = dataSet;

		}
	}

	public Instances getDataSet() {
		return dataSet;
	}

	public void setDataSet(Instances dataSet) {

		this.dataSet = dataSet;
		this.dataSet.setClassIndex(dataSet.numAttributes() - 1);

	}

	public void setDataSetAndBuildClassifier(Instances dataSet) {
		this.dataSet = dataSet;
		try {
			ganreClassifier.getClassifier().buildClassifier(dataSet);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public FastVector getAttributes() {
		return attributes;
	}

	public void setAttributes(FastVector attributes) {
		this.attributes = attributes;
	}

	public static Instances readDataSetFromFile(String filepath)
			throws FileNotFoundException {

		File f = new File(filepath);

		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		try {
			return DataSource.read(filepath);
		} catch (Exception e) {
			System.err.println("Can not create data set !");
			e.printStackTrace();
		}

		return null;

	}

	public static Instances readDataSetFromFile(File f)
			throws FileNotFoundException {

		if (!f.exists()) {
			throw new FileNotFoundException();
		}
		try {
			return readDataSetFromFile(f.getAbsolutePath());
		} catch (Exception e) {
			System.err.println("Can not create data set !");
			e.printStackTrace();
		}

		return null;

	}

	public Evaluation evaluateClassifier(Instances instances) throws Exception {

		if (instances.classIndex() == -1) {
			instances.setClassIndex(instances.numAttributes() - 1);
		}
		if ((ganreClassifier == null || instances == null)
				|| (ganreClassifier == null && instances == null)) {
			throw new NullPointerException("Null values!");
		}
		Evaluation evaluation = new Evaluation(instances);
		evaluation.evaluateModel(ganreClassifier.getClassifier(), instances);

		System.out.println(evaluation.toSummaryString());
		System.out.println(evaluation.toMatrixString());
		System.out.println(evaluation.toClassDetailsString());
		return evaluation;
	}

	public Evaluation evaluateClassifier() throws Exception {

		if ((ganreClassifier == null || dataSet == null)
				|| (ganreClassifier == null && dataSet == null)) {
			throw new NullPointerException("Not set: dataset or classifier !!");
		}
		Evaluation evaluation = new Evaluation(dataSet);
		try {
			evaluation.evaluateModel(ganreClassifier.getClassifier(), dataSet);
		} catch (Exception e) {
			throw new Exception("Classifier building error !");

		}

		System.out.println(evaluation.toSummaryString());
		System.out.println(evaluation.toMatrixString());
		System.out.println(evaluation.toClassDetailsString());
		return evaluation;
	}

	public Instances createInstancesMFCCAndPitchModel(String[] nominalValues) {

		FastVector ganreNominalValues = createGanresNominalValues(nominalValues);

		Attribute ganre = new Attribute("ganre", ganreNominalValues);
		attributes = new FastVector();
		for (int i = 1; i <= 24; i++) {
			if (i > 12) {
				attributes.addElement(new Attribute("pitch" + (i - 12)));
			} else {
				attributes.addElement(new Attribute("mfcc" + i));
			}

		}
		attributes.addElement(ganre);
		dataSet = new Instances("dataSet", attributes, 0);

		return dataSet;

	}

	private FastVector createGanresNominalValues(String[] nominalValues) {

		FastVector ganreNominalValues = new FastVector();

		if (nominalValues != null && nominalValues.length > 0) {

			for (String nomValue : nominalValues) {
				ganreNominalValues.addElement(nomValue);
			}
		}

		return ganreNominalValues;
	}

	public Instances createInstancesMFCCModel(String[] nominalValues) {

		FastVector ganreNominalValues = createGanresNominalValues(nominalValues);

		Attribute ganre = new Attribute("ganre", ganreNominalValues);
		attributes = new FastVector();
		for (int i = 1; i <= 12; i++) {
			attributes.addElement(new Attribute("mfcc" + i));
		}
		attributes.addElement(ganre);

		dataSet = new Instances("dataSet", attributes, 0);
		return dataSet;

	}

	public Instances createInstancesPitchModel(String[] nominalValues) {

		FastVector ganreNominalValues = createGanresNominalValues(nominalValues);

		Attribute ganre = new Attribute("ganre", ganreNominalValues);
		attributes = new FastVector();
		for (int i = 1; i <= 12; i++) {
			attributes.addElement(new Attribute("pitch" + i));
		}
		attributes.addElement(ganre);
		dataSet = new Instances("dataSet", attributes, 0);

		return dataSet;

	}

	public Instance createInstance(double values[], String ganre) {

		
		Instance instance = new Instance(values.length + 1);
		instance.setDataset(dataSet);
		for (int i = 0; i < values.length; i++) {
			instance.setValue((Attribute) attributes.elementAt(i), values[i]);
		}
		instance.setValue((Attribute) attributes.elementAt(values.length),
				ganre);

		return instance;
	}

	public Instance createInstanceMFCCAndPitch(double mfccs[], double pitchs[],
			String ganre) {

		if (mfccs.length != 12 || pitchs.length != 12) {
			return null;
		}

		Instance instance = new Instance(mfccs.length + (pitchs.length + 1));
		instance.setDataset(dataSet);

		for (int i = 0; i < mfccs.length; i++) {
			instance.setValue((Attribute) attributes.elementAt(i), mfccs[i]);
		}

		for (int i = 0; i < pitchs.length; i++) {
			instance.setValue((Attribute) attributes.elementAt(i + 12),
					pitchs[i]);
		}

		instance.setValue(
				(Attribute) attributes.elementAt(mfccs.length + pitchs.length),
				ganre);
		return instance;
	}

	public void addSongToMFCCAndPitchDataSet(File f, String ganre)
			throws InterruptedException, ServiceException, IOException {

		f = new File(f.getPath());

		Track track;
		TrackAnalysis analysis;
		List<Segment> segments = null;
		if (!f.exists()) {
			System.err.println("Can't find " + f.getPath());
			return;
		}

		else {
			try {
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
				System.out.println(f.getPath());
			} catch (Exception e) {
				System.out.println("Catch :" + f.getPath());
				Thread.sleep(60000);
				System.out.println("Again :" + f.getPath());
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
			}

			double[][] mfcss = new double[segments.size()][12];
			double[][] pitchs = new double[segments.size()][12];
			for (int j = 0; j < segments.size(); j++) {
				pitchs[j] = segments.get(j).getPitches();
				mfcss[j] = segments.get(j).getTimbre();
				dataSet.add(createInstanceMFCCAndPitch(mfcss[j], pitchs[j],
						ganre));
			}

		}

	}

	public void addSongToMFCCDataSet(File f, String ganre)
			throws InterruptedException, ServiceException, IOException {

		f = new File(f.getPath());

		Track track;
		TrackAnalysis analysis;
		List<Segment> segments = null;
		if (!f.exists()) {
			System.err.println("Can't find " + f.getPath());
			return;
		}

		else {
			try {
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
				System.out.println(f.getPath());
			} catch (ServiceException e) {
				System.out.println("Catch :" + f.getPath());
				Thread.sleep(60000);
				System.out.println("Again :" + f.getPath());
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
			}

			double[][] mfcss = new double[segments.size()][12];
			for (int j = 0; j < segments.size(); j++) {
				mfcss[j] = segments.get(j).getTimbre();
				dataSet.add(createInstance(mfcss[j], ganre));
			}

		}

	}

	public void addSongToPitchCDataSet(File f, String ganre)
			throws InterruptedException, ServiceException, IOException {

		f = new File(f.getPath());

		Track track;
		TrackAnalysis analysis;
		List<Segment> segments = null;
		if (!f.exists()) {
			System.err.println("Can't find " + f.getPath());
			return;
		}

		else {
			try {
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
				System.out.println(f.getPath());
			} catch (Exception e) {
				System.out.println("Catch :" + f.getPath());
				Thread.sleep(60000);
				System.out.println("Again :" + f.getPath());
				track = serviceExecutor.uploadTrack(f);
				analysis = track.getAnalysis();
				segments = analysis.getSegments();
			}

			double[][] pitchs = new double[segments.size()][12];
			for (int j = 0; j < segments.size(); j++) {
				pitchs[j] = segments.get(j).getPitches();
				dataSet.add(createInstance(pitchs[j], ganre));
			}

		}

	}

	public void addSongsToMFCCAndPitchDataSet(File[] songsPaths, String ganre)
			throws InterruptedException, ServiceException, IOException {

		if (songsPaths == null) {
			throw new NullPointerException();
		}

		for (File song : songsPaths) {
			addSongToMFCCAndPitchDataSet(song, ganre);
		}
	}

	public void addSongsToMFCCDataSet(File[] songsPaths, String ganre)
			throws InterruptedException, ServiceException, IOException {

		if (songsPaths == null) {
			throw new NullPointerException();
		}

		for (File song : songsPaths) {
			addSongToMFCCDataSet(song, ganre);
		}
	}

	public void addSongsToPitchDataSet(File[] songsPaths, String ganre)
			throws InterruptedException, ServiceException, IOException {

		if (songsPaths == null) {
			throw new NullPointerException();
		}

		for (File song : songsPaths) {
			addSongToPitchCDataSet(song, ganre);
		}
	}

	public void addGanresToMFCCAndPitchDataSet(String directoryPath)
			throws FileNotFoundException {

		File file = new File(directoryPath);

		if (!file.exists() || !file.isDirectory()) {
			throw new FileNotFoundException();
		}
		String[] dirs = file.list();
		for (String directory : dirs) {
			file = new File(directoryPath + directory);
			if (!file.exists()) {
				System.err.println("Directory not exist!");
				return;
			}
			try {
				addSongsToMFCCAndPitchDataSet(file.listFiles(), directory);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	public void addGanresToPitchDataSet(String directoryPath)
			throws FileNotFoundException {

		File file = new File(directoryPath);

		if (!file.exists() || !file.isDirectory()) {
			throw new FileNotFoundException();
		}
		String[] dirs = file.list();
		for (String directory : dirs) {
			file = new File(directoryPath + directory);
			if (!file.exists()) {
				System.err.println("Directory not exist!");
				return;
			}
			try {
				addSongsToPitchDataSet(file.listFiles(), directory);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void addGanresToMFCCDataSet(String directoryPath)
			throws FileNotFoundException {

		File file = new File(directoryPath);

		if (!file.exists() || !file.isDirectory()) {
			throw new FileNotFoundException();
		}
		String[] dirs = file.list();
		for (String directory : dirs) {
			file = new File(directoryPath + directory);
			if (!file.exists()) {
				System.err.println("Directory not exist!");
				return;
			}
			try {
				addSongsToMFCCDataSet(file.listFiles(), directory);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

	public void saveInstancesToFile(String fileName) {

		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		try {
			saver.setFile(new File(fileName + ".arff"));
			saver.setDestination(new File(fileName + ".arff"));
			saver.writeBatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
