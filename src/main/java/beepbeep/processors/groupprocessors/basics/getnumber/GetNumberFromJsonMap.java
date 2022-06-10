package beepbeep.processors.groupprocessors.basics.getnumber;

import beepbeep.processors.applyfunctions.parsers.JsonMapToJsonProperty;
import beepbeep.processors.applyfunctions.parsers.properties.JsonPropertyToBoolean;
import beepbeep.processors.applyfunctions.parsers.properties.JsonPropertyToNumber;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;

import java.util.ArrayList;
import java.util.List;

public class GetNumberFromJsonMap extends GroupProcessor {

    private JsonMapToJsonProperty jsonMapToJsonProperty;
    private JsonPropertyToNumber jsonPropertyToNumber;
    private final String jsonPath;

    public GetNumberFromJsonMap(String jsonPropertyPath) {
        super(1, 1);

        this.jsonPath = jsonPropertyPath;

        List<Processor> processors = makeProcessors();
        this.addProcessors(processors.toArray(new Processor[0]));

        this.connectAll();
    }

    private List<Processor> makeProcessors() {
        List<Processor> processors = new ArrayList<>();

        processors.add(jsonMapToJsonProperty = new JsonMapToJsonProperty(jsonPath));
        processors.add(jsonPropertyToNumber = new JsonPropertyToNumber());

        return processors;
    }

    private void connectAll() {
        Connector.connect(jsonMapToJsonProperty, 0, jsonPropertyToNumber, 0);

        this.associateInput(0, jsonMapToJsonProperty, 0);
        this.associateOutput(0, jsonPropertyToNumber, 0);
    }

}