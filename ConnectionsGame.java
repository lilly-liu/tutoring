import java.awt.*;
import javax.swing.*; 

public class ConnectionsGame {

	public static void main(String[] args) {
//		JSONParser parser = new JSONParser(); 
//		JSONArray array = new JSONArray(); 
		
		JFrame frame = new JFrame("Connections");
		frame.setSize(600, 400);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ConnectionsBoard board = new ConnectionsBoard();
		frame.setContentPane(board);
		frame.setVisible(true);
	}

}
