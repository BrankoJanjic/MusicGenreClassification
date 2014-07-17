package is.fon.rs.threads_synchronized;

import is.fon.rs.restservice.DynamicPlaylistSession;
import is.fon.rs.restservice.Playlist;
import is.fon.rs.restservice.PlaylistParams;
import is.fon.rs.restservice.Segment;
import is.fon.rs.restservice.ServiceException;
import is.fon.rs.restservice.ServiceExecutor;
import is.fon.rs.restservice.Song;
import is.fon.rs.restservice.TrackAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ThreadGanre implements Runnable {

	String ganre;
	int id;

	private ThreadControler thControler;

	public ThreadGanre(String ganre, int id, String fields,
			ThreadControler controler) {

		this.ganre = ganre;
		this.id = id;
		thControler = controler;
	}

	public void run() {
		double pitches[][];
		double mfcc[][];
		TrackAnalysis analysis;

		File file = new File("./dataSetSongs/" + ganre + "15.txt");

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		BufferedWriter bWriter;
		try {
			 bWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e3) {
			
			e3.printStackTrace();
			return;
		}

		List<Segment> segments;
		ServiceExecutor en = new ServiceExecutor("XAP34IMQD2FGN1SCO");
		Playlist playlist = null;
		ThreadDataCreator params = new ThreadDataCreator();
		params.setType(PlaylistParams.PlaylistType.GENRE_RADIO);
		params.addGenre(ganre);
		params.includeAudioSummary();
		DynamicPlaylistSession session = null;
		try {
			session = en.createDynamicPlaylist(params);
		} catch (ServiceException e2) {
			
			e2.printStackTrace();
		}

		int i = 0;
		while (i < 20) {
		
				
				
			
			try {
				session = en.createDynamicPlaylist(params);
				playlist = session.next();

				for (Song song : playlist.getSongs()) {
					try {
						bWriter.write(song.getArtistName()+" "+ song.getReleaseName()+"\n");
						
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					analysis = song.getAnalysis();
					segments = analysis.getSegments();
					pitches = new double[segments.size()][12];
					mfcc = new double[segments.size()][12];
					for (int j = 0; j < segments.size(); j++) {
						mfcc[j] = segments.get(j).getTimbre();
						pitches[j] = segments.get(j).getPitches();
						if (thControler.getFields() == "mfccs") {
							thControler.addValues(mfcc[j], ganre);
						}

						if (thControler.getFields() == "pitchs") {
							thControler.addValues(pitches[j], ganre);
						}

						if (thControler.getFields() == "both") {
							thControler.addValues(mfcc[j], pitches[j], ganre);
						}
					}

					System.out.println("Thread: " + id + "->" + i + "# "
							+ song.toString());

				}

				i++;
			} catch (ServiceException e) {
				try {
					System.out.println("Sleaping" + id);
					Thread.sleep(60000);
					System.out.println("Strarting" + id);

				} catch (InterruptedException e1) {
					e.printStackTrace();
				}

			}
		}

		thControler.saveToFile("smoDataSmallSet");
		try {
			bWriter.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		for (ThreadGanre thGanre : thControler.getThreads()) {
			if (thGanre == this) {
				thGanre = null;
			}
		}

	}

}
