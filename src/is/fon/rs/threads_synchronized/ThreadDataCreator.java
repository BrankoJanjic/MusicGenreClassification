
package is.fon.rs.threads_synchronized;

import is.fon.rs.restservice.PlaylistParams;

/**
 *
 * @author plamere
 */
public class ThreadDataCreator extends PlaylistParams {

 
    public void setResults(int results) {
        throw new UnsupportedOperationException("setResults not supported on dynamic playlist create");
    }

    public void addSessionCatalog(String id) {
        super.add("session_id", id);
    }
}
