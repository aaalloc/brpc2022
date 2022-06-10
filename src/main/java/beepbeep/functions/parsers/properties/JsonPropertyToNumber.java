package beepbeep.functions.parsers.properties;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.json.*;

public class JsonPropertyToNumber extends UnaryFunction<JsonElement, Number>
{

    public static final JsonPropertyToNumber instance = new JsonPropertyToNumber();

    public JsonPropertyToNumber()
    {
        super(JsonElement.class, Number.class);
    }

    @Override
    public Number getValue(JsonElement property)
    {
        JsonNumber jsonNumber = (JsonNumber) property;
        return jsonNumber.numberValue();
    }
}
