/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package is.fon.rs.main;

import is.fon.rs.restservice.Segment;
import is.fon.rs.restservice.ServiceExecutor;
import is.fon.rs.restservice.Track;
import is.fon.rs.restservice.TrackAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Params;
import com.echonest.api.v4.Song;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;

/*
 * @author branko
 */

public class Main {
	static Instances dataSet;
	static FastVector attributes;
	
	

	public static Instance createInstance(double niz[], String ganre) {
		
		Instance i1 = new Instance(13);
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
		i1.setValue((Attribute) attributes.elementAt(12), ganre);
		

		/*
		 * // drugi nacin i1.setValue(dataSet.attribute(0), v1);
		 * i1.setValue(dataSet.attribute(1), v2);
		 * i1.setValue(dataSet.attribute(2), v3);
		 * i1.setValue(dataSet.attribute(3), v4);
		 * i1.setValue(dataSet.attribute(4), v5);
		 */
		return i1;
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
	
	public static Instance createInstance(double niz[], double niz1[],double tempo,double densability,
			double duration,double energy,double livness,double loudness,double speechiness,  String ganre) {
		
		// kreiramo jednu instancu
		Instance i1 = new Instance(32);
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
		i1.setValue((Attribute) attributes.elementAt(24), tempo);
		i1.setValue((Attribute) attributes.elementAt(25), densability);
		i1.setValue((Attribute) attributes.elementAt(26), duration);
		i1.setValue((Attribute) attributes.elementAt(27), energy);
		i1.setValue((Attribute) attributes.elementAt(28), livness);
		i1.setValue((Attribute) attributes.elementAt(29), loudness);
		i1.setValue((Attribute) attributes.elementAt(30), speechiness);
		i1.setValue((Attribute) attributes.elementAt(31), ganre);

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
		ServiceExecutor en = new ServiceExecutor("XAP34IMQD2FGN1SCO");

		String path = "";
		File file = new File(path);
		

		if (args.length > 2) {
			path = args[1];
		}

		if (file.exists()) {
			//System.err.println("Can't find " + path);
		} else {

			try {
				Track track;
				// track.waitForAnalysis(30000);
				// if (track.getStatus() == Track.AnalysisStatus.COMPLETE) {
				if (true) {

					/*
					 * System.out.println("Tempo: " + track.getTempo());
					 * System.out.println("Danceability: " +
					 * track.getDanceability());
					 * System.out.println("Speechiness: " +
					 * track.getSpeechiness()); System.out.println("Liveness: "
					 * + track.getLiveness()); System.out.println("Energy: " +
					 * track.getEnergy()); System.out.println("Loudness: " +
					 * track.getLoudness()); System.out.println();
					 * System.out.println("Beat start times:");
					 */

					TrackAnalysis analysis;

					List<Segment> segments;

					double pitches[][];
					double timbres[][];

					/*
					 * for (int i = 0; i < segments.size(); i++) {
					 * 
					 * pitches[i]= segments.get(i).getPitches(); }
					 */
					/*FastVector ganreNominalValues = new FastVector();
					// adding nominal values
					ganreNominalValues.addElement("classical");
					ganreNominalValues.addElement("jazz");
					ganreNominalValues.addElement("metal");
					ganreNominalValues.addElement("pop");
					ganreNominalValues.addElement("disco");
					ganreNominalValues.addElement("rock");
					ganreNominalValues.addElement("reggae");*/
					
					FastVector ganreNominalValues = new FastVector();
					ganreNominalValues.addElement("techno");
					ganreNominalValues.addElement("rock");
					ganreNominalValues.addElement("rap");
					ganreNominalValues.addElement("jazz");
					ganreNominalValues.addElement("classical");
					ganreNominalValues.addElement("metal");

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
					/*Attribute temp = new Attribute("tempo");
					Attribute dens = new Attribute("densability");
					Attribute dur = new Attribute("duration");
					Attribute energ = new Attribute("energy");
					Attribute liv = new Attribute("livness");
					Attribute loud = new Attribute("loudness");
					Attribute spech = new Attribute("speechiness");*/

					attributes = new FastVector();
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
					/*attributes.addElement(temp);
					attributes.addElement(dens);
					attributes.addElement(dur);
					attributes.addElement(energ);
					attributes.addElement(liv);
					attributes.addElement(loud);
					attributes.addElement(spech);*/
					attributes.addElement(ganre);
					dataSet = new Instances("dataSet", attributes, 0);
					
					
					
			/*	for (int i = 0; i < 100; i++) {

						System.out.println(i);

						path = "./genre_data/data/reggae/reggae.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/reggae/reggae.000" + i + ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}
						
						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {
							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"reggae"));
						}
					}
					for (int i = 0; i < 100; i++) {

						System.out.println(i);

						path = "./genre_data/data/disco/disco.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/disco/disco.000" + i + ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}
						
						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {
							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"disco"));
						}
					}
		

					for (int i = 0; i < 100; i++) {

						System.out.println(i);

						path = "./genre_data/data/pop/pop.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/pop/pop.000" + i + ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}
						
						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {
							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"pop"));
						}
					}
					

					for (int i = 0; i < 100; i++) {

						System.out.println(i);
						path = "./genre_data/data/jazz/jazz.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/jazz/jazz.000" + i
									+ ".au";
						}
						file = new File(path);
					
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}

						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {

							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"jazz"));
						}
					}
					

					for (int i = 0; i < 100; i++) {
						System.out.println(i);
						path = "./genre_data/data/classical/classical.0000" + i
								+ ".au";
						if (i >= 10) {
							path = "./genre_data/data/classical/classical.000"
									+ i + ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}

						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {

							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"classical"));
						}
					}
					
				
					for (int i = 0; i < 100; i++) {
						System.out.println(i);
						path = "./genre_data/data/metal/metal.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/metal/metal.000" + i
									+ ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}

						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {

							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"metal"));
						}

					}
				
					for (int i = 0; i < 100; i++) {
						System.out.println(i);
						path = "./genre_data/data/rock/rock.0000" + i + ".au";
						if (i >= 10) {
							path = "./genre_data/data/rock/rock.000" + i
									+ ".au";
						}
						file = new File(path);
						double tempo = 0;
						double densability= 0 ;
						double duration = 0;
						double energy = 0;
						double livness= 0;
						double loudness =0;
						double speechiness = 0 ;
						
						try {
							track = en.uploadTrack(file);
							tempo = track.getTempo();
							densability = track.getDanceability();
							 duration = track.getDuration();
							 energy = track.getEnergy();
							 livness = track.getLiveness();
							 loudness = track.getLoudness();
							 speechiness = track.getSpeechiness();
							

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						} catch (Exception e) {
							System.out.println("************************************************************");
							Thread.sleep(60000);
							track = en.uploadTrack(file);

							analysis = track.getAnalysis();

							segments = analysis.getSegments();
						}

						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
						for (int j = 0; j < segments.size(); j++) {

							timbres[j] = segments.get(j).getTimbre();
							pitches[j] = segments.get(j).getPitches();
							dataSet.add(createInstance(timbres[j], pitches[j],"rock"));
						}

					}

					
					if (dataSet.classIndex() == -1) {
						dataSet.setClassIndex(dataSet.numAttributes() - 1);
					}
					ArffSaver saver = new ArffSaver();
					saver.setInstances(dataSet);
					saver.setFile(new File("gzan.arff"));
					saver.setDestination(new File("gzan.arff"));
					saver.writeBatch();
				/*LBR lbk = new LBR();
					lbk.buildClassifier(dataSet);
					J48 klasifikator = new J48();
					klasifikator.buildClassifier(dataSet);*/

					//Evaluation eval = new Evaluation(dataSet);

					dataSet= new Instances("dataSetTest", attributes, 0);
					path = "./genre_data/data/rock/rock.00060.au";
					file = new File(path);
					
					double tempo = 0;
					double densability= 0 ;
					double duration = 0;
					double energy = 0;
					double livness= 0;
					double loudness =0;
					double speechiness = 0 ;
					
					
						track = en.uploadTrack(file);
						System.out.println(track.getTitle());
						tempo = track.getTempo();
						
						densability = track.getDanceability();
						 duration = track.getDuration();
						 energy = track.getEnergy();
						 livness = track.getLiveness();
						 loudness = track.getLoudness();
						 speechiness = track.getSpeechiness();
						

						analysis = track.getAnalysis();
	

						segments = analysis.getSegments();
						pitches = new double[segments.size()][12];
						timbres = new double[segments.size()][12];
					
					for (int j = 0; j < segments.size(); j++) {

						timbres[j] = segments.get(j).getTimbre();
						pitches[j] = segments.get(j).getPitches();
						dataSet.add(createInstance(timbres[j], pitches[j],"rock"));
					}

					if(dataSet.classIndex() == -1) {
						dataSet.setClassIndex(dataSet.numAttributes() - 1);
					}

			/*	eval.evaluateModel(klasifikator, dataSet);

					System.out.println(eval.toSummaryString());

					// Ispis matrice konfuzije
					System.out.println(eval.toMatrixString());*/

					ArffSaver saver = new ArffSaver();
					saver.setInstances(dataSet);
					saver.setFile(new File("./rockneki.arff"));
					saver.setDestination(new File("./rockneki.arff")); // **not**
																	// necessary
																	// in 3.5.4
																	// and later
					saver.writeBatch();

					/*
					 * for (TimedEvent beat : analysis.getBeats()) {
					 * System.out.println("beat " + beat.getStart()); }
					 */
				} else {
					System.err.println("Trouble analysing track "
							+ track.getStatus());
				}
			} catch (IOException e) {
				System.err.println("Trouble uploading file");
			}

		}
	}
}
