/**
 *
 */
package edu.ck.rnd.beans;

import java.io.Serializable;

/**
 * @author Chaitanya
 *
 * @created-on Nov 28, 201411:31:51 PM
 *
 */
public class AdDisplayResult implements Serializable,Comparable<AdDisplayResult> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String link;
	private String description;
	private String keyLinkId;
	public String getKeyLinkId() {
		return keyLinkId;
	}

	public void setKeyLinkId(String keyLinkId) {
		this.keyLinkId = keyLinkId;
	}

	private double finalScore;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}

	@Override
	public int compareTo(AdDisplayResult o) {
		if(o==null){
			return -1;
		}
		if(this.finalScore==o.finalScore){
			return 0;
		}
		if(this.finalScore>o.finalScore){
			return -1;
		}
		return 1;
	}

}
