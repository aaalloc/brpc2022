import beepbeep.BeepBeep;
import socket.Server;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args)
    {
        Server serv = new Server(8080,"end");
        serv.waitConnection();

        BeepBeep bb = new BeepBeep();

        while(serv.dataFromClient().getClientState())
        {
            String data = serv.dataFromClient().getData();
            bb.addToQueue(data);
            bb.printMaxSpeed();
        }
        System.out.println("end of loop");
    }
}
