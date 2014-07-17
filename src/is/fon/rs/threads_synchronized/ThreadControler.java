package is.fon.rs.threads_synchronized;

import java.io.File;
import java.util.List;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Song;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

public class ThreadControler {
	
	 Instances dataSet;
	 FastVector attributes;
	 String fields;
	 ThreadGanre threads[] ;
	 public static int brojac;
	
	
	public synchronized void add() {
		brojac++;
	}
	
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	public ThreadControler(String fields) {
		
			this.fields=fields;
			FastVector ganreNominalValues = new FastVector();
			ganreNominalValues.addElement("techno");
			ganreNominalValues.addElement("rock");
			ganreNominalValues.addElement("rap");
			ganreNominalValues.addElement("jazz");
			ganreNominalValues.addElement("classical");
			ganreNominalValues.addElement("metal");

			Attribute ganre = new Attribute("ganre", ganreNominalValues);
			attributes = new FastVector();
			if (fields=="mfccs") {
				for (int i = 1; i <= 12; i++) {
					attributes.addElement(new Attribute("mfcc" + i));
				}
				attributes.addElement(ganre);
			}
			
			if (fields=="pitchs"){
				for (int i = 1; i <= 12; i++) {
					attributes.addElement(new Attribute("pitch" + i));
				}
				attributes.addElement(ganre);
			}
			
			if (fields=="both") {
				for (int i = 1; i <= 24; i++) {
					if (i > 12) {
						attributes.addElement(new Attribute("pitch" + (i - 12)));
					} else {
						attributes.addElement(new Attribute("mfcc" + i));
					}

				}
				attributes.addElement(ganre);
			}
			dataSet = new Instances("dataSet", attributes, 0);
			
	}
	public Instance createInstance(double values[], String ganre) {
		
		if (dataSet == null) {
			return null;
		}
		Instance instance = new Instance(values.length + 1);
		instance.setDataset(dataSet);

		for (int i = 0; i < values.length; i++) {
			instance.setValue((Attribute) attributes.elementAt(i), values[i]);
		}
		instance.setValue((Attribute) attributes.elementAt(values.length),
				ganre);

		return instance;
	}
	public Instance createInstance(double mfccs[], double pitchs[],
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
	public Instances getDataSet() {
		return dataSet;
	}
	public void setDataSet(Instances dataSet) {
		this.dataSet = dataSet;
	}
	public FastVector getAttributes() {
		return attributes;
	}
	public void setAttributes(FastVector attributes) {
		this.attributes = attributes;
	}
	
	public synchronized void addValues (double values[], String ganre){
		
		if (values==null) {
			return;
		} 
		dataSet.add(createInstance(values, ganre));
		notifyAll();
	}
	
public synchronized void addValues (double mfccs[], double pitchs[],String ganre){
		
		if (mfccs==null || pitchs==null || mfccs.length!=12 || pitchs.length!=12) {
			return;
		} 
		dataSet.add(createInstance(mfccs,pitchs,ganre));
		notifyAll();
	}

public void saveToFile (String dataSetName){
	try {
		ArffSaver saver = new ArffSaver();
		saver.setInstances(dataSet);
		saver.setFile(new File(dataSetName+".arff"));
		saver.setDestination(new File(dataSetName+".arff"));
		saver.writeBatch();
	} catch (Exception e) {
		System.err.println("Can not save instances to file !");
	}
}

public void start() {
	
	threads = new ThreadGanre[6];
	
	for (int i = 0; i < threads.length; i++) {
		if (i==0) {
			threads[i]=new ThreadGanre("techno",i,"mfccs",this);
			new Thread(threads[i]).start();
		}
		
		if (i==1) {
			threads[i]=new ThreadGanre("rock",i,"mfccs",this);
			new Thread(threads[i]).start();
		}
		
		if (i==2) {
			threads[i]=new ThreadGanre("rap",i,"mfccs",this);
			new Thread(threads[i]).start();
		}
		
		if (i==3) {
			threads[i]=new ThreadGanre("jazz",i,"mfccs",this);
			new Thread(threads[i]).start();;
		}
		
		if (i==4) {
			threads[i]=new ThreadGanre("classical",i,"mfccs",this);
			new Thread(threads[i]).start();
		}
		
		if (i==5) {
			threads[i]=new ThreadGanre("metal",i,"mfccs",this);
			new Thread(threads[i]).start();
		}
		
	}

	
}




public ThreadGanre[] getThreads() {
	return threads;
}


public void setThreads(ThreadGanre[] threads) {
	this.threads = threads;
}

}
