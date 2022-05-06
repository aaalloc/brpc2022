import socket.Server;
import java.io.IOException;



public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args) throws IOException {
        Server serv = new Server("end");
        serv.waitConnection();

        Boolean isFinished = true;

        while(isFinished){
            String data = serv.receivedData().getKey();
            System.out.println(data);
            // do beep bepp things
            // .....

            isFinished = serv.receivedData().getValue();
        }

        System.out.println("end of loop");
    }
}
