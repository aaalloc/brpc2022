package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public boolean onRun;
    public String fromClient;
    public String toClient;
    public final int port = 8080;
    public ServerSocket server;


    public Server() throws IOException {
        server =  new ServerSocket(port);
    }

    public void start() throws IOException {
        onRun = true;
        while(onRun){
            Socket client = server.accept();
            System.out.println("got connection on port " + client.getLocalPort());
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            fromClient = in.readLine();
            System.out.println("received : " + fromClient +"\n");

            toClient = "received!";
            out.println(toClient);

            if(fromClient.equals("stop"))
                stop(client);
        }
    }

    private void stop(Socket client) throws IOException {
        client.close();
        System.out.println("closed");
        onRun = false;
        System.exit(0);
    }

}
