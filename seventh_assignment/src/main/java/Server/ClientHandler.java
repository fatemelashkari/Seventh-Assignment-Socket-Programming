package Server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable { // this class will handle the clients with threads

    private Socket socket;
    static ArrayList<ClientHandler> clientHandlers = new ArrayList<>(); // we want to have a  list of clients to know who we have to send them the messages
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String userName;

    public ClientHandler (Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            // ^^^ note about InputStream and InputStreamReader and BufferedReader ^^^
            //(stream is a sequence of data)
            //1.InputStream = the data which they are in the socket are byte stream --> getInputStream() will get the data in the byte stream format
            //2.InputStreamReader = by doing this : InputStreamReader(socket.getInputStream()) we are convert the byte stream to char stream because we are sending messages and this format is better than byte stream
            //3.BufferedReader = is a wrapper class which we do this : new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())) because doing this make our code and data more efficient
            this.userName = bufferedReader.readLine();//the first thing the clients should write is their username before connecting the server they would enter their username so the first line which is written inside the buffer is the clients' username
            clientHandlers.add(this);//this refers to this object of ClientHandler which we inside it right now
            sendMessages(userName + " : Entered The Chat");//when we call the client handler class this message will be shown
        }catch (IOException e) {
            closeAll(socket , bufferedWriter , bufferedReader);
        }
    }

    @Override
    public void run() {

    }

    public void sendMessages (String message) {
        try {
            for (ClientHandler clientHandler : clientHandlers) {
                if (!clientHandler.userName.equals(userName)) {
                    clientHandler.bufferedWriter.write(message); // in this line we are write the message inside the buffer
                    clientHandler.bufferedWriter.newLine(); // in this line we rae making a new line for more efficiency of our code
                    clientHandler.bufferedWriter.flush(); // in this line we used from flush() method
                    // note about flush method :
                    // usually when we write sth inside the buffer it won't happen immediately, and it may be lost in there, so we use from the flush method because we want to ensure ourselves that it will be written immediately
                    // why we have to ensure ourselves that the message is written immediately ? because we are sending messages inside an infinity loop, so we have to be sure the previous message positively is written
                }
            }
        }catch (IOException e) {
            closeAll(socket , bufferedWriter , bufferedReader);
        }
    }
    public void closeAll(Socket socket , BufferedWriter bufferedWriter , BufferedReader bufferedReader) {

        clientHandlers.remove(this);
        sendMessages(userName + "Left The Chat!!");
        try {
            if (socket != null) { //we have to check this always that they won't be null because if they were null and then w close them we will get a null pointer exception
                socket.close();
            }
            if (bufferedReader != null) { //we have to check this always that they won't be null because if they were null and then w close them we will get a null pointer exception
                bufferedReader.close();
            }
            if (bufferedWriter != null) { //we have to check this always that they won't be null because if they were null and then w close them we will get a null pointer exception
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
