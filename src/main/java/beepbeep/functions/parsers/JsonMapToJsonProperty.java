package beepbeep.functions.parsers;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonPath;

public class JsonMapToJsonProperty extends UnaryFunction<JsonElement, JsonElement>
{

    private final String jsonPath;

    public JsonMapToJsonProperty(String jsonPath) {
        super(JsonElement.class, JsonElement.class);
        this.jsonPath = jsonPath;
    }

    @Override
    public JsonElement getValue(JsonElement jsonMap) {
        return JsonPath.get(jsonMap, jsonPath);
    }

}
