package projectpintail.twig.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Authors implements Serializable{

	private Author author1 = null;
	private Author author2 = null;
	private Author author3 = null;
	private Author author4 = null;
	private Author author5 = null;
	public Author getAuthor1() {
		return author1;
	}
	public void setAuthor1(Author author1) {
		this.author1 = author1;
	}
	public Author getAuthor2() {
		return author2;
	}
	public void setAuthor2(Author author2) {
		this.author2 = author2;
	}
	public Author getAuthor3() {
		return author3;
	}
	public void setAuthor3(Author author3) {
		this.author3 = author3;
	}
	public Author getAuthor4() {
		return author4;
	}
	public void setAuthor4(Author author4) {
		this.author4 = author4;
	}
	public Author getAuthor5() {
		return author5;
	}
	public void setAuthor5(Author author5) {
		this.author5 = author5;
	}
	

}
