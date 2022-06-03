package beepbeep.functions.parsers.properties;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonString;

public class JsonPropertyToString extends UnaryFunction<JsonElement, String> {

    public static final JsonPropertyToString instance = new JsonPropertyToString();

    public JsonPropertyToString() {
        super(JsonElement.class, String.class);
    }

    @Override
    public String getValue(JsonElement property) {
        JsonString jsonString = (JsonString) property;
        return jsonString.stringValue();
    }
}