package Client;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private String userName;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    //-----------------------------------------Constructor--------------------------------------------------
    public Client (Socket socket , String userName) {
        try {
            this.socket = socket;
            this.userName = userName;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); //I've talked about these things in the ClientHandler Class completely :)
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (IOException e) {
            closeAll(socket , bufferedWriter , bufferedReader);
        }
    }
    //-----------------------------------------Constructor--------------------------------------------------


    //-----------------------------------------To Send Messages-------------------------------------------
    public void sendMessages() {
        try {
            bufferedWriter.write(userName); // each client when connect will enter her/his username then we are writing their userName here
            bufferedWriter.newLine();
            bufferedWriter.flush();
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(userName + " : " + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        }catch (IOException e) {
            closeAll(socket , bufferedWriter , bufferedReader);
        }
    }
    //-----------------------------------------To Send Messages-------------------------------------------


    //-----------------------------------------To Wait For New Message-------------------------------------------
    public void waiting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String message;
                while (socket.isConnected()) {
                    try {
                        message = bufferedReader.readLine();
                        System.out.println(message);
                    }catch (IOException e) {
                        closeAll(socket , bufferedWriter , bufferedReader);
                    }
                }
            }
        }).start();
    }
    //-----------------------------------------To Wait For New Message-------------------------------------------


    //-----------------------------------------To Close EveryThing-------------------------------------------
    public void closeAll(Socket socket , BufferedWriter bufferedWriter , BufferedReader bufferedReader) {
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
    //-----------------------------------------To Close EveryThing-------------------------------------------


    //-------------------------------------------------Menu----------------------------------------------------
    public void menu() {

    }
    //-------------------------------------------------Menu----------------------------------------------------


    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Your UserName Here, Please : ");
        String userName = scanner.nextLine();
        Socket socket1 = new Socket("localhost" ,1234);
        Client client = new Client(socket1 , userName);
        client.waiting();
        client.sendMessages();
    }
}
