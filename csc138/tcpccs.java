// CLIENT

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
                        File file = new File(splitStr[2]);

                        System.out.println("[" + username + "] " + splitStr[0] + " " + splitStr[1] + " " + splitStr[2] + " " + file.length() + "B");
                        
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
        private ServerSocket serverSocket;
        private Socket clientSocket;
        private int targetPort;

        public FileTransfer(String inputPacket) {
            String[] inputStr = inputPacket.split(",");
            targetPort = Integer.valueOf(inputStr[2]);
        }

        @Override
        public void run() {
            running = true;

        }
        
    }

}


