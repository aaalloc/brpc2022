package socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server{

    private ServerSocket server = null;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * Create a Server socket with a port
     * @param port : Port of the server.
     */
    public Server(int port)
    {
        try{
            server =  new ServerSocket(port);
        } catch (Exception e){
            e.getStackTrace();
        }
    }

    /**
     * Wait a  connection from a client.
     * If a connection occurs, send the socket
     * corresponding to the current connection
     */
    public Socket waitConnection() {
        Socket client;
        try {
            //wait connection from the python script
            client = server.accept();
            System.out.printf("Connection from %s on %d port\n%n", client.getInetAddress().getHostAddress(), client.getLocalPort());
        } catch (IOException e) {
            if(server.isClosed()) {
                System.out.println("Server Stopped.");
                return null;
            }
            throw new RuntimeException("Error accepting client connection", e);
        }
        return client;
    }


    public void run() {
        Socket client;
        while ((client = waitConnection()) != null){
            this.threadPool.execute(new ClientWorker(client, this.server));
        }
        this.threadPool.shutdownNow();
        System.out.println("END SERVER");
    }
}
