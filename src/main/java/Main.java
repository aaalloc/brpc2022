import socket.Server;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: main <port> <interval>");
        }

        int port;
        long interval;

        try {
            port = Integer.parseInt(args[0]); //Use 8080 by default
            interval = Long.parseLong(args[1]);
            Server serv = new Server(port, interval);
            serv.run();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Couldn't parse arguments. Usage: main <port> <interval>");
        }
    }
}
