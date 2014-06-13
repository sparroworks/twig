package twig.gui;


import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class MainGUI extends JFrame{
	
	private JLabel word = new JLabel();
	private JTextArea meanings = new JTextArea();
	
	public MainGUI(String word, List<String> wordMeanings){
		setTitle("Dictionary");
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
	    
	    
	    //Create components
	    this.word.setText(word);
	    this.meanings.setText(formatText(wordMeanings));
	    this.meanings.setEditable(false);
	    JScrollPane sPane = new JScrollPane(this.meanings);
	    sPane.setBorder(BorderFactory.createEmptyBorder());
	    
	    
	    //Create layout
	    JPanel panel = new JPanel(new MigLayout("wrap 1"));
	    panel.add(this.word,"wrap");
	    panel.add(sPane);
	    panel.setBackground(Color.white);
	    
	    this.getContentPane().add(panel);
	    this.setMinimumSize(new Dimension(200,200));
	    this.setBackground(Color.white);
	    this.pack();
		
	}
	private String formatText(List<String> meanings){
		StringBuffer str = new StringBuffer();
		for(String meaning : meanings){
			List<String> splitText = textSplit(100,meaning);
			str.append("    -- ");
			for(int i =0 ; i < splitText.size();i++){
				String s = splitText.get(i);
				if((i+1) >= splitText.size()){
					str.append(s+"\n");
				}
				else{
					str.append(s+"\n        ");
				}
			}
			
		}
		return str.toString();
	}
	
	private List<String> textSplit(int limit, String input){
		List<String> retText = new ArrayList<String>();
		int currLen = input.length();
		int currCount = 0;
		StringBuffer text = new StringBuffer();
		for(int i = 0; i < currLen;i++){
			char ch = input.charAt(i);
			text.append(ch);
			currCount++;
			if(currCount >= limit){
				if(!(ch >= 33)){
					retText.add(text.toString());
					text = new StringBuffer();
					currCount = 0;
				}
			}
		}
		if(!text.toString().isEmpty()){
			retText.add(text.toString());
		}
			
		return retText;
	}
	

}

