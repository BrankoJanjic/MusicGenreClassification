package is.fon.rs.restservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.echonest.api.v4.EchoNestException;
import com.echonest.api.v4.Track.AnalysisStatus;

@SuppressWarnings("rawtypes")
public class Track extends ENItem {

    private final static String PATH = "track";
    private final static String TYPE = "track";

    /**
     * The status of an analysis
     */
    public enum AnalysisStatus {

        /**
         * track is unknown
         */
        UNKNOWN,
        /**
         * track analysis is underway
         */
        PENDING,
        /**
         * track analysis is complete
         */
        COMPLETE,
        /**
         * track analysis is unavailable
         */
        UNAVAILABLE,
        /**
         * track analysis failed
         */
        ERROR
    };
    private TrackAnalysis analysis = null;
    private AnalysisStatus currentStatus = AnalysisStatus.UNKNOWN;

    Track(ServiceExecutor en, String idOrMD5, String type) throws ServiceException {
        super(en, TYPE, PATH, idOrMD5, type);
    }

	Track(ServiceExecutor en, Map data) throws ServiceException {
        super(en, TYPE, PATH, data);
    }

    /**
     * Creates a track given an ID
     *
     * @param en the EchoNest API
     * @param id the ID of the track
     * @return the track
     * @throws ServiceException
     */
 
    @SuppressWarnings("unchecked")
	static Track createTrack(ServiceExecutor en, String id) throws ServiceException {
        Map data = new HashMap();
        data.put("id", id);
        Track track = new Track(en, data);
        return track;
    }
    

   
	static Track createTrackFromSong(ServiceExecutor en, Map data) throws  ServiceException {
        Track track = new Track(en, data);
        track.currentStatus = AnalysisStatus.COMPLETE;
        return track;
    }

    public String getAnalysisURL() throws ServiceException {
        fetchBucket("audio_summary");
        String url = getString("audio_summary.analysis_url");
        return url;
    }

    /**
     * Gets the analysis status for the track
     *
     * @return the analysis status
     */
    public AnalysisStatus getStatus() throws ServiceException {

        if (currentStatus != AnalysisStatus.COMPLETE
                && currentStatus != AnalysisStatus.ERROR) {
            refresh();
            String status = getString("status");
            // if we have no status, this is a pre-analyzed track
            if (status == null) {
                status = "complete";
            }
            status = status.toLowerCase();
            for (AnalysisStatus as : AnalysisStatus.values()) {
                if (as.name().toLowerCase().equals(status)) {
                    currentStatus = as;
                    break;
                }
            }
        }
        // System.out.println("GS " + currentStatus);
        return currentStatus;
    }


    public AnalysisStatus waitForAnalysis(long timeoutMillis)
            throws ServiceException {
        long startTime = System.currentTimeMillis();
        long elapsed = 0;
        AnalysisStatus status = AnalysisStatus.UNKNOWN;
        do {
            status = getStatus();
            elapsed = System.currentTimeMillis() - startTime;
        } while (status == AnalysisStatus.PENDING && elapsed < timeoutMillis);
        return status;
    }

	public TrackAnalysis getAnalysis() throws ServiceException {
        fetchBucket("audio_summary");
        try {
            if (analysis == null) {
                Map analysisMap = HTTPController.fetchURLAsJSON(getAnalysisURL());
                analysis = new TrackAnalysis(analysisMap);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return analysis;
    }

    /**
     * Gets the key for the track
     *
     * @return
     * @throws ServiceException
     */
    public int getKey() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.key");
    }

    /**
     * Gets the tempo for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getTempo() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.tempo");
    }

    /**
     * Gets the mode for the track
     *
     * @return
     * @throws ServiceException
     */
    public int getMode() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.mode");
    }

    /**
     * Gets the time signature for the track
     *
     * @return
     * @throws ServiceException
     */
    public int getTimeSignature() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.time_signature");
    }

    /**
     * Gets the duration for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getDuration() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.duration");
    }

    /**
     * Gets the loudness for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getLoudness() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.loudness");
    }

    /**
     * Gets the energy for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getEnergy() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.energy");
    }

    /**
     * Gets the danceability for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getDanceability() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.danceability");
    }

    /**
     * Gets the speechiness for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getSpeechiness() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.speechiness");
    }

    /**
     * Gets the liveness for the track
     *
     * @return
     * @throws ServiceException
     */
    public double getLiveness() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.liveness");
    }
    
    public String getTitle() throws ServiceException {
        return getTopLevelItem("title");
    }
    
    private String getTopLevelItem(String itemName) throws  ServiceException {
        if (getStatus() == null) {
            refresh();
        }
        return getString(itemName);
    }
    





}
