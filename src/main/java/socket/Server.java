package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class Server {
    private static boolean onRun;
    public static final int port = 8080;
    private static ServerSocket server;


    public Server() throws IOException {
        server =  new ServerSocket(port);
    }

    public static void start() throws IOException {
        onRun = true;

        //wait connection from the python script
        Socket client = server.accept();
        System.out.println("got connection on port " + client.getLocalPort() + "\n");


        while(onRun){
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            String fromClient = in.readLine();
            System.out.println("received from " + client.getLocalAddress() + " : " + fromClient);

            String toClient;
            if(fromClient.equals("stop")){
                toClient = "closed";
            }
            else{
                toClient = "packet received";
            }
            out.println(toClient);

            if(fromClient.equals("stop"))
                stop(client);
        }
    }

    public static void stop(Socket client) throws IOException {
        client.close();
        server.close();
        System.out.println("\nserver closed");
        onRun = false;
        System.exit(0);
    }

}
