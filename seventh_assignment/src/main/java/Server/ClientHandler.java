package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable { // this class will handle the clients with threads
    private Socket socket;
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String userName;
    public ClientHandler (Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {

    }
}
