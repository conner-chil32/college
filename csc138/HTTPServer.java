import java.io.*;
import java.net.*;

public class HTTPServer {
    public static void main(String[] args) {
        // Set the port number
        int port = 6789;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server running on port " + port);
            
            // Infinite loop to listen for incoming connections
            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                // Create a new thread to handle the request
                HttpRequestHandler requestHandler = new HttpRequestHandler(clientSocket);
                Thread thread = new Thread(requestHandler);
                thread.start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class HttpRequestHandler implements Runnable {
    private final Socket socket;

    public HttpRequestHandler(Socket socket) {
        this.socket = socket;
    }

    /*
     * IMPORTANT: Do NOT use BufferedReader or other character-based readers here.   
     * BufferedReader reads ahead and buffers data internally, which can cause
     * POST body reads to fail or hang. All header and body parsing must be done
     * using byte-level reads from InputStream to maintain precise control.
     */

    @Override
    public void run() {
        try (
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(os)
        ) {
            // Buffer to collect incoming header bytes
            ByteArrayOutputStream headerBuffer = new ByteArrayOutputStream();

            int prev = 0, curr;

            // Loop reads byte-by-byte until it detects the end of headers (\r\n\r\n)
            while ((curr = is.read()) != -1) {
                headerBuffer.write(curr);

                // Detect CRLF CRLF sequence by checking the last 4 bytes
                int len = headerBuffer.size();
                if (prev == '\r' && curr == '\n' && len >= 4) {
                    byte[] b = headerBuffer.toByteArray();
                    if (b[len - 4] == '\r' && b[len - 3] == '\n') {
                        break; // End of headers
                    }

                }
                prev = curr;
            }

            // Convert collected header bytes to a String
            String headers = headerBuffer.toString();
            System.out.println("Headers:\n" + headers);

            if (headers.contains("GET /")) {                                        // handling GET requests
                // parsing filepath from header
                String filePath = headers.substring(headers.indexOf("GET"), headers.indexOf("HTTP")).replace("GET /", "");

                // handling filepath with GET request
                try (FileInputStream fStream = new FileInputStream(new File(filePath))) {
                    out.writeBytes("HTTP/1.1 200 OK\r\n");                              // Status line
                    out.writeBytes("Content-Type: text/html\r\n");                      // MIME type
                    out.writeBytes("\r\n");                                             // End of headers
                    out.write(fStream.readAllBytes());                                    // Response body
                    fStream.close();
                } catch (FileNotFoundException e) {                                       // handling 404 error

                    // 404 error HTML code for a simple 404 message
                    String error = "<div id=\"main\">\r\n" + //
                                                "    \t<div class=\"fof\">\r\n" + //
                                                "          <h1>Error 404</h1>\r\n" + //
                                                "          <p>File Not Found</p>\r\n" + //
                                                "    \t</div>\r\n" + //
                                                "</div>";

                    out.writeBytes("HTTP/1.1 404 Not Found\r\n");                       // Status line
                    out.writeBytes("Content-Type: text/html\r\n");                      // MIME type
                    out.writeBytes("\r\n");                                             // End of headers
                    out.writeBytes(error);
                }
            } else if (headers.contains("POST")) {                                    // handling POST request
                String type = headers.substring(headers.indexOf("Content-Type:"), headers.indexOf("Content-Length")).replace("Content-Type: ", "");
                String[] indexes = headers.substring(headers.indexOf("Content-Length:")).split("\r\n");
                int length = Integer.parseInt(indexes[0].replace("Content-Length: ", ""));

                if(length == 0 || !headers.contains("Content-Length")) {   // no body is entered with post request or Content-Length is malformed
                    out.writeBytes("HTTP/1.1 400 Bad Request\r\n");
                } else {
                    ByteArrayOutputStream bodyBuffer = new ByteArrayOutputStream();
                    int data;
                    while((data = is.read()) != -1) {
                        bodyBuffer.write(data);
                        int len = bodyBuffer.size();

                        if(len >= length) {
                            break;
                        }
                    }
                    String body = bodyBuffer.toString().replace("\n", "");
                    System.out.println("Body:\n" + body);

                    out.writeBytes("HTTP/1.1 200 OK\r\n");                                  // Status line
                    out.writeBytes("Content-Type: " + type + "\r\n");                          // MIME type
                    out.writeBytes("\r\n");                                                 // End of headers
                    out.writeBytes(body);                                                     // body of the POST request
                }
            } else {                                                                      // handling 405 error
                // basic 405 error html for non supported methods
                String error = "<div id=\"main\">\r\n" + //
                                        "    \t<div class=\"fof\">\r\n" + //
                                        "          <h1>Error 405</h1>\r\n" + //
                                        "          <p>Method Not Allowed</p>\r\n" + //
                                        "    \t</div>\r\n" + //
                                        "</div>";

                out.writeBytes("HTTP/1.1 405 Method Not Allowed\r\n");                  // Status line
                out.writeBytes("Content-Type: text/html\r\n");                          // MIME type
                out.writeBytes("\r\n");                                                 // End of headers
                out.writeBytes(error);                                                    // Response body
            }

        } catch (IOException e) {
            // Log any exceptions thrown during request handling
            e.printStackTrace();
        } finally {
            // Always close the socket at the end of processing
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
