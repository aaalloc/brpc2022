import socket.Server;

import java.io.IOException;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args)
    {
        Server serv = new Server(8080,"end");
        serv.waitConnection();

        BeepBeep bb = new BeepBeep();

        while(serv.dataFromClient().getClientState())
        {
            String data = serv.dataFromClient().getData();
            // do beep bepp things
            // .....

            bb.addToQueue(data);
            bb.process();
        }
        bb.setProcessing(false);
        System.out.println("end of loop");
    }
}
