import socket.Server;

public class Demo { // need to change the name in the future, to abstract ...
    public static void main(String[] args){
        Server serv = new Server(8080,"end");
        serv.waitConnection();

        while(serv.dataFromClient().getClientState()){
            String data = serv.dataFromClient().getData();
            System.out.println(data);
            // do beep bepp things
            // .....

        }
        System.out.println("end of loop");
    }
}
