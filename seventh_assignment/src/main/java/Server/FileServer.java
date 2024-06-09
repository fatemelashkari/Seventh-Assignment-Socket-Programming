package Server;

import javax.management.remote.JMXConnectorFactory;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.net.JarURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileServer {
    static ArrayList<MyFiles> myFiles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int fileId = 0;

        JFrame jFrame = new JFrame("Server");
        jFrame.setSize(500 , 500);
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane() , BoxLayout.Y_AXIS));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel , BoxLayout.Y_AXIS));

        JScrollPane jScrollPane = new JScrollPane(jPanel);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel jLabel = new JLabel("File Receiver");
        jLabel.setFont(new Font("Arial" , Font.BOLD , 20));
        jLabel.setBorder(new EmptyBorder(20 , 0 , 10 , 0));
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        jFrame.add(jLabel);
        jFrame.add(jScrollPane);
        jFrame.setVisible(true);

        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                int fileNameLength = dataInputStream.readInt();

                if (fileNameLength > 0) {
                    byte[] fileNameByte = new byte[fileNameLength];
                    dataInputStream.readFully(fileNameByte , 0 , fileNameByte.length);
                    String fileName = new String(fileNameByte);
                    int file = dataInputStream.readInt();

                    if (file > 0) {
                        byte[] fileBytes = new byte[file];
                        dataInputStream.readFully(fileBytes , 0 , file);

                        JPanel jPanel1 = new JPanel();
                        jPanel1.setLayout(new BoxLayout(jPanel1 , BoxLayout.Y_AXIS));

                        JLabel jLabel1 = new JLabel(fileName);
                        jLabel1.setFont(new Font("Arial" , Font.BOLD , 18));
                        jLabel1.setBorder(new EmptyBorder(10 , 0 , 10 , 0));

                        if (getFile(fileName).equalsIgnoreCase("txt")) {
                            jPanel1.setName(String.valueOf(fileId));
                            jPanel1.addMouseListener(getMouseListener());

                            jPanel1.add(jLabel1);
                            jPanel.add(jPanel1);
                            jFrame.validate();
                        }
                        else {
                            jPanel1.setName(String.valueOf(fileId));
                            jPanel1.addMouseListener(getMouseListener());
                            jPanel1.add(jLabel1);
                            jPanel.add(jPanel1);
                            jFrame.validate();
                        }
                    }
                    myFiles.add(new MyFiles(fileId , fileName , fileNameByte , getFile(fileName)));
                }

            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static JFrame createFrame (String fileName , byte[] fileData , String file) {
        JFrame jFrame = new JFrame("File DownLoader");
        jFrame.setSize(500 , 500);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel , BoxLayout.Y_AXIS));

        JLabel jLabel = new JLabel("File Downloader");
        jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel.setFont(new Font("Arial" , Font.BOLD , 20));
        jLabel.setBorder(new EmptyBorder(20 , 0 , 10 , 0));

        JLabel jLabel2 = new JLabel("Do you want to download : " + fileName);
        jLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel2.setFont(new Font("Arial" , Font.BOLD , 20));
        jLabel2.setBorder(new EmptyBorder(20 , 0 , 10 , 0));


        JButton jButton = new JButton("Yes");
        jButton.setPreferredSize(new Dimension(100 , 50));
        jButton.setFont(new Font("Arial" , Font.BOLD , 18));

        JButton jButton2 = new JButton("No");
        jButton2.setPreferredSize(new Dimension(100 , 50));
        jButton2.setFont(new Font("Arial" , Font.BOLD , 18));

        JLabel jLabel1 = new JLabel();
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel jPanel1 = new JPanel();
        jPanel1.setBorder(new EmptyBorder(20 , 0 , 10 , 0));
        jPanel1.add(jButton);
        jPanel1.add(jButton2);

        if (file.equalsIgnoreCase("txt")) {
            jLabel1.setText("<html>" + new String(fileData) + "</html>");
        }
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file1 = new File(fileName);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file1);
                    fileOutputStream.write(fileData);
                    fileOutputStream.close();
                    jFrame.dispose();
                }catch (IOException error) {
                    error.printStackTrace();
                }
            }
        });

        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jFrame.dispose();
            }
        });
        jPanel.add(jLabel);
        jPanel.add(jLabel2);
        jPanel.add(jLabel1);
        jPanel.add(jPanel1);
        jFrame.add(jPanel);
        return jFrame;
    }
    public static MouseListener getMouseListener() {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JPanel jPanel =(JPanel) e.getSource();
                int fileId = Integer.parseInt(jPanel.getName());
                for (MyFiles myFiles1 : myFiles) {
                    if (myFiles1.getId() == fileId) {
                        JFrame jFrame = createFrame(myFiles1.getName() , myFiles1.getData() , myFiles1.getFile());
                        jFrame.setVisible(true);

                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        };
    }
    public static String getFile(String fileName) {
        int x = fileName.lastIndexOf(".");
        if (x > 0) {
            return fileName.substring(x + 1);
        }
        else {
            return "Not Found";
        }
    }
}
