package twig.beans;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Diction implements Serializable{

	private String result = null;
	private Authors authors = null;
	private String dest = null;
	private String phrase = null;
	private List<Entry> tuc = null;
	private String from = null;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Authors getAuthors() {
		return authors;
	}
	public void setAuthors(Authors authors) {
		this.authors = authors;
	}
	public String getDest() {
		return dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
	public String getPhrase() {
		return phrase;
	}
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public List<Entry> getTuc() {
		return tuc;
	}
	public void setTuc(List<Entry> tuc) {
		this.tuc = tuc;
	}

}
