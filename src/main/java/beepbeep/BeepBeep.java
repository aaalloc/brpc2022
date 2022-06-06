package beepbeep;

import beepbeep.processors.applyfunctions.parsers.StringToJsonMap;
import beepbeep.processors.groupprocessors.getboolean.GetBooleanFromJsonMap;
import beepbeep.processors.groupprocessors.getmax.GetMaxValueFromJsonMap;
import beepbeep.processors.groupprocessors.getmin.GetMinValueFromJsonMap;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Pump;
import ca.uqac.lif.cep.tmf.QueueSource;

public class BeepBeep
{

    private final QueueSource jsonStrings = new QueueSource();

    private final Processor minRPM;
    private final Processor maxWheelSpeed;
    private final Processor leftSignal;
    private final Processor rightSignal;
    private final Processor lowFuel;

    /*private final Cumulate max;
    //private final QueueSink sink;
    //private final Pushable psPrint;

    private final ApplyFunction stringToJsonMap;*/

    public BeepBeep()
    {
        System.out.println("Instantiating BeepBeep process");

        //final QueueSource elementsToGrab = new QueueSource().setEvents("electrics.rpm");

        Pump pump = new Pump(100);


        //Trying with my own processors
        //maxRPMprocessor = new GetMaxValueFromString("electrics.rpm");
        //Connector.connect(jsonStrings, 0, maxRPMprocessor, 0);

        Processor stringToJsonMap = new StringToJsonMap();
        Connector.connect(jsonStrings, 0, stringToJsonMap, 0);


        int nbOfProperties = 5;
        Fork fork = new Fork(nbOfProperties); //1 output with the same JsonMap for each property
        Connector.connect(stringToJsonMap, 0, fork, 0);

        //QueueSink sink = new QueueSink(nbOfProperties);

        minRPM = new GetMinValueFromJsonMap("electrics.rpm");
        Connector.connect(fork, 0, minRPM, 0);

        maxWheelSpeed = new GetMaxValueFromJsonMap("electrics.wheelspeed");
        Connector.connect(fork, 1, maxWheelSpeed, 0);

        leftSignal = new GetBooleanFromJsonMap("electrics.left_signal");
        Connector.connect(fork, 2, leftSignal, 0);

        rightSignal = new GetBooleanFromJsonMap("electrics.right_signal");
        Connector.connect(fork, 3, rightSignal, 0);

        lowFuel = new GetBooleanFromJsonMap("electrics.lowfuel");
        Connector.connect(fork, 4, lowFuel, 0);


        /*sink = new QueueSink();
        Connector.connect(max, 0, sink, 0); //Stores the result in the sink*/

        /*final Print print = new Print();
        print.setPrintStream(System.out);
        psPrint = print.getPushableInput();
        Connector.connect(max, 0, print, 0); //Prints the output*/
    }

    public void printMaxSpeed()
    {
        System.out.println("******************************");
        System.out.println("Min RPM: " + minRPM.getPullableOutput().pull());
        System.out.println("Max WheelSpeed: " + maxWheelSpeed.getPullableOutput().pull() + " m/s");
        System.out.println("LeftSignal: " + leftSignal.getPullableOutput().pull());
        System.out.println("RightSignal: " + rightSignal.getPullableOutput().pull());
        System.out.println("LowFuel: " + lowFuel.getPullableOutput().pull());

        //System.out.println("Max: " + sink.getPullableOutput().pull());
    }


    public void addToQueue(String s)
    {
        //System.out.println("Added to queue: " + s);
        jsonStrings.addEvent(s);
        //jsonStrings.getPushableInput().push(s); pas sûr
        //stringToJsonMap.getPushableInput().push(s);
    }

}
