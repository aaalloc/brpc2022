import beepbeep.BeepBeep;
import socket.Server;
import socket.WorkerBeepBeep;

import java.util.List;
import java.util.concurrent.*;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) {
        Server serv = new Server(8080);
        serv.run();
    }
}
