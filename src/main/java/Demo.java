import ca.uqac.lif.cep.json.JPathFunction;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import socket.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException {
        Server serv = new Server(8080,"end");
        serv.waitConnection();

        BeepBeep bb = new BeepBeep();

        /*File f = new File("./src/main/java/beepbeep/data.json");
        String s = Files.readString(f.toPath());
        System.out.println(s);*/

        while(serv.dataFromClient().getClientState())
        {
            String data = serv.dataFromClient().getData();
            // do beep bepp things
            // .....

            bb.addToQueue(data);
            if (!bb.isProcessing())
                bb.startProcessing();
        }
        bb.setProcessing(false);
        System.out.println("end of loop");
    }
}
