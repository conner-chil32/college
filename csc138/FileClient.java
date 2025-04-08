import java.io.*;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        String serverAddress = "192.168.1.15";
        int serverPort = 5000;

        try (Socket socket = new Socket(serverAddress, serverPort)) {
            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);

            InputStream inputStream = socket.getInputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("test.txt"));

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
            bufferedOutputStream.flush();

            System.out.println("File received successfully!");

            bufferedOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}