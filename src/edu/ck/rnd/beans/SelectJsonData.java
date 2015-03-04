/**
 *
 */
package edu.ck.rnd.beans;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

/**
 * @author Chaitanya
 *
 * @created-on Nov 28, 20144:19:49 AM
 *
 */
public class SelectJsonData {
	private Response response;
	private ResponseHeader responseHeader;
	@SerializedName("highlighting")
	private Map<String, Content> highlightedText;

	public Map<String, Content> getHighlightedText() {
		return highlightedText;
	}

	public void setHighlightedText(Map<String, Content> highlightedText) {
		this.highlightedText = highlightedText;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

}

class Response {
	private List<Docs> docs;
	private int numFound;
	private int start;

	public List<Docs> getDocs() {
		return docs;
	}

	public void setDocs(List<Docs> docs) {
		this.docs = docs;
	}

	public int getNumFound() {
		return numFound;
	}

	public void setNumFound(int numFound) {
		this.numFound = numFound;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}

class Docs {

	private String AUTHOR;
	private String AUTHORORG;
	private String CATEGORY;
	private String CONTENT;
	private String FILEID;
	private String NEWSDATE;
	private String PLACE;
	private String TITLE;

	public String getAUTHOR() {
		return AUTHOR;
	}

	public void setAUTHOR(String aUTHOR) {
		AUTHOR = aUTHOR;
	}

	public String getAUTHORORG() {
		return AUTHORORG;
	}

	public void setAUTHORORG(String aUTHORORG) {
		AUTHORORG = aUTHORORG;
	}

	public String getCATEGORY() {
		return CATEGORY;
	}

	public void setCATEGORY(String cATEGORY) {
		CATEGORY = cATEGORY;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getFILEID() {
		return FILEID;
	}

	public void setFILEID(String fILEID) {
		FILEID = fILEID;
	}

	public String getNEWSDATE() {
		return NEWSDATE;
	}

	public void setNEWSDATE(String nEWSDATE) {
		NEWSDATE = nEWSDATE;
	}

	public String getPLACE() {
		return PLACE;
	}

	public void setPLACE(String pLACE) {
		PLACE = pLACE;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}
}

class ResponseHeader {
	private double QTime;
	private Params params;
	private int status;

	public double getQTime() {
		return QTime;
	}

	public void setQTime(double qTime) {
		QTime = qTime;
	}

	public Params getParams() {
		return params;
	}

	public void setParams(Params params) {
		this.params = params;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}

class Params {
	private boolean indent;
	private String q;
	private String wt;
	private boolean hl;
	@SerializedName("hl.simple.pre")
	private String hlsimplepret;
	@SerializedName("hl.simple.post")
	private String hlsimplepost;

	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getWt() {
		return wt;
	}

	public void setWt(String wt) {
		this.wt = wt;
	}

	public boolean isHl() {
		return hl;
	}

	public void setHl(boolean hl) {
		this.hl = hl;
	}

	public String getHlsimplepret() {
		return hlsimplepret;
	}

	public void setHlsimplepret(String hlsimplepret) {
		this.hlsimplepret = hlsimplepret;
	}

	public String getHlsimplepost() {
		return hlsimplepost;
	}

	public void setHlsimplepost(String hlsimplepost) {
		this.hlsimplepost = hlsimplepost;
	}

}