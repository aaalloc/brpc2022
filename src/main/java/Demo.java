import org.json.simple.parser.ParseException;
import socket.Server;


import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException {
        /*
        API.start();
        System.out.println(API.fetchData(""));
        API.close();
         */
        Server serv = new Server();
        serv.start();
    }
}
