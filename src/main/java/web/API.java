package web;

import utilities.StreamGobbler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * Shutdown the server through a simple open connection
     */
    public static void close() throws IOException {
        System.out.println(fetchData("/exit"));
        process.destroy();
        System.exit(0);
    }

    public static String fetchData(String element) throws IOException {
        String urlToRead = "http://localhost:8000" + element;
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        conn.disconnect();
        return result.toString();
    }
}
