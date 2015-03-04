/**
 *
 */
package edu.ck.rnd.beans;

import java.util.List;
import java.util.Map;

/**
 * @author Chaitanya
 *
 * @created-on Nov 29, 20149:43:34 PM
 *
 */
public class SpelCheckJsonData {

	private Response response;
	private ResponseHeader responseHeader;
	private Spellcheck[] spellcheck;

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public Spellcheck[] getSpellcheck() {
		return spellcheck;
	}

	public void setSpellcheck(Spellcheck[] spellcheck) {
		this.spellcheck = spellcheck;
	}
}

class Spellcheck {
	private Suggestions[] suggestions;

	public Suggestions[] getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(Suggestions[] suggestions) {
		this.suggestions = suggestions;
	}
}

class Suggestions {

	private boolean correctlySpelled;
	private List<Collation> collation;

	public boolean isCorrectlySpelled() {
		return correctlySpelled;
	}

	public void setCorrectlySpelled(boolean correctlySpelled) {
		this.correctlySpelled = correctlySpelled;
	}

	public List<Collation> getCollation() {
		return collation;
	}

	public void setCollation(List<Collation> collation) {
		this.collation = collation;
	}
}

class Collation {
	private String collationQuery;
	private int hits;
	private Map<String, String> misspellingsAndCorrections;

	public String getCollationQuery() {
		return collationQuery;
	}

	public void setCollationQuery(String collationQuery) {
		this.collationQuery = collationQuery;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public Map<String, String> getMisspellingsAndCorrections() {
		return misspellingsAndCorrections;
	}

	public void setMisspellingsAndCorrections(
			Map<String, String> misspellingsAndCorrections) {
		this.misspellingsAndCorrections = misspellingsAndCorrections;
	}
}