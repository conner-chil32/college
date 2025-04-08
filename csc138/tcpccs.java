// CLIENT

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

// Written By: Conner Childers, 4/5/2025

public class tcpccs {

    private static Socket socket;
    private static BufferedReader bufferedReader;
    private static BufferedWriter bufferedWriter;
    private static String username;
    private static File file;
    
    public static void main(String[] args) {
        if(args.length <= 0) {
            System.out.println("Usage: java tcpccs.java <hostname> <username>");
            return;
        }

        try {
            socket = new Socket(args[0], 12345);
            username = args[1];
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            System.out.println("Connected to the server. You can start sending messages.");

            UserInput ui = new UserInput();
            MessageListener ml = new MessageListener();

            Thread userInputThread = new Thread(ui);
            Thread messageListenerThread = new Thread(ml);

            userInputThread.start();
            messageListenerThread.start();
            
        } catch(IOException e) {
            close(socket, bufferedReader, bufferedWriter);
        }
    }

    private static void close(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static class MessageListener implements Runnable {

        @Override
        public void run() {
            String msg;

            while(socket.isConnected()) {
                try {
                    msg = bufferedReader.readLine();
                    if(msg.contains("[adr]")) { // start file transfer thread
                        FileTransfer ft = new FileTransfer(msg);
                        Thread fileTransferThread = new Thread(ft);
                        fileTransferThread.start();
                    } else {
                        System.out.println(msg);
                    }
                } catch(IOException e) {
                    close(socket, bufferedReader, bufferedWriter);
                }
            }
        }
        
    }

    public static class UserInput implements Runnable {
        @Override
        public void run() {
           try {
                while(socket.isConnected()) {
                    Scanner scanner = new Scanner(System.in);
                    String msg = scanner.nextLine();
                    if(msg.contains("/quit")) {
                        close(socket, bufferedReader, bufferedWriter);
                    }
                    else if(msg.contains("/sendfile")) {
                        String[] splitStr = msg.split("\\s+");
                        file = new File(splitStr[2]);
                        
                        bufferedWriter.write("[" + username + "] " + splitStr[0] + " " + splitStr[1] + " " + splitStr[2] + " " + file.length() + "B");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } else {
                        bufferedWriter.write("[" + username + "] " + msg);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                }
            } catch(IOException e) {
                close(socket, bufferedReader, bufferedWriter);
            }
        }

    }

    public static class FileTransfer implements Runnable {
        public static boolean running;
        private FileInputStream fileInputStream;
        private ServerSocket serverSocket;
        private Socket clientSocket;
        private boolean isSender;

        private String serverIP;
        private int port; 

        private String filename;
        

        public FileTransfer(String inputPacket) {
            System.out.println(inputPacket);
            String[] inputStrings = inputPacket.split(",");
            serverIP = inputStrings[1];
            port = Integer.valueOf(inputStrings[2]);
            if(inputStrings[3].contains("[snd]")) {
                isSender = false;
            } else {
                isSender = true;
            }
            //filename = inputStrings[4];

        }

        @Override
        public void run() {
            running = true; 
            try {
                if(!isSender) {
                    
                    serverSocket = new ServerSocket(port);
                    clientSocket = serverSocket.accept();

                    InputStream inputStream = clientSocket.getInputStream();
                    FileOutputStream fileOutputStream = new FileOutputStream("test.txt");

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer,0,bytesRead);
                    }
                    
                    fileOutputStream.close();
                    inputStream.close();
                    clientSocket.close();
                    serverSocket.close();
                } else {
                    
                    clientSocket = new Socket(serverIP, port);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    OutputStream outputStream = clientSocket.getOutputStream();

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while((bytesRead = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    fileInputStream.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                close(clientSocket, bufferedReader, bufferedWriter);
            }

        }
        
    }

}


