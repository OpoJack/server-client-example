package module11;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/* Name: Jack Oporto
 * Class: CEN 3024C
 * Professor Dhrgam Al Kafaf
 * Assignment: Module 11, SDLC
 * Date completed: December 5, 2020
 * 
 * Summary:
 * This program demonstrates client-server functionality
 * using JavaFX. Both the Server and Client communicate information to each
 * other to determine if the user's number input is prime or not.
 */

public class Client extends Application{

	DataOutputStream toServer = null;
	DataInputStream fromServer = null;
	
	@Override //Override the start method in the Application class
	public void start(Stage primaryStage) {
		BorderPane paneForTextField = new BorderPane();
		paneForTextField.setPadding(new Insets(5, 5, 5, 5));
		paneForTextField.setStyle("-fx-border-color: green");
		paneForTextField.setLeft(new Label("Enter a number to prime-check: "));
		
		TextField tf = new TextField();
		tf.setAlignment(Pos.BOTTOM_RIGHT);
		paneForTextField.setCenter(tf);
		
		BorderPane mainPane = new BorderPane();
		// Text area to display contents
		TextArea ta = new TextArea();
		mainPane.setCenter(new ScrollPane(ta));
		mainPane.setTop(paneForTextField);
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(mainPane, 450, 200);
		primaryStage.setTitle("Client"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
		tf.setOnAction(e -> {
			try {
				// Get the input from the text field        
				int myNumber = Integer.parseInt(tf.getText().trim());
				
				// Send the number to the server
				toServer.writeInt(myNumber);
				toServer.flush();
				
				//Get area from the server
				boolean result = fromServer.readBoolean();
				
				//Display to the text area
				ta.appendText("You entered " + myNumber + "\n");
				if(result == true)
					ta.appendText("Server says " + myNumber + " is prime.\n");
				else
					ta.appendText("Server says " + myNumber + " is not prime.\n");
			}catch (IOException ex) {
				System.err.println(ex);
			}
		});
		
		try {
			//Create a socket to connect to the server
			Socket socket = new Socket("localhost", 8000);
			
			//Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());
			
			//Create an output stream to send data to the server
			toServer = new DataOutputStream(socket.getOutputStream());
		}catch (IOException ex) {
			ta.appendText(ex.toString() + '\n');
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
