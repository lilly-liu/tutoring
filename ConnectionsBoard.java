/* import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import org.json.simple.parser.*;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject; */ 

import java.awt.*;
import java.awt.event.*; 
import java.io.*; 
import java.util.*; 
import javax.swing.*;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConnectionsBoard extends JPanel implements ActionListener {

	// hardcoded answers for now until i figure out how to read in the JSON file
	
	private String filePath = "src/connections.json"; 
//	private String[] groups = {"WET WEATHER", "NBA TEAMS", "KEYBOARD KEYS", "PALINDROMES"}; 
//	private ArrayList<String> words = new ArrayList<String>(Arrays.asList("HAIL", "RAIN", "SLEET", "SNOW", "BUCKS", "HEAT", "JAZZ", "NETS", "OPTION",
//            "RETURN", "SHIFT", "TAB", "KAYAK", "LEVEL", "MOM", "RACECAR")); 
//	private String[][] correctAnswers = {{"HAIL", "RAIN", "SLEET", "SNOW"}, 
//			{"BUCKS", "HEAT", "JAZZ", "NETS"}, 
//			{"OPTION", "RETURN", "SHIFT", "TAB"}, 
//			{"KAYAK", "LEVEL", "MOM", "RACECAR"}}; 
	private ArrayList<String> groups; 
	private ArrayList<String> words; 
	private String[][] correctAnswers; 
	private HashSet<JButton> clickedButtons; 
	private JPanel grid; 
	private int attempts; 
	
	public ConnectionsBoard() {
		 
		this.groups = new ArrayList<String>(); 
		this.words = new ArrayList<String>(); 
		this.correctAnswers = new String[4][4]; 
		
		loadDataFromJSON(filePath);
		createGrid(); 
		
		clickedButtons = new HashSet<JButton>(); 
		
		JPanel buttonPanel = new JPanel(); 
		
		JButton submitButton = new JButton("Submit"); 
		submitButton.addActionListener(new SubmitButtonListener()); 
		buttonPanel.add(submitButton); 
		
		this.setLayout(new GridLayout(2,1)); 
		this.add(grid); 
		this.add(buttonPanel);

	}

	public void createGrid() {
		grid = new JPanel(new GridLayout(4,4)); 
		this.add(grid); 
		
		Collections.shuffle(words);
		System.out.println(words); 
		
		for (int i = 0; i < words.size(); i++) {
			JButton button = new JButton(words.get(i)); 
			grid.add(button); 
			button.addActionListener(this);
		}
		
		setVisible(true); 
	}
	
	private void loadDataFromJSON(String filePath) {
		JSONParser parser = new JSONParser(); 
		try {
	        Object obj = parser.parse(new FileReader(filePath));
	        
	        JSONArray jsonArray = (JSONArray) obj;
	        
	        int randomDay = (int) (Math.random() * jsonArray.size()); 
	        JSONObject day = (JSONObject) jsonArray.get(randomDay); 
	        
	        JSONArray answersArray = (JSONArray) day.get("answers"); 
	        
	        for (int i = 0; i < answersArray.size(); i++) {
	        	JSONObject level = (JSONObject) answersArray.get(i); 
	        	groups.add((String) level.get("group")); 
	        	JSONArray members = (JSONArray) level.get("members");
	        	for (int j = 0; j < members.size(); j++) {
	        		words.add((String) members.get(j));
	        	}
	        }
	        
	        int counter = 0; 
	        for (int i = 0; i < correctAnswers.length; i++) {
	        	for (int j = 0; j < correctAnswers[0].length; j++) {
	        		correctAnswers[i][j] = words.get(counter); 
	        		counter++; 
	        	}
	        }
	        
	        System.out.println(Arrays.deepToString(correctAnswers)); 
	        
	    } catch (IOException e) {
	        System.out.println("Error reading JSON file: " + e.getMessage());
	    } catch (ParseException e) {
	        System.out.println("Error parsing JSON data: " + e.getMessage());
	    }
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource(); 
		String word = button.getText(); 
		
		if (clickedButtons.contains(button)) {
			clickedButtons.remove(button);
			System.out.println("Button unclicked: " + word); 
			button.setBackground(null); // reset background color
		}
		else {
			
			if (clickedButtons.size() == 4) {
				JOptionPane.showMessageDialog(grid, "Can't click more than four!");
				return;
			}
			clickedButtons.add(button);
			System.out.println("Button clicked: " + word);
			button.setOpaque(true);
			button.setBackground(Color.GRAY); // set background color
		
		}
	}
	
	private class SubmitButtonListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (clickedButtons.size() < 4) {
				JOptionPane.showMessageDialog(grid, "Must select four!");
				return; 
			}
			System.out.println(clickedButtons); 
			
			if (clickedButtons.size() == 4) {
				// check if it is in the same group
				// have a counter of correct words to check for 3/4 
			}
			
			for (String[] category : correctAnswers) {
				for (JButton b : clickedButtons) {
					if (!Arrays.asList(category).contains(b.getText())) {
						break; 
					}
				}
			}
			
		}
		
	}
}

	
