package beepbeep.processors.groupprocessors.basics.getboolean;

import beepbeep.processors.applyfunctions.parsers.JsonMapToJsonProperty;
import beepbeep.processors.applyfunctions.parsers.properties.JsonPropertyToBoolean;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;

import java.util.ArrayList;
import java.util.List;

public class GetBooleanFromString extends GroupProcessor {

    private JsonMapToJsonProperty jsonMapToJsonProperty;
    private JsonPropertyToBoolean jsonPropertyToBoolean;
    private final String jsonPath;

    public GetBooleanFromString(String jsonPropertyPath) {
        super(1, 1);

        this.jsonPath = jsonPropertyPath;

        List<Processor> processors = makeProcessors();
        this.addProcessors(processors.toArray(new Processor[0]));

        this.connectAll();
    }

    private List<Processor> makeProcessors() {
        List<Processor> processors = new ArrayList<>();

        processors.add(jsonMapToJsonProperty = new JsonMapToJsonProperty(jsonPath));
        processors.add(jsonPropertyToBoolean = new JsonPropertyToBoolean());

        return processors;
    }

    private void connectAll() {
        Connector.connect(jsonMapToJsonProperty, 0, jsonPropertyToBoolean, 0);

        this.associateInput(0, jsonMapToJsonProperty, 0);
        this.associateOutput(0, jsonPropertyToBoolean, 0);
    }

}