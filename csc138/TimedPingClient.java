import java.net.*;

// Written By: Conner Childers, 2/11/2025

public class TimedPingClient {
    public static void main(String[] args) throws Exception {
        if(args.length <= 0) {
            System.out.println("Usage: java TimedPingClient <host>");
            return;
        }
        try {
            DatagramSocket socket = new DatagramSocket();
            long start = System.currentTimeMillis();
            InetAddress address = InetAddress.getByName(args[0]);
            boolean reachable = address.isReachable(5000);
            byte[] data = "test".getBytes();

            if(reachable) {
                DatagramPacket packet = new DatagramPacket(data, data.length, address, 53);
                socket.send(packet);
                socket.close();
                long end = System.currentTimeMillis();
                System.out.println(args[0] + " is reachable. RTT: " + (end-start) + "ms");
            } else {
                socket.close();
                System.out.println(args[0] + " is not reachable");
            }
        } catch (Exception e) {
            System.out.println(args[0] + " is not reachable");
        }
    }
}