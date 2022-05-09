import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.json.ParseJson;
import ca.uqac.lif.cep.tmf.QueueSource;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;

import java.io.IOException;

public class BeepBeep
{
    private final QueueSource queueSource;
    private boolean isProcessing;

    public BeepBeep()
    {
        System.out.println("Instantiating BeepBeep process");
        this.isProcessing = false;
        this.queueSource = new QueueSource();
        //Connector.connect(queueSource,
    }

    public void startProcessing() throws IOException
    {
        isProcessing = true;
        Pullable p = queueSource.getPullableOutput();

        System.out.println("Starting processing");
        while (isProcessing)
        {
            for (Object o : p) {
                JsonMap jMap = (JsonMap) o;
                double speed = getSpeedFromJson(jMap);
                System.out.println(speed);
            }
        }

    }

    public double getSpeedFromJson(JsonMap jMap)
    {
        JsonMap jMap2 = (JsonMap) jMap.get("electrics");
        return Double.parseDouble(jMap2.get("rpm").toString());
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
        System.out.println("Added to queue: " + jMap.toString());
        queueSource.addEvent(jMap);
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
