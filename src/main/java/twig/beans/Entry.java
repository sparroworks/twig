package twig.beans;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Entry implements Serializable{

	private List<Integer> authors = null;
	private Integer meaningId = null;
	private List<Meaning> meanings = null;
	public List<Integer> getAuthorIds() {
		return authors;
	}
	public void setAuthorIds(List<Integer> authorIds) {
		this.authors = authorIds;
	}
	public Integer getMeaningId() {
		return meaningId;
	}
	public void setMeaningId(Integer meaningId) {
		this.meaningId = meaningId;
	}
	public List<Meaning> getMeanings() {
		return meanings;
	}
	public void setMeanings(List<Meaning> meanings) {
		this.meanings = meanings;
	}
	
	

}
