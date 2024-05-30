package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Server Class
public class Server {
    // TODO: Implement the server-side operations

    // TODO: Add constructor and necessary methods

    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the server
        Socket socket = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(1234);
        while (true) {
            try {
                socket = serverSocket.accept();
                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedReader = new BufferedReader(inputStreamReader);
                 while (true) {
                     String message = bufferedReader.readLine();
                     System.out.println("The Client said : " + message);
                     if (message.equals("Quit")) {
                         break;
                     }
                 }
                socket.close();
                bufferedWriter.close();
                bufferedReader.close();
                inputStreamReader.close();
                outputStreamWriter.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}