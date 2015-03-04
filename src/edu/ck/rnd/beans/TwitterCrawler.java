package edu.ck.rnd.beans;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterCrawler {
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

		Integer numberOfTweets = 10;
		Twitter twitter = tf.getInstance();
		Query query = new Query(queryString);
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

	public static void main(String[] args) {
		TwitterCrawler crawler = new TwitterCrawler();
		for (String string : crawler.crawl("obama")) {
			System.out.println(string);
		}
	}
}
