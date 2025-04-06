// CLIENT

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.http.WebSocket.Listener;
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
            //FileTransfer ft = new FileTransfer();

            Thread userInputThread = new Thread(ui);
            Thread messageListenerThread = new Thread(ml);
            //Thread fileTransferThread = new Thread(ft)

            userInputThread.start();
            messageListenerThread.start();
            //fileTransferThread.start();
            
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
                    System.out.println(msg);
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
                Scanner scanner = new Scanner(System.in);
                while(socket.isConnected()) {
                    String msg = scanner.nextLine();
                    bufferedWriter.write("[" + username + "] " + msg);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch(IOException e) {
                close(socket, bufferedReader, bufferedWriter);
            }
        }

    }

    public static class FileTransfer implements Runnable {

        @Override
        public void run() {

        }
        
    }

}


