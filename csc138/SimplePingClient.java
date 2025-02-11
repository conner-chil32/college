import java.net.InetAddress;

// Written By: Conner Childers, 2/11/2025

public class SimplePingClient {
    public static void main(String[] args) throws Exception{
        if(args.length <= 0) {
            System.out.println("Usage: java SimplePingClient <host>");
            return;
        }
        try {
            InetAddress address = InetAddress.getByName(args[0]);
            boolean reachable = address.isReachable(5000);
            if(reachable) {
                System.out.println(args[0] + " is reachable");
            } else {
                System.out.println(args[0] + " is not reachable");
            }
        } catch (Exception e) {
            System.out.println(args[0] + " is not reachable");
        }
    }
}