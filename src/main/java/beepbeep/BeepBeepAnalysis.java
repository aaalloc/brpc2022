package beepbeep;

import beepbeep.processors.applyfunctions.parsers.StringToJsonMap;
import beepbeep.processors.groupprocessors.getboolean.GetBooleanFromJsonMap;
import beepbeep.processors.groupprocessors.getmax.GetMaxValueFromJsonMap;
import beepbeep.processors.groupprocessors.getmin.GetMinValueFromJsonMap;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.io.Print;
import ca.uqac.lif.cep.tmf.Fork;
import beepbeep.processors.SocketPump;

import java.net.ServerSocket;
import java.net.Socket;

public class BeepBeepAnalysis implements Runnable
{

    private final long interval;
    private final Socket clientSocket;
    private final ServerSocket serverSocket;

    public BeepBeepAnalysis(long interval, Socket clientSocket, ServerSocket serverSocket)
    {
        System.out.println("Instantiating BeepBeep process");

        this.interval = interval;
        this.clientSocket = clientSocket;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        process();
    }


    private void process()
    {
        //final QueueSource elementsToGrab = new QueueSource().setEvents("electrics.rpm");

        //Trying with my own processors
        //maxRPMprocessor = new GetMaxValueFromString("electrics.rpm");
        //Connector.connect(jsonStrings, 0, maxRPMprocessor, 0);

        final Processor stringToJsonMap = new StringToJsonMap();
        final SocketPump pump = new SocketPump(this.interval, this.clientSocket, this.serverSocket);
        Connector.connect(pump, 0, stringToJsonMap, 0);


        int nbOfProperties = 5;

        Fork fork = new Fork(nbOfProperties);
        Connector.connect(stringToJsonMap, 0, fork, 0);

        //QueueSink sink = new QueueSink(nbOfProperties);

        final Print[] printers = new Print[nbOfProperties];
        for (int i=0; i<printers.length; i++) {
            printers[i] = new Print();
            printers[i].setSeparator("\n");
        }
        printers[0].setPrefix("Min RPM: ");
        printers[1].setPrefix("Max WheelSpeed: ");
        printers[2].setPrefix("LeftSignal: ");
        printers[3].setPrefix("RightSignal: ");
        printers[4].setPrefix("LowFuel: ");


        Processor minRPM = new GetMinValueFromJsonMap("electrics.rpm");
        Connector.connect(fork, 0, minRPM, 0);
        Connector.connect(minRPM, 0, printers[0], 0);


        Processor maxWheelSpeed = new GetMaxValueFromJsonMap("electrics.wheelspeed");
        Connector.connect(fork, 1, maxWheelSpeed, 0);
        Connector.connect(maxWheelSpeed, 0, printers[1], 0);


        Processor leftSignal = new GetBooleanFromJsonMap("electrics.left_signal");
        Connector.connect(fork, 2, leftSignal, 0);
        Connector.connect(leftSignal, 0, printers[2], 0);


        Processor rightSignal = new GetBooleanFromJsonMap("electrics.right_signal");
        Connector.connect(fork, 3, rightSignal, 0);
        Connector.connect(rightSignal, 0, printers[3], 0);


        Processor lowFuel = new GetBooleanFromJsonMap("electrics.lowfuel");
        Connector.connect(fork, 4, lowFuel, 0);
        Connector.connect(lowFuel, 0, printers[4], 0);


        /*sink = new QueueSink();
        Connector.connect(max, 0, sink, 0); //Stores the result in the sink*/

        final Thread thread = new Thread(pump);
        thread.start();
    }
}
