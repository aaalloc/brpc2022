package web;

import utilities.StreamGobbler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Let the controll of the API Endpoint made with Python.
 */
public class API  {
    Process process;

    public API(){

    }

    public void startServer() throws InterruptedException, IOException {

        ProcessBuilder builder = new ProcessBuilder();

        builder.command("cmd.exe", "/c", "endpoints --prefix=controllers --host=localhost:8000");
        builder.directory(new File("./src/main/python/src/"));

        process = builder.start();
        System.out.println(process.pid());
        /*
        StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
        Executors.newSingleThreadExecutor().submit(streamGobbler);
        */
    }

    public void stop(){
        process.destroy();
    }

}
