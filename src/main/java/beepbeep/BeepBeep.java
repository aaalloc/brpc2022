package beepbeep;

import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Cumulate;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.QueueSink;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;

public class BeepBeep
{
    private final Cumulate max;

    private final QueueSource jsonStrings = new QueueSource();

    public BeepBeep()
    {
        System.out.println("Instantiating BeepBeep process");

        final ApplyFunction stringToJsonMap = new ApplyFunction(ParseJson.instance);
        System.out.println("Input arity of stringToJsonMap: " + stringToJsonMap.getInputArity());
        System.out.println("Output arity of stringToJsonMap: " + stringToJsonMap.getOutputArity());

        final ApplyFunction jsonMapToNumber = new ApplyFunction(JsonAsNumber.instance);
        System.out.println("Input arity of jsonMapToNumber: " + jsonMapToNumber.getInputArity());
        System.out.println("Output arity of jsonMapToNumber: " + jsonMapToNumber.getOutputArity());

        final CumulativeFunction<Number> maxFunction = new CumulativeFunction<>(Numbers.maximum);
        max = new Cumulate(maxFunction);
        System.out.println("Input arity of max: " + max.getInputArity());
        System.out.println("Output arity of max: " + max.getOutputArity());

        QueueSource elementsToGrab = new QueueSource().setEvents("electrics.rpm");

        Connector.connect(jsonStrings, 0, stringToJsonMap, 0); //Parse string to jsonMap
        Connector.connect(stringToJsonMap, 0, jsonMapToNumber, 0); //Setup input containing maps to grab from
        Connector.connect(elementsToGrab, 0, jsonMapToNumber, 1); //Add path of element to grab to parameter
        Connector.connect(jsonMapToNumber, 0, max, 0); //Parse the output to number

    }

    public void printMaxSpeed()
    {
        System.out.println("Max: " + max.getPullableOutput().pull());
    }


    public void addToQueue(String s)
    {
        System.out.println("Added to queue: " + s);
        jsonStrings.addEvent(s);
    }

}
