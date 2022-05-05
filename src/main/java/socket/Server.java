package socket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Server {
    private boolean onRun;
    public final int port = 8080;
    private final ServerSocket server;


    public Server() throws IOException {
        server =  new ServerSocket(port);
    }

    public void start() throws IOException, InterruptedException {
        onRun =true;

        //wait connection from the python script
        Socket client = server.accept();
        System.out.println("got connection on port " + client.getLocalPort() + "\n");


        while(onRun){

            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);

            String fromClient = in.readLine();
            System.out.println("received from " + client.getLocalAddress() + " : " + fromClient);
            String endOfFile = Character.toString(fromClient.charAt(fromClient.length() - 1));

            String toClient;
            if(fromClient.contains(endOfFile)){
                toClient = "closed";
            }
            else{
                toClient = "packet received";
            }
            out.println(toClient);
            if(fromClient.contains(endOfFile))
                stop(client);
        }
    }

    public void stop(Socket client) throws IOException, InterruptedException {
        client.close();
        server.close();
        System.out.println("\nserver closed");
        onRun = false;

        System.exit(0);
    }

}
