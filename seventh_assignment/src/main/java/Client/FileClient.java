package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.JarURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;
    private static final String DIRECTORY_PATH = "C:\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\java\\Client\\files";

    private static final String DOWNLOAD_DIRECTORY_PATH = "C:\\Seventh-Assignment-Socket-Programming\\seventh_assignment\\src\\main\\files-destination";  // Specify your download directory here

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Download File");
        jFrame.setSize(900, 900);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel jLabel = new JLabel("Available Text Files");
        jLabel.setFont(new Font("Arial", Font.BOLD, 18));
        jLabel.setBorder(new EmptyBorder(40, 0, 0, 0));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel filePanel = new JPanel();
        filePanel.setBorder(new EmptyBorder(70, 0, 10, 0));
        filePanel.setLayout(new GridLayout(0, 1, 10, 10));

        File[] files = getTextFilesInDirectory(DIRECTORY_PATH);
        for (File file : files) {
            JButton downloadButton = new JButton("Download " + file.getName());
            downloadButton.setPreferredSize(new Dimension(300, 50));
            downloadButton.setFont(new Font("Arial", Font.BOLD, 18));
            downloadButton.addActionListener(new DownloadButtonListener(file));
            filePanel.add(downloadButton);
        }

        jFrame.add(jLabel);
        jFrame.add(filePanel);
        jFrame.setVisible(true);
    }

    private static File[] getTextFilesInDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            return Files.list(path)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .map(Path::toFile)
                    .toArray(File[]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new File[0];
        }
    }

    private static class DownloadButtonListener implements ActionListener {
        private final File file;

        public DownloadButtonListener(File file) {
            this.file = file;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);

                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                String fileName = file.getName();
                byte[] fileNameBytes = fileName.getBytes();

                byte[] fileBytes = new byte[(int) file.length()];
                fileInputStream.read(fileBytes);

                dataOutputStream.writeInt(fileNameBytes.length);
                dataOutputStream.write(fileNameBytes);

                dataOutputStream.writeInt(fileBytes.length);
                dataOutputStream.write(fileBytes);

                fileInputStream.close();
                socket.close();

                // Save the file to the specified download directory
                File downloadFile = new File(DOWNLOAD_DIRECTORY_PATH + File.separator + fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);
                fileOutputStream.write(fileBytes);
                fileOutputStream.close();

                JOptionPane.showMessageDialog(null, "Text file " + fileName + " has been downloaded in : " + DOWNLOAD_DIRECTORY_PATH);
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }
}

