import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.cep.util.Numbers;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonPath;

import java.io.IOException;

public class BeepBeep
{
    private QueueSource queueSource;
    private QueueSource queueSource2;
    private boolean isProcessing;
    private final ApplyFunction maxFunction;
    private boolean hasFilledSecondQueue = false;

    public BeepBeep()
    {
        System.out.println("Instantiating BeepBeep process");
        this.isProcessing = false;
        this.queueSource = new QueueSource();
        this.queueSource2 = new QueueSource();

        maxFunction = new ApplyFunction(Numbers.maximum);
        Connector.connect(queueSource, 0, maxFunction, 0);
        Connector.connect(queueSource2, 0, maxFunction, 1);
    }

    public void process()
    {
        isProcessing = true;
        Pullable p = maxFunction.getPullableOutput();

        //JsonMap jMap = (JsonMap) p.pull();
        float maxSpeed  = (float) p.pull();

        queueSource2 = new QueueSource();
        Connector.connect(queueSource2, 0, maxFunction, 1);
        queueSource2.addEvent(maxSpeed);

        System.out.println(queueSource.printState().toString());
        System.out.println(queueSource2.printState().toString());
        this.hasFilledSecondQueue = true;

        //System.out.println("Max:" + maxSpeed);
    }

    public float getSpeedFromJson(JsonMap jMap)
    {
        String rpmString = JsonPath.get(jMap, "electrics.rpm").toString();
        return Float.parseFloat(rpmString);
    }

    public void setEvents(String... queue)
    {
        JsonMap[] maps = new JsonMap[queue.length];
        for (int i=0; i<maps.length; i++)
        {
            maps[i] = parseJson(queue[i]);
        }
        queueSource.setEvents((Object[]) maps);
    }

    public void addToQueue(String s)
    {
        JsonMap jMap = parseJson(s);
        //System.out.println("Added to queue: " + jMap.toString());
        float speed = getSpeedFromJson(jMap);

        queueSource = new QueueSource();
        Connector.connect(queueSource, 0, maxFunction, 0);


        queueSource.addEvent(speed);
        if (!this.hasFilledSecondQueue)
        {
            queueSource2.addEvent(speed);
        }
    }

    public JsonMap parseJson(String jsonToParse)
    {
        ParseJson parse = ParseJson.instance;
        Object[] out = new Object[1];
        parse.evaluate(new Object[]{jsonToParse}, out);
        JsonElement j = (JsonElement) out[0];
        return (JsonMap) j;
    }


    public QueueSource getQueueSource()
    {
        return queueSource;
    }

    public boolean isProcessing()
    {
        return isProcessing;
    }

    public void setProcessing(boolean processing)
    {
        isProcessing = processing;
    }

}
