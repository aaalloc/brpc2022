package socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import utilities.Packet;

// Accept only one connection at that moment
public class Server {
    public int port;
    private ServerSocket server = null;
    public String flagKill;
    public Socket client;
    private boolean isEndOfTransmission;

    /**
     * Create a Server socket with a port and a flag.
     * @param port : Port of the server.
     * @param flagEndTransmission : Flag that will shut down the server when the client will send a packet that will correspond to the flag.
     */
    public Server(int port,String flagEndTransmission)
    {
        try{
            server =  new ServerSocket(port);
            this.port = port;
            this.flagKill = flagEndTransmission;
        } catch (Exception e){
            e.getStackTrace();
        }
    }

    /**
     * Wait a unique connection from a client.
     */
    public void waitConnection() {
        try {
            //wait connection from the python script
            client = server.accept();
            System.out.printf("Connection from %s on %d port\n%n", client.getInetAddress().getHostAddress(), client.getLocalPort());

        } catch (Exception e){
            e.getStackTrace();
        }
    }

    /**
     * Return data received from clients and return the statement of the server, if Boolean is true,
     * the server waits other data, if it false then the server shutdown.
     * @return Sort of a tuples with a String (the data received from clients) and Boolean (If the server wait other data or not)
     */
    public Packet<String, Boolean> dataFromClient() {

        BufferedReader in;
        PrintWriter out = null;
        String fromClient = null;
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),true);

            fromClient = in.readLine();
            //System.out.println("received from " + client.getLocalAddress() + " : " + fromClient);

            //if data correspond to flagKILL, then server should stop
            isEndOfTransmission = fromClient.equals(flagKill);
        } catch (Exception e){
            e.getStackTrace();
        }
        return new Packet<>(fromClient, answerClient(out));
    }

    private boolean answerClient(PrintWriter out){
        String toClient;
        if(isEndOfTransmission){
            toClient = "closed";
            out.println(toClient);
            try {
                stopServer();
                return false;
            } catch (Exception e){
                e.getStackTrace();
            }
        }
        toClient = "packet received from server";
        out.println(toClient);
        return true;
    }

    public void stopServer() {
        try {
            server.close();
        } catch (Exception e){
            e.getStackTrace();
        }
        System.out.println("\nserver closed");
    }
}
