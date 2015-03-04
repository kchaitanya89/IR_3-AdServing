/**
 *
 */
package edu.ck.rnd.beans;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * @author Chaitanya
 *
 * @created-on Nov 28, 201411:26:24 PM
 *
 */
public class AdJsonData {
	private AdResponse response;
	private AdResponseHeader responseHeader;

	public AdResponse getResponse() {
		return response;
	}

	public void setResponse(AdResponse response) {
		this.response = response;
	}

	public AdResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(AdResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

}

class AdResponse {
	private List<AdDocs> docs;
	private int numFound;
	private int start;

	public List<AdDocs> getDocs() {
		return docs;
	}

	public void setDocs(List<AdDocs> docs) {
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

class AdDocs {

	private String KEYLINK_ID;
	private String LINK_ID;
	private String DESCRIPTION;
	private String LINK;
	private String KEYWORD;
	@SerializedName("score")
	private String SCORE;

	public String getKEYLINK_ID() {
		return KEYLINK_ID;
	}

	public void setKEYLINK_ID(String kEYLINK_ID) {
		KEYLINK_ID = kEYLINK_ID;
	}

	public String getLINK_ID() {
		return LINK_ID;
	}

	public void setLINK_ID(String lINK_ID) {
		LINK_ID = lINK_ID;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getLINK() {
		return LINK;
	}

	public void setLINK(String lINK) {
		LINK = lINK;
	}

	public String getKEYWORD() {
		return KEYWORD;
	}

	public void setKEYWORD(String kEYWORD) {
		KEYWORD = kEYWORD;
	}

	public String getSCORE() {
		return SCORE;
	}

	public void setSCORE(String sCORE) {
		SCORE = sCORE;
	}
}

class AdResponseHeader {
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

class AdParams {
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