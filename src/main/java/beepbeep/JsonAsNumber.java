package beepbeep;

import ca.uqac.lif.cep.functions.BinaryFunction;
import ca.uqac.lif.json.*;

public class JsonAsNumber extends BinaryFunction<JsonElement, String, Number>
{

    public static JsonAsNumber instance = new JsonAsNumber();

    public JsonAsNumber()
    {
        super(JsonElement.class, String.class, Number.class);
    }

    @Override
    public Number getValue(JsonElement map, String s)
    {
        JsonNumber jsonNumber = (JsonNumber) JsonPath.get(map, s);
        return jsonNumber.numberValue();
    }
}
