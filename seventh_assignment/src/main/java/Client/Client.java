package Client;

import java.io.*;
import java.net.Socket;

/**
 * WebSocket client implementation.
 */
public class Client {

    /**
     * Main method to start the WebSocket client.
     *
     * @param args Command line arguments (not used)
     * @throws IOException If an I/O error occurs while communicating with the server
     */
    public static void main(String[] args) throws IOException {
        // Establish a connection to the server
        Socket client = new Socket("localhost", 1234);
        // Output stream to send data to the server
        DataOutputStream out = new DataOutputStream(client.getOutputStream());
//        System.out.println("Enter you name here");
//        Scanner scanner = new Scanner(System.in);
//        String clientName = scanner.nextLine();
        // Print a message indicating successful connection
        System.out.println("The client"+"connected to server :)");

        // Input stream reader to read user input from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Start a thread to handle server responses asynchronously
        ServerHandler serverHandler = new ServerHandler(client);
        new Thread(serverHandler).start();

        String userInput;
        // Continuously read user input from the console and send it to the server
        while (true) {
            userInput = reader.readLine();
            out.writeUTF(userInput);
        }
    }
}
