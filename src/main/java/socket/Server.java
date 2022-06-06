package socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Accept only one connection at that moment
public class Server implements Runnable{

    private ServerSocket server = null;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);
    protected Thread runningThread= null;

    /**
     * Create a Server socket with a port and a flag.
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
     * If a connection occurs, send the socket corresponding to the current connection
     */
    public Socket waitConnection() {
        Socket client;
        try {
            //wait connection from the python script
            client = server.accept();
            System.out.printf("Connection from %s on %d port\n%n", client.getInetAddress().getHostAddress(), client.getLocalPort());
        } catch (IOException e) {
            if(isClosed()) {
                System.out.println("Server Stopped.");
                return null;
            }
            throw new RuntimeException("Error accepting client connection", e);
        }
        return client;
    }

    private synchronized boolean isClosed(){
        return server.isClosed();
    }

    @Override
    public void run() {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        Socket client;
        while ((client = waitConnection()) != null){
            this.threadPool.execute(new WorkerBeepBeep(client, this.server));
        }
        this.threadPool.shutdownNow();
        System.out.println("END SERVER");
    }
}
