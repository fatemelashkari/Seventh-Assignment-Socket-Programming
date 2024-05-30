package Client;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

// Client Class
public class Client {
    // TODO: Implement the client-side operations

    // TODO: Add constructor and necessary methods

    public static void main(String[] args) throws IOException {
        // TODO: Implement the main method to start the client
        Socket socket = null;
        BufferedWriter bufferedwriter = null;
        BufferedReader bufferedreader = null;
        OutputStreamWriter outputstreamWriter = null;
        InputStreamReader inputStreamReader = null;
        try{
            socket = new Socket("localhost" , 1234);//we have to pass two essential things to the Socket : 1.IP address 2. TCP port
            inputStreamReader = new InputStreamReader(socket.getInputStream()); // read from the server
            outputstreamWriter = new OutputStreamWriter(socket.getOutputStream()); // output from the server
            bufferedreader = new BufferedReader(inputStreamReader);
            bufferedwriter = new BufferedWriter(outputstreamWriter);
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Type Your Message Here : ");
                String message = scanner.nextLine();
                bufferedwriter.write(message);
                bufferedwriter.newLine();
                bufferedwriter.flush();
                System.out.println("The answer of the server : " + bufferedreader.readLine());
                System.out.println("If you want to leave type quit");
                if (message.equals("QUIT") || message.equals("quit")) {
                    break;
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (socket != null) {
                socket.close();
            }
            if (bufferedwriter != null) {
                bufferedwriter.close();
            }
            if (bufferedreader != null) {
                bufferedreader.close();
            }
            if (outputstreamWriter != null) {
                outputstreamWriter.close();
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

        }
    }
}