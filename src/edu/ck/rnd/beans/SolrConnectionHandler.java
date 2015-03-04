/**
 *
 */
package edu.ck.rnd.beans;

import javax.faces.bean.ApplicationScoped;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;

/**
 * @author Chaitanya
 *
 * @created-on Nov 29, 201410:38:34 PM
 *
 */
@ApplicationScoped
public class SolrConnectionHandler {

	//CHANGEME: change the ip and port to connect to your server
	private static final String NEWS_CORE_URL = "http://localhost:8983/solr/news";
	
	private static HttpSolrServer newsSolrServer;

	private SolrConnectionHandler() {
	}

	public static HttpSolrServer getNewsSolrServer() {
		if (newsSolrServer == null) {
			newsSolrServer = new HttpSolrServer(NEWS_CORE_URL);
			newsSolrServer.setMaxRetries(1); // defaults to 0. > 1 not
			// recommended.
			newsSolrServer.setConnectionTimeout(5000); // 5 seconds to establish
			// TCP
			// Setting the XML response parser is only required for cross
			// version compatibility and only when one side is 1.4.1 or
			// earlier and the other side is 3.1 or later.
			newsSolrServer.setParser(new XMLResponseParser()); // binary parser
			// is used by
			// default
			// The following settings are provided here for completeness.
			// They will not normally be required, and should only be used
			// after consulting javadocs to know whether they are truly
			// required.
			newsSolrServer.setSoTimeout(1000); // socket read timeout
			newsSolrServer.setDefaultMaxConnectionsPerHost(100);
			newsSolrServer.setMaxTotalConnections(100);
			newsSolrServer.setFollowRedirects(false); // defaults to false
			// allowCompression defaults to false.
			// Server side must support gzip or deflate for this to have any
			// effect.
			newsSolrServer.setAllowCompression(true);
		}
		return newsSolrServer;
	}

}
