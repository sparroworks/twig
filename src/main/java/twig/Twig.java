package twig;


import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.SwingUtilities;

import twig.beans.Diction;
import twig.beans.Entry;
import twig.beans.Meaning;
import twig.gui.MainGUI;
import twig.tools.HTMLEscapeProcessor;


import com.google.gson.Gson;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

public class Twig implements HotkeyListener{

JIntellitype jType = null;
public static String TWIGBOARD = "F:\\Gen3_Automation\\Users\\arajagox\\twigboard.txt";
//public static String TWIGBOARD = "C:\\twigboard.txt";

	Twig() {
		jType = JIntellitype.getInstance();
		jType.registerHotKey(1, JIntellitype.MOD_CONTROL, (int)'0');
		jType.registerHotKey(2, JIntellitype.MOD_CONTROL, (int)'8');
		jType.registerHotKey(3, JIntellitype.MOD_CONTROL, (int)'9');
		JIntellitype.getInstance().addHotKeyListener(this);
	}
	
	private String processPhrase(String phrase)
	{
		URL url;
		HttpURLConnection connection = null;  
		String targetURL = "http://glosbe.com/gapi/translate?from=eng&dest=eng&format=json&phrase="+phrase+"&pretty=true";
		try{
			//Need proxy settings for work environment
			//*************************************************************
			System.setProperty("http.proxyHost", "proxy01.fm.intel.com");
			System.setProperty("http.proxyPort", "911");
			//*************************************************************
			url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Language", "en-US"); 
			connection.connect();
		    
		    int responseCode = connection.getResponseCode();
		    //Get response
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer();
		    while((line = rd.readLine()) != null) {
		    	
		        response.append(line);
		        response.append('\r');
		      }
		    rd.close();
		    return response.toString();
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	public void onHotKey(int id) {
		//Get text from clip board
		
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		String text = null;
		String error = "Unable to obtain copied content";
		boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
		if (hasTransferableText) {
			try {
		        text = (String)contents.getTransferData(DataFlavor.stringFlavor);
		    }
			catch (UnsupportedFlavorException ex){
				text = null;
				error = "Unsupported format of copied content";
			}
			catch(IOException e){
				text = null;
				error = "IO Error in getting copied content";
			}
		}
		if(id == 1){
			String word = text;
			List<String> meanings = new ArrayList<String>();
			if(word != null){
				word = word.trim().toLowerCase();
				String jsonString = processPhrase(word);
				
				try
				{
					Gson gson = new Gson();
					Diction dict = gson.fromJson(jsonString, Diction.class);
					meanings = parseDiction(dict);
					if(meanings.isEmpty()){
						meanings.add("No Definition Found");
					}
				}catch(Exception e){
					meanings = new ArrayList<String>();
					meanings.add("Sorry :( ... Unable to fetch definition.");
				}
			}
			else{
				word = error;
			}
			//Now create the GUI
			
			final String w = word;
			final List<String> m = meanings;
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
		            /*try {
		                // 0 => "javax.swing.plaf.metal.MetalLookAndFeel"
		                // 3 => the Windows Look and Feel
		                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		            } catch (Exception ex) {
		                //nothing here
		            }*/
		            MainGUI gui = new MainGUI(w, m);
					gui.setVisible(true);
					
			}
			});
			
			
		}
		if(id == 2){//Copy to twig board file
			
			//Write into twig board file
			if(text != null){
				
				try {
					BufferedWriter toWriter = new BufferedWriter(new FileWriter(Twig.TWIGBOARD));
					toWriter.write(text);
					toWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		}
		if(id == 3){//Paste from twig board file
			//Get text from twig board file
			BufferedReader fromReader;
			StringBuffer copiedText = new StringBuffer();
			try {
				fromReader = new BufferedReader(new FileReader(Twig.TWIGBOARD));
			
			
			String line = null;
			while((line=fromReader.readLine()) != null){
				copiedText.append(line);
			}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			//Put into clip board
			StringSelection stringSelection = new StringSelection(copiedText.toString());
			clipboard.setContents(stringSelection, null);
		}
		
	}
	
	private List<String> parseDiction(Diction dict){
		List<String> meanings = new ArrayList<String>();
		
		//Meanings
		List<Entry> tuc = dict.getTuc();
		if(tuc != null){
			for(Entry t : tuc){
				List<Meaning> mList = t.getMeanings();
				if(mList != null){
					for(Meaning m : mList){
						if(!meanings.contains(m.getText())){
							HTMLEscapeProcessor proc = HTMLEscapeProcessor.getInstance();
							meanings.add(proc.convertHTMLEscapeChars(m.getText()));
						}
					}
				}
				
			}
		}
		return meanings;
	}
	
	public static void onStart(){
        Preferences prefs = Preferences.userRoot().node("Twig");
        prefs.put("RUNNING", "true");
    }
    public static void onFinish(){
        Preferences prefs = Preferences.userRoot().node("Twig");
        prefs.put("RUNNING", "false");
    }
    public static boolean isRunning(){
        Preferences prefs = Preferences.userRoot().node("Twig");
        return prefs.get("RUNNING", null) != null ? Boolean.valueOf(prefs.get("RUNNING", null)) : false;
            
    }
    public static void onMain(){
    	new Twig();
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(isRunning()){
            System.out.println("Two instances of this program cannot " +
                    "be running at the same time.  Exiting now");
        }
        else{

			Twig.onStart();
			try{
				Twig.onMain();
			}catch(Exception e)
			{
				System.out.println(e.getMessage());
				
			}
			Twig.onFinish();
        }

		
			    
	}
	

}

