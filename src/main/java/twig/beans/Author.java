package projectpintail.twig.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Author implements Serializable{

	private Integer id = null;
	private String U = null;
	private String N = null;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getU() {
		return U;
	}
	public void setU(String u) {
		U = u;
	}
	public String getN() {
		return N;
	}
	public void setN(String n) {
		N = n;
	}
	
	
	

}
