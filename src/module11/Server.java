package module11;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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

public class Server extends Application{

	@Override
	public void start(Stage primaryStage) {
		//Text area for displaying contents
		TextArea ta = new TextArea();
		
		//Create a scene and place it in the stage
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server"); // Set the stage title    
		primaryStage.setScene(scene); // Place the scene in the stage    
		primaryStage.show(); // Display the stage
		
		new Thread( () -> {
			try {
				//Create a server socket
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater( () ->
					ta.appendText("Server started at " + new Date() + '\n'));
				
				//Listen for a connection request
				Socket socket = serverSocket.accept();
				
				//Create data input and output streams
				DataInputStream inputFromClient = new DataInputStream(
						socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(
						socket.getOutputStream());
				
				while (true) {
					//Receive input from the client
					int myNumber = inputFromClient.readInt();
					boolean result = isPrime(myNumber);
					
					//Send area back to the client
					outputToClient.writeBoolean(result);
					
					Platform.runLater(() -> {
						ta.appendText("Number received from client: " + myNumber + '\n');
						if(result == true)
							ta.appendText(myNumber + " is prime.\n");
						else
							ta.appendText(myNumber + " is not prime.\n");
					});
					}
				}catch(IOException ex) {
					ex.printStackTrace();
				}
			}).start();
		}
	
	public static boolean isPrime(int num) {
		int i = 2;
	    boolean flag = false;
	    while (i <= num / 2) {
	      // condition for nonprime number
	      if (num % i == 0) {
	        flag = true;
	        break;
	      }

	      ++i;
	    }

	    if (!flag)
	      return true;
	    else
	      return false;
	  }
	
	public static void main(String[] args) {
		launch(args);

	}

}
