package is.fon.rs.restservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.echonest.api.v4.util.Commander;

@SuppressWarnings("rawtypes")
public class Song extends ENItem {

    /** 
     * Possible song types
     */
    public enum SongType {

        christmas, live, studio
    }

    /**
     * Possible states for song type
     */
    public enum SongTypeFlag {

        True, False, seed, any
    }
    
    private final static String PATH = "songs[0]";
    private Map<String, Track> trackMap = new HashMap<String, Track>();
    private TrackAnalysis analysis = null;

 
	public
    Song(ServiceExecutor en, Map map) throws ServiceException {
        super(en, "song", PATH, map);
    }

    public Song(ServiceExecutor en, String id) throws ServiceException {
        super(en, "song", PATH, id);
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public String getTitle() {
        return getString("title");
    }

    public String getArtistName() {
        return getString("artist_name");
    }

    //TBD - switch to release name when we have it
    public String getReleaseName() {
        return getTitle();
        // return getString("release_name");
    }

    public String getArtistID() {
        return getString("artist_id");
    }

    public String getAudio() {
        return getString("audio");
    }

    public String getCoverArt() {
        return getReleaseImage();
    }

    public String getReleaseImage() {
        return getString("release_image");
    }



    public double getSongHotttnesss() throws ServiceException {
        fetchBucket("song_hotttnesss");
        return getDouble("song_hotttnesss");
    }

    public double getDuration() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.duration");
    }

    public double getLoudness() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.loudness");
    }

    public double getTempo() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.tempo");
    }

    public double getEnergy() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.energy");
    }

    public double getDanceability() throws ServiceException {
        fetchBucket("audio_summary");
        return getDouble("audio_summary.danceability");
    }

    public String getAnalysisURL() throws ServiceException {
        fetchBucket("audio_summary");
        return getString("audio_summary.analysis_url");
    }

    public TrackAnalysis getAnalysis() throws ServiceException {
        try {
            if (analysis == null) {
                Map analysisMap = Commander.fetchURLAsJSON(getAnalysisURL());
                analysis = new TrackAnalysis(analysisMap);
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return analysis;
    }

    public int getTimeSignature() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.time_signature");
    }

    public int getMode() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.mode");
    }

    public int getKey() throws ServiceException {
        fetchBucket("audio_summary");
        return getInteger("audio_summary.key");
    }

    public Track getTrackOld(String idSpace) throws ServiceException {
        Track track = trackMap.get(idSpace);
        if (track == null) {
            // see if we already have the track data
            List tlist = (List) getObject("tracks");
            if (tlist == null) {
                String[] buckets = {"tracks", "id:" + idSpace};
                fetchBuckets(buckets, true);
                tlist = (List) getObject("tracks");
            }
            for (int i = 0; tlist != null && i < tlist.size(); i++) {
                Map tmap = (Map) tlist.get(i);
                String tidSpace = (String) tmap.get("catalog");
                if (idSpace.equals(tidSpace)) {
                    track = new Track(en, tmap);
                    trackMap.put(idSpace, track);
                }
            }
        }
        return track;
    }

    /**
     * Gets a track for the given idspace
     *
     * @param idSpace the idspace of interest
     * @return
     * @throws EchoNestException
     */
    public Track getTrackOlder(String idSpace) throws ServiceException {
        Track track = trackMap.get(idSpace);
        if (track == null) {
            // nope, so go grab it.
            String[] buckets = {"tracks", "id:" + idSpace};
            fetchBuckets(buckets, true);
            List tlist = (List) getObject("tracks");
            if (tlist != null) {
                for (Object item : tlist) {
                    Map tracks = (Map) item;
                    String catalog = (String) tracks.get("catalog");
                    String trid = (String) tracks.get("id");
                    if (!trackMap.containsKey(catalog)) {
                        track = en.newTrackByID(trid);
                        trackMap.put(catalog, track);
                    }
                }
            }
        }
        return track;
    }



    @SuppressWarnings("unused")
	public Track getTrack(String idSpace) throws ServiceException {
        Track track = trackMap.get(idSpace);
        if (track == null) {
            List tlist = (List) getObject("tracks");
            if (tlist != null) {
                for (Object item : tlist) {
                    Map trackData = (Map) item;
                    String catalog = (String) trackData.get("catalog");
                    String trid = (String) trackData.get("id");
                    if (!trackMap.containsKey(catalog)) {
                        Track ttrack = Track.createTrackFromSong(en, trackData);
                        trackMap.put(catalog, ttrack);
                    }
                }
                track = trackMap.get(idSpace);
            }
        }
        return track;
    }
    
    
}
