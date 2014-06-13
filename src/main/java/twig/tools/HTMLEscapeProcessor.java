package twig.tools;

public class HTMLEscapeProcessor {

	private static HTMLEscapeProcessor processor = null;

	private HTMLEscapeProcessor(){
		
	}
	
	public static synchronized HTMLEscapeProcessor getInstance(){
		if(processor == null){
			processor = new HTMLEscapeProcessor();
		}
		return processor;
	}
	
	public String convertHTMLEscapeChars(String input){
		try{
			input = input.replace("&quot;", "\"");
			input = input.replace("&rsquo;", "'");
		}catch(Exception e){
			//nothing here
		}
		
		return input;
	}

}
