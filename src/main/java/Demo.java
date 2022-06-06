import beepbeep.BeepBeep;
import socket.Server;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) {
        Server serv = new Server(8080);
        serv.run();
    }
}
