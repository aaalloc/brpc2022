import socket.Server;


import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException, InterruptedException {
        /*
        API.start();
        System.out.println(API.fetchData(""));
        API.close();
         */
        Server.start();
    }
}
