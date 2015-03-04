/**
 *
 */
package edu.ck.rnd.beans;

import java.io.Serializable;

/**
 * @author Chaitanya
 *
 * @created-on Nov 28, 20144:13:58 AM
 *
 */
public class DisplayResult implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String author;
	private String authorOrg;
	private String category;
	private String content;
	private String fileId;
	private String newsDate;
	private String place;
	private String title;
	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getHighlightedContent() {
		return highlightedContent;
	}

	public void setHighlightedContent(String highlightedContent) {
		this.highlightedContent = highlightedContent;
	}

	private String highlightedContent;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorOrg() {
		return authorOrg;
	}

	public void setAuthorOrg(String authorOrg) {
		this.authorOrg = authorOrg;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
