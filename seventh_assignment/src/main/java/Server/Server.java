package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebSocket server implementation.
 */
public class Server {
    private ServerSocket serverSocket;
    public Server(ServerSocket serverSocket) { //the constructor
        this.serverSocket = serverSocket;
    }

    public void runServer() {
        try{
            Socket socket = serverSocket.accept(); // this is waiting for new connection from clients
            System.out.println("A New Client Connected Successfully!!"); // because if the code pass the last line(serversocket.accept) it means that a new client has connected to the server
            ClientHandler clientHandler = new ClientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }catch(IOException e) {
            close(serverSocket);
        }
    }

    public void close(ServerSocket serverSocket) {
        try {
            if (serverSocket != null) { //we have to check if the server socket is null or not  because if the server socket was null when we want to close it we will get a null pointer error for this reason we put this part inside the try-catch block
                serverSocket.close();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket1 = new ServerSocket(1234);
        Server server = new Server(serverSocket1);
        server.runServer();
    }

}
