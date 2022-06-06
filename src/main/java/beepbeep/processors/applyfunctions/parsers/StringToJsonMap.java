package beepbeep.processors.applyfunctions.parsers;

import ca.uqac.lif.cep.functions.ApplyFunction;
import ca.uqac.lif.cep.json.ParseJson;

public class StringToJsonMap extends ApplyFunction {

    public StringToJsonMap() {
        super(ParseJson.instance);
    }
}
