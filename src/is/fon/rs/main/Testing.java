package is.fon.rs.main;

import java.io.IOException;

import weka.classifiers.trees.J48;
import is.fon.rs.classification.ClassificationController;
import is.fon.rs.classification.GanreClassifier;
import is.fon.rs.restservice.ServiceException;

public class Testing {

	public static void main(String[] args) throws InterruptedException, ServiceException, IOException {
		
		/*new Thread(new ThreadGanre("techno",1)).start();
		new Thread(new ThreadGanre("rock",2)).start();
		new Thread(new ThreadGanre("pop",3)).start();
		new Thread(new ThreadGanre("rap",4)).start();
		new Thread(new ThreadGanre("jazz",5)).start();
		new Thread(new ThreadGanre("classical",6)).start();
		new Thread(new ThreadGanre("metal",7)).start();
		new Thread(new ThreadGanre("punk",8)).start();*/
		
		String nomVal[] = {"classical","disco","jazz","metal","pop","reggae","rock"};
		ClassificationController classificationController = new ClassificationController(new GanreClassifier(new J48()));
		classificationController.createInstancesMFCCAndPitchModel(nomVal);
		classificationController.addGanresToMFCCAndPitchDataSet("./genre_data/data/");
		classificationController.saveInstancesToFile("FullSet");

	}

}
