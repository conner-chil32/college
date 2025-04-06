// SERVER

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// Written By: Conner Childers, 4/5/2025


public class tcpcss {

    private static ServerSocket serverSocket;
    private static ArrayList<Thread> threads = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port = args.length == 0 ? 12345 : Integer.valueOf(args[0]);
        serverSocket = new ServerSocket(port);
        System.out.println("Listener on port 12345\n" + "Waiting for connections...");
        try {
            while(!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("New connection, thread name is Thread-" + threads.size() + ", ip is: " + socket.getInetAddress() + ", port is: " + socket.getPort());
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch(IOException e) {

        }
    }

    public void StopServer() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static class ClientHandler implements Runnable {

        public static ArrayList<ClientHandler> clients = new ArrayList<>();
        private Socket socket;
        private BufferedReader bufferedReader;
        private BufferedWriter bufferedWriter;
        private String clientName;

        public ClientHandler(Socket socket) {
            try {
                this.socket = socket;
                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.clientName = bufferedReader.readLine();
                System.out.println("Adding to list of sockets at " + clients.size());
                clients.add(this);
                broadcast("[" + clientName + "] has joined the chat.");
            } catch(IOException e) {
                close(socket, bufferedReader, bufferedWriter);
            }
        }

        @Override
        public void run() {
            String msg;
            while(socket.isConnected()) {
                try {
                    msg = bufferedReader.readLine();
                    // general message & command handling
                    if(msg.contains("/sendfile")) {         // send file
                        String[] inputStrings = msg.split("\\s+");
                        if(inputStrings.length != 4) {
                            bufferedWriter.write("[Invalid Command | Usage: /sendfile <user> <filename>]");
                        }
                        String originUser = inputStrings[0].replaceAll("\\[|\\]", "");
                        String targetUser = inputStrings[2];
                        String filePath = inputStrings[3];
                    }
                    if(msg.contains("/acceptfile")) {       // accept file
                        String[] inputStrings = msg.split("\\s+");
                        if(inputStrings.length != 2) {
                            bufferedWriter.write("[Invalid Command | Usage: /acceptfile <user>]");
                        }
                        String originUser = inputStrings[0].replaceAll("\\[|\\]", "");
                        String targetUser = inputStrings[1];
                    }
                    if(msg.contains("/rejectfile")) {       // reject file
                        String[] inputStrings = msg.split("\\s+");
                        if(inputStrings.length != 2) {
                            bufferedWriter.write("[Invalid Command | Usage: /rejectfile <user>]");
                        }
                        String originUser = inputStrings[0].replaceAll("\\[|\\]", "");
                        String targetUser = inputStrings[1];
                    }
                    if(msg.contains("/who")) {              // who
                        bufferedWriter.write("[");
                        for (ClientHandler client : clients) {
                            bufferedWriter.write(client.clientName);
                            if(client != clients.get(clients.size()-1)) {
                                bufferedWriter.write(",");
                            }
                        }
                        bufferedWriter.write("]");
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                    if(msg.contains("/quit")) {             // quit
                        close(socket, bufferedReader, bufferedWriter);
                    } 
                    else {                                    // broadcast
                        broadcast(msg);
                    }
                } catch(IOException e) {
                    close(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }

        public void broadcast(String msg) {
            System.out.println(msg);
            for (ClientHandler clientHandler : clients) {
                try {
                    if(clientHandler.clientName != clientName) {
                        clientHandler.bufferedWriter.write(msg);
                        clientHandler.bufferedWriter.newLine();
                        clientHandler.bufferedWriter.flush();
                    }
                } catch(IOException e) {
                    close(socket, bufferedReader, bufferedWriter);
                }
            }
        }

        public void removeClientHandler() {
            clients.remove(this);
            broadcast("["+clientName+"] has left the chat.");
        }

        public void close(Socket socket, BufferedReader reader, BufferedWriter writer) {
            removeClientHandler();
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
        
    }
    
}
