package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.AbstractMap;
// Accept only one connection at that moment
public class Server {
    public final int port = 8080;
    private final ServerSocket server;
    public String flagKILL;
    public Socket client;
    private boolean isEndOfTransmission;
    public Server(String flagEndTransmission) throws IOException
    {
        this.flagKILL = flagEndTransmission;
        server =  new ServerSocket(port);
    }

    public void waitConnection() throws IOException {
        //wait connection from the python script
        client = server.accept();
        System.out.println("got connection on port " + client.getLocalPort() + "\n");
    }

    /**
     * Return data received from clients and return the statement of the server, if Boolean is true,
     * the server waits other data, if it false then the server shutdown.
     * @return Sort of a tuples with a String (the data received from clients) and Boolean (If the server wait other data or not)
     */
    public AbstractMap.SimpleEntry<String, Boolean> receivedData() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter out = new PrintWriter(client.getOutputStream(),true);

        String fromClient = in.readLine();
        System.out.println("received from " + client.getLocalAddress() + " : " + fromClient);

        //if data correspond to flagKILL, then server should stop
        isEndOfTransmission = fromClient.equals(flagKILL);
        return new AbstractMap.SimpleEntry<>(fromClient, responseToClient(out));
    }

    private boolean responseToClient(PrintWriter out){
        String toClient;
        if(isEndOfTransmission){
            toClient = "closed";
            out.println(toClient);
            try {
                stop();
                return false;
            }
            catch (Exception e){
                e.getStackTrace();
            }
        }
        toClient = "packet received from server";
        out.println(toClient);
        return true;
    }

    public void stop() throws IOException {
        server.close();
        System.out.println("\nserver closed");
    }

}
