/**
 *
 */
package edu.ck.rnd.beans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import com.google.gson.Gson;

import edu.buffalo.cse.irf14.document.Document;
import edu.buffalo.cse.irf14.document.Parser;
import edu.buffalo.cse.irf14.index.IndexReader;
import edu.buffalo.cse.irf14.index.IndexType;
import edu.buffalo.cse.irf14.index.IndexWriter;
import edu.buffalo.cse.irf14.index.IndexerException;

/**
 * @author Chaitanya
 *
 * @created-on Nov 28, 20142:46:17 AM
 *
 */
@ManagedBean
@SessionScoped
public class IndexBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String query;
	private List<DisplayResult> results;

	private List<AdDisplayResult> adResults;
	private List<String> twittterResults;

	private double qTime;
	private long numFound;
	private boolean paginationEnabled;
	private long numberOfPages;
	private int range;
	private String xmlFile;
	private String suggestedWord;

	private String currentCategory;
	private String currentFileId;
	
	//CHANGEME: Give local paths to index directory and corpus directory
	private String indexDir = "C:\\Users\\Ashok\\Downloads\\test";
	private String corpusPath = "C:\\Users\\Ashok\\Downloads\\training";

	/**
	 *
	 */
	public String newSearch() {

		HttpSolrServer newsSolrServer = SolrConnectionHandler
				.getNewsSolrServer();

		SolrQuery newsSelectQueryParams = new SolrQuery();
		newsSelectQueryParams.set("q", query);
		newsSelectQueryParams.setHighlight(true);
		newsSelectQueryParams.setHighlightSimplePre("<b>");
		newsSelectQueryParams.setHighlightSimplePost("</b>");
		// http://localhost:8983/solr/collection1/select?q=comm&wt=json&indent=true&spellcheck=true&spellcheck.extendedResults=true&spellcheck.collate=true

		newsSelectQueryParams.setParam("hl.fl", "CONTENT TITLE");
		newsSelectQueryParams.setParam("spellcheck.extendedResults", "true");
		newsSelectQueryParams.setParam("spellcheck", "true");

		if (range != 0) {
			newsSelectQueryParams.setParam("start", range * 10 + "");
		}

		try {
			QueryResponse response = newsSolrServer
					.query(newsSelectQueryParams);

			SolrDocumentList list = response.getResults();
			Map<String, Map<String, List<String>>> highlighting = response
					.getHighlighting();

			System.out.println(list.getNumFound());
			ListIterator<SolrDocument> listIterator = list.listIterator();
			results = new ArrayList<DisplayResult>();
			while (listIterator.hasNext()) {
				SolrDocument solrDocument = listIterator.next();

				DisplayResult result = new DisplayResult();
				result.setFileId((String) solrDocument.get("FILEID"));
				result.setTitle((String) solrDocument.get("TITLE"));
				result.setCategory((String) solrDocument.get("CATEGORY"));
				result.setContent((String) solrDocument.get("CONTENT"));

				// Map<String, Content> highlightedText =
				// highlighting.get(result.getFileId()).get("TITLE").get(0);
				List<String> contentSnippets = highlighting.get(
						result.getFileId()).get("CONTENT");
				List<String> titleSnippets = highlighting.get(
						result.getFileId()).get("TITLE");
				result.setHighlightedContent(contentSnippets == null ? titleSnippets == null ? ""
						: titleSnippets.get(0)
						: contentSnippets.get(0));
				results.add(result);
			}

			qTime = response.getQTime();
			numFound = response.getResults().getNumFound();

			if (numFound > 10) {
				numberOfPages = numFound / 10;
				paginationEnabled = true;
			} else {
				numberOfPages = 0;
				paginationEnabled = false;
			}

			SpellCheckResponse spellCheckResponse = response
					.getSpellCheckResponse();
			List<Suggestion> suggestions = spellCheckResponse.getSuggestions();
			if (suggestions != null && !suggestions.isEmpty()) {
				suggestedWord = suggestions.get(0).getAlternatives().get(0);
			} else {
				suggestedWord = null;
			}
			System.out.println();

			searchAds();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "main";
	}

	/**
	 *
	 */
	public String searchWithSuggestion() {
		query = suggestedWord;
		newSearch();
		return "main";
	}

	/**
	 *
	 */
	@Deprecated
	public String search() {
		int rangeInt = range;
		// int rangeInt = Integer.parseInt(range);
		try {
			URL url = null;

			if (rangeInt == 0) {
				// get URL content
				url = new URL(
						"http://localhost:8983/solr/collection1/select?q="
								+ query.replaceAll("\\s", "%20")
								+ "&wt=json&indent=true&hl=true&hl.fl=CONTENT%2C+TITLE&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E");
			} else {
				url = new URL(
						"http://localhost:8983/solr/collection1/select?q="
								+ query.replaceAll("\\s", "%20")
								+ "&start="
								+ rangeInt
								* 10
								+ "&wt=json&indent=true&hl=true&hl.fl=CONTENT%2C+TITLE&hl.simple.pre=%3Cb%3E&hl.simple.post=%3C%2Fb%3E");
			}

			StringBuffer buffer = getUrlData(url);

			SelectJsonData selectJsonData = new Gson().fromJson(
					buffer.toString(), SelectJsonData.class);

			results = new ArrayList<DisplayResult>();
			for (Docs docs : selectJsonData.getResponse().getDocs()) {
				DisplayResult result = new DisplayResult();
				result.setFileId(docs.getFILEID());
				result.setTitle(docs.getTITLE());
				result.setCategory(docs.getCATEGORY());
				result.setFileId(docs.getFILEID());
				result.setContent(docs.getCONTENT());
				Map<String, Content> highlightedText = selectJsonData
						.getHighlightedText();
				Content content = highlightedText.get(docs.getFILEID());
				String[] contentArray = content.getCONTENT();
				String[] titleArray = content.getTITLE();
				result.setHighlightedContent(contentArray == null ? titleArray == null ? ""
						: titleArray[0]
						: contentArray[0]);
				results.add(result);
			}

			qTime = selectJsonData.getResponseHeader().getQTime();
			numFound = selectJsonData.getResponse().getNumFound();

			if (numFound > 10) {
				numberOfPages = numFound / 10;
				paginationEnabled = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		searchAds();
		return "main";
	}

	private StringBuffer getUrlData(URL url) throws IOException {
		URLConnection conn = url.openConnection();
		// open the stream and put it into BufferedReader
		BufferedReader br = new BufferedReader(new InputStreamReader(
				conn.getInputStream()));

		String inputLine;
		StringBuffer buffer = new StringBuffer();

		while ((inputLine = br.readLine()) != null) {
			buffer.append(inputLine);
		}

		br.close();

		System.out.println(buffer.toString());
		return buffer;
	}

	private AdJsonData searchAds(String queryString) {
		AdJsonData fromJson = null;
		System.out.println(FacesContext.getCurrentInstance()
				.getExternalContext().getContextName());
		try {
			URL url = null;
			//CHANGEME: change the ip and port to connect to your server
			url = new URL("http://localhost:8983/solr/ads/select?q="
					+ URLEncoder.encode(queryString.substring(queryString
							.indexOf(':') != -1 ? queryString.indexOf(":") + 1
							: 0), "UTF-8")
					+ "&rows=50&wt=json&indent=true&fl=*,score");
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;
			StringBuffer buffer = new StringBuffer();

			while ((inputLine = br.readLine()) != null) {
				buffer.append(inputLine);
			}

			br.close();

			System.out.println(buffer.toString());

			fromJson = new Gson().fromJson(buffer.toString(), AdJsonData.class);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return fromJson;
	}

	/**
	 *
	 */
	private void searchAds() {
		AdJsonData fromJson = searchAds(query);

		adResults = new ArrayList<AdDisplayResult>();
		List<AdDocs> docs = fromJson.getResponse().getDocs();

		if (docs.isEmpty() && !results.isEmpty()) {
			docs = modifyAdSearch();
		}

		if (!docs.isEmpty()) {
			HashMap<String, Double> bidAmts = DatabaseHandler.getBidAmts(docs);
			for (AdDocs doc : docs) {
				AdDisplayResult result = new AdDisplayResult();
				result.setLink(doc.getLINK());
				result.setDescription(doc.getDESCRIPTION());
				result.setFinalScore(Double.parseDouble(doc.getSCORE()) * 0.6
						+ bidAmts.get(doc.getKEYLINK_ID()) * 0.4);
				result.setKeyLinkId(doc.getKEYLINK_ID());
				adResults.add(result);
			}
			Object[] resultsArr = adResults.toArray();
			Arrays.sort(resultsArr);
			adResults = new ArrayList<AdDisplayResult>();
			for (int i = 0, j = 0; i < resultsArr.length && j < 3; i++, j++) {
				adResults.add((AdDisplayResult) resultsArr[i]);
			}
			System.out.println("done");
		}

		if (adResults != null && adResults.isEmpty()) {
			twittterResults = crawl(query);
		}else{
			twittterResults = null;
		}
	}
	
	public List<String> crawl(String queryString) {

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("U6nxloWGEiPIHThAsd9RrPLfd")
				.setOAuthConsumerSecret(
						"2cEu0e5sqSOMWGtPnKEuBW2FMg2LTTiRolJBfv4n87rYYZsrjY")
				.setOAuthAccessToken(
						"2914204832-HWc1BhGFLgEXoIMlCu73Cvx5V2pHq8RymyWO3C0")
				.setOAuthAccessTokenSecret(
						"my32dxYoXeBnhYuW4MlWSxeEBP0jtXN5NN66lhihQjOhl");
		TwitterFactory tf = new TwitterFactory(cb.build());

		Integer numberOfTweets = 3;
		Twitter twitter = tf.getInstance();

		Query query = new Query(queryString);
		query.setLang("en");
		query.setCount(numberOfTweets);
		QueryResult result;
		List<String> sendResult = new ArrayList<String>();
		try {
			result = twitter.search(query);

			List<Status> tweets = result.getTweets();
			for (Status status : tweets) {
				sendResult.add(status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return sendResult;
	}
	
	public void updateCounter(String keyLinkID, String link) {
		try {
			DatabaseHandler.incrementClickCount(Long.parseLong(keyLinkID));
			FacesContext.getCurrentInstance().getExternalContext().redirect(link);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private List<AdDocs> modifyAdSearch() {
		IndexWriter writer = new IndexWriter(indexDir);
		for (DisplayResult result : results) {
			try {
				Document d = Parser.parse(corpusPath + File.separatorChar
						+ result.getCategory() + File.separatorChar
						+ result.getFileId());
				writer.addDocument(d);
			} catch (edu.buffalo.cse.irf14.document.ParserException e) {
				e.printStackTrace();
			} catch (IndexerException e) {
				e.printStackTrace();
			}
		}
		try {
			writer.close();
		} catch (IndexerException e) {
			e.printStackTrace();
		}

		IndexReader reader = new IndexReader(indexDir, IndexType.TERM);
		List<String> terms = reader.getTopK(4);
		String q = new String();
		for (String term : terms) {
			q += term + " ";
		}

		AdJsonData fromJson = searchAds(q);
		return fromJson.getResponse().getDocs();
	}

	/**
	 *
	 */
	public String fetchFile() {
		File file = new File(corpusPath + File.separatorChar + currentCategory
				+ File.separatorChar + currentFileId);
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					file));

			String inputLine;
			StringBuffer buffer = new StringBuffer();
			while ((inputLine = bufferedReader.readLine()) != null) {
				buffer.append(inputLine);
			}
			bufferedReader.close();
			currentCategory = null;
			currentFileId = null;
			xmlFile = buffer.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "xmlFile";
	}

	public List<DisplayResult> getResults() {
		return results;
	}

	public void setResults(List<DisplayResult> results) {
		this.results = results;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public double getqTime() {
		return qTime;
	}

	public void setqTime(double qTime) {
		this.qTime = qTime;
	}

	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}

	public boolean isPaginationEnabled() {
		return paginationEnabled;
	}

	public void setPaginationEnabled(boolean paginationEnabled) {
		this.paginationEnabled = paginationEnabled;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public static void main(String[] args) {

		IndexBean indexBean = new IndexBean();
		indexBean.query = "comm";
		indexBean.newSearch();
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}

	public String getCurrentCategory() {
		return currentCategory;
	}

	public void setCurrentCategory(String currentCategory) {
		this.currentCategory = currentCategory;
	}

	public String getCurrentFileId() {
		return currentFileId;
	}

	public void setCurrentFileId(String currentFileId) {
		this.currentFileId = currentFileId;
	}

	public List<AdDisplayResult> getAdResults() {
		return adResults;
	}

	public void setAdResults(List<AdDisplayResult> adResults) {
		this.adResults = adResults;
	}

	public String getSuggestedWord() {
		return suggestedWord;
	}

	public void setSuggestedWord(String suggestedWord) {
		this.suggestedWord = suggestedWord;
	}

	public long getNumFound() {
		return numFound;
	}

	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}

	public long getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(long numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public List<String> getTwittterResults() {
		return twittterResults;
	}

	public void setTwittterResults(List<String> twittterResults) {
		this.twittterResults = twittterResults;
	}
}
