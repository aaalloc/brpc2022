package beepbeep.processors;

import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.Source;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;

public class SocketReader extends Source {

    private final Socket clientSocket;
    private final ServerSocket serverSocket;

    public SocketReader(Socket clientSocket, ServerSocket serverSocket) {
        super(1);
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
    }

    @Override
    protected boolean compute(Object[] inputs, Queue<Object[]> outputs) {
        String data = getData();

        if (data == null) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("No data received while computing");
        }
        outputs.add(new Object[]{data});
        return true;
    }

    private String getData() {
        BufferedReader in;
        String data = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            data = in.readLine();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return data;
    }

    @Override
    public Processor duplicate(boolean b) {
        return null;
    }
}