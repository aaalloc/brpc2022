package beepbeep.functions.parsers.properties;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.json.JsonBoolean;
import ca.uqac.lif.json.JsonElement;

public class JsonPropertyToBoolean extends UnaryFunction<JsonElement, Boolean> {

    public static final JsonPropertyToBoolean instance = new JsonPropertyToBoolean();

    public JsonPropertyToBoolean() {
        super(JsonElement.class, Boolean.class);
    }

    @Override
    public Boolean getValue(JsonElement property) {
        JsonBoolean jsonBoolean = (JsonBoolean) property;
        return jsonBoolean.boolValue();
    }
}