package web;

import utilities.StreamGobbler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

/**
 * Let the control of the API Endpoints made with Python.
 */
public abstract class API  {
    public static Process process;

    /**
     * Start the server through executing a command through a terminal.
     * @throws IOException
     */
    public static void start() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", "endpoints --prefix=controllers --host=localhost:8000");
        builder.directory(new File("./src/main/python/src/"));

        process = builder.start();
        System.out.println(process.pid());
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
    }

    /**
     * Shutdown the server through a simple open connection
     */
    public static void close() {
        String url = "http://localhost:8000/exit";
        try{
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // we don't try to receive any HTTP response since the server has been shutdown.
            con.disconnect();
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
        process.destroy();
        System.exit(0);
    }


}
