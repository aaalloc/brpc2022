package beepbeep.processors;

import beepbeep.BeepBeep;
import beepbeep.processors.groupprocessors.getmax.GetMaxValueFromString;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.Pushable;
import ca.uqac.lif.cep.tmf.Pump;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketPump extends Pump {

    private final ServerSocket serverSocket;
    private final Socket clientSocket;

    public SocketPump(long interval, Socket clientSocket, ServerSocket serverSocket) {
        super(interval);
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        boolean m_run = true;
        Pushable pushable = this.getPushableOutput(0);

        String data;
        while(m_run && (data = getData()) != null) {
            pushable.push(data);
            System.out.println("*************************************");
            if (this.m_interval >= 0L) {
                try {
                    Thread.sleep(this.m_interval);
                } catch (InterruptedException var5) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            e.getStackTrace();
        }

        pushable.notifyEndOfTrace();
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



}
