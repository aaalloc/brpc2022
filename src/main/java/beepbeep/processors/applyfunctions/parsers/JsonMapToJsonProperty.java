package beepbeep.processors.applyfunctions.parsers;

import ca.uqac.lif.cep.functions.ApplyFunction;

public class JsonMapToJsonProperty extends ApplyFunction {

    public JsonMapToJsonProperty(String jsonPath) {
        super(new beepbeep.functions.parsers.JsonMapToJsonProperty(jsonPath));
    }

}
