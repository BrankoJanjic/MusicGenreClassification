package is.fon.rs.restservice;

import is.fon.rs.restservice.Track.AnalysisStatus;
import is.fon.rs.util.Params;
import is.fon.rs.util.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ServiceExecutor {

    private HTTPController cmd = new HTTPController("EchoNestAPI");
    public HTTPController getCmd() {
		return cmd;
	}

	private Params stdParams = new Params();

    public enum TermType {

        ANY, STYLE, MOOD
    };

    /**
     * Possible types of artist descriptions
     */
    public enum DescriptionType {

        general {
            public String toString() {
                return "description";
            }
        }, style, mood
    }

    /**
     * Creates a new EchoNestAPI. This method will attempt to get the Echo Nest
     * API Key from the ECHO_NEST_API_KEY system property or environment
     * variable
     *
     * @throws ServiceException
     */
    public ServiceExecutor() throws ServiceException {
        this(getApiKey());
        setMinCommandTime(-1);
    }

    /**
     * Creates a new EchoNestAPI with the given API key
     *
     * @param apiKey the developer api key
     */
    public ServiceExecutor(String apiKey) {
        stdParams.add("api_key", apiKey);
        cmd.setStandardParams(stdParams);
    }

    

    private static String getApiKey() throws ServiceException {
        String key = System.getProperty("ECHO_NEST_API_KEY");
        if (key == null) {
            key = System.getenv("ECHO_NEST_API_KEY");
        }

        if (key == null) {
            System.out.println("No API KEY set");
            throw new ServiceException(ServiceException.ERR_NO_KEY,
                    "No API Key");
        }
        return key;
    }

    /**
     * Sets the host to use for the API.
     *
     * @param hostName
     */
    public void setHostName(String hostName) {
        cmd.setHost(hostName);
    }

    /**
     * Sets the minimum time between Echo Nest commands
     *
     * @param minTime
     */
    public final void setMinCommandTime(int minTime) {
        cmd.setMinCommandTime(minTime);
    }

    /**
     * Gets the minimum time between commands
     *
     * @return
     */
    public int getMinCommandTime() {
        return cmd.getMinCommandTime();
    }
    
    
	public DynamicPlaylistSession createDynamicPlaylist(Params p) throws ServiceException {
        Map results = cmd.sendCommand("playlist/dynamic/create", p);
        Map response = (Map) results.get("response");
        String sessionID = (String) response.get("session_id");
        return new DynamicPlaylistSession(this, sessionID);
    }

    /**
     * Enables / disables tracing of sent commands
     *
     * @param traceSends
     */
    public void setTraceSends(boolean traceSends) {
        cmd.setTraceSends(traceSends);
    }

    /**
     * Enables / disables traceing of responses
     *
     * @param traceRecvs
     */
    public void setTraceRecvs(boolean traceRecvs) {
        cmd.setTraceRecvs(traceRecvs);
    }

    /**
     * Shows performance and error statistics for the API
     */
    public void showStats() {
        cmd.showStats();
    }



	public Track uploadTrack(URL trackUrl, boolean wait)
            throws ServiceException {

        Params p = new Params();

        p.add("url", trackUrl.toExternalForm());
        p.add("wait", wait ? "true" : "false");
        p.add("upload", (String) null);
        if (wait) {
            p.add("bucket", "audio_summary");
        }
        int tmo = cmd.getTimeout();
        // for track uploads set the timeout to 5 mins
        cmd.setTimeout(300000);
        try {
            Map results = cmd.sendCommand("track/upload", p, true);
            Map response = (Map) results.get("response");
            Map trackData = (Map) response.get("track");
            Track track = new Track(this, trackData);
            return track;
        } finally {
            cmd.setTimeout(tmo);
        }
    }

    /**
     * Upload a track
     *
     * @param trackFile the file to upload
     * @param wait if true, wait for the analysis
     * @return the ID of the track
     * @throws com.echonest.api.ServiceException.artist.EchoNestException
     */

	public Track uploadTrack(File audioFile)
            throws ServiceException, IOException {

        Track track = getKnownTrack(audioFile);
        if (track != null) {
            return track;
        }

        Params p = new Params();

        p.add("filetype", getFileType(audioFile));
        int tmo = cmd.getTimeout();
        // for track uploads set the timeout to 5 mins
        cmd.setTimeout(300000);
        try {
            Map results = cmd.sendCommand("track/upload", p, true, audioFile);
            Map response = (Map) results.get("response");
            Map trackData = (Map) response.get("track");
            track = new Track(this, trackData);
            return track;
        } finally {
            cmd.setTimeout(tmo);
        }
    }

    /**
     * Determines whether or not the track is known by the echo nest
     *
     * @param md5 of the track
     * @return true if the track is known
     */
    public Track getKnownTrack(String md5) throws ServiceException {
        try {
            Track track = newTrackByMD5(md5);
            Track.AnalysisStatus status = track.getStatus();
            if (status != AnalysisStatus.UNKNOWN
                    && status != AnalysisStatus.UNAVAILABLE) {
                return track;
            } else {
                return null;
            }
        } catch (ServiceException e) { // Bug, clean this up
            return null;
        }
    }

    /**
     * Determines whether or not the track is known by the echo nest
     *
     * @param file the file to test
     * @return true if the track is known
     * @throws IOException
     */
    public Track getKnownTrack(File file) throws IOException, ServiceException {
        return getKnownTrack(Utilities.md5(file));
    }

    public String getFileType(File file) {
        int dot = file.getName().lastIndexOf('.');
        if (dot >= 0 && dot < file.getName().length() - 1) {
            return file.getName().substring(dot + 1).toLowerCase();
        } else {
            return "mp3";
        }
    }

    /**
     * Creates a track from a track ID
     *
     * @param id the ID or MD5 of the track
     * @return a new track
     */
    public Track newTrackByID(String id) throws ServiceException {
        return Track.createTrack(this, id);
    }

    /**
     * Creates a track from a track ID
     *
     * @param id the ID or MD5 of the track
     * @return a new track
     */
    public Track newTrackByMD5(String md5) throws ServiceException {
        return new Track(this, md5, "md5");
    }






}
