package beepbeep.processors.groupprocessors.getmax;

import beepbeep.processors.applyfunctions.GetMaxValue;
import beepbeep.processors.applyfunctions.parsers.JsonMapToJsonProperty;
import beepbeep.processors.applyfunctions.parsers.properties.JsonPropertyToNumber;
import ca.uqac.lif.cep.Connector;
import ca.uqac.lif.cep.GroupProcessor;
import ca.uqac.lif.cep.Processor;

import java.util.ArrayList;
import java.util.List;

public class GetMaxValueFromJsonMap extends GroupProcessor {

    private JsonMapToJsonProperty jsonMapToJsonProperty;
    private JsonPropertyToNumber jsonPropertyToNumber;
    private GetMaxValue getMaxValue;
    private final String jsonPath;

    public GetMaxValueFromJsonMap(String jsonPropertyPath) {
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
        processors.add(getMaxValue = new GetMaxValue());

        return processors;
    }

    private void connectAll() {
        Connector.connect(jsonMapToJsonProperty, 0, jsonPropertyToNumber, 0);
        Connector.connect(jsonPropertyToNumber, 0, getMaxValue, 0);

        this.associateInput(0, jsonMapToJsonProperty, 0);
        this.associateOutput(0, getMaxValue, 0);
    }

}