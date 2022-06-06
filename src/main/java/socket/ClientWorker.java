package socket;

import beepbeep.BeepBeep;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ClientWorker implements Runnable{
    private final ServerSocket serverSocket;
    private final Socket clientSocket;

    public ClientWorker(Socket clientSocket, ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    private String getData(){
        BufferedReader in;
        String data = null;
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            data = in.readLine();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return data;
    }

    @Override
    public void run() {
        BeepBeep beepBeep = new BeepBeep();
        String data;
        while((data = getData())!= null){
            beepBeep.addToQueue(data);
            beepBeep.printMaxSpeed();
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
