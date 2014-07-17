package is.fon.rs.restservice;

import is.fon.rs.restservice.MQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class TrackAnalysis {

   
	private Map map;
    private MQuery mq;

    TrackAnalysis(Map map) {
        this.map = map;
        this.mq = new MQuery(map);
    }

    public Map getMap() {
        return map;
    }

    public String toString() {
        return map.toString();
    }

    public Integer getNumSamples() {
        return mq.getInteger("track.num_samples");
    }

    public Double getDuration() {
        return mq.getDouble("track.duration");
    }

    public String getMD5() {
        return mq.getString("track.sample_md5");
    }

    public Double getSampleRate() {
        return mq.getDouble("track.analysis_sample_rate");
    }

    public Integer getNumChannels() {
        return mq.getInteger("track.analysis_channels");
    }

    public Double getLoudness() {
        return mq.getDouble("track.loudness");
    }

    public Double getTempo() {
        return mq.getDouble("track.tempo");
    }

   

    

 

    public List<TimedEvent> getBars() {
        List<TimedEvent> results = new ArrayList<TimedEvent>();
        List event = (List) mq.getObject("bars");
        for (int i = 0; i < event.size(); i++) {
            results.add(new TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    public List<TimedEvent> getBeats() {
        List<TimedEvent> results = new ArrayList<TimedEvent>();
        List event = (List) mq.getObject("beats");
        for (int i = 0; i < event.size(); i++) {
            results.add(new TimedEvent((Map) event.get(i)));
        }
        return results;
    }

    public List<Segment> getSegments() {
        List<Segment> results = new ArrayList<Segment>();
        List event = (List) mq.getObject("segments");
        for (int i = 0; i < event.size(); i++) {
            results.add(new Segment((Map) event.get(i)));
        }
        return results;
    }

  
}
