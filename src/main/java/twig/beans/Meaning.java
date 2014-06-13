package twig.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Meaning implements Serializable{

	private String text = null;
	private String language = null;
	
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	

}
