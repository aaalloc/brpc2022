import socket.Server;


import java.io.IOException;


public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException {
        /*
        API.start();
        System.out.println(API.fetchData(""));
        API.close();
         */
        Server server = new Server();
        server.start();
    }
}
