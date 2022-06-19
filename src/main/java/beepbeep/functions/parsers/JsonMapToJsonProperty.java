package beepbeep.functions.parsers;

import ca.uqac.lif.cep.functions.UnaryFunction;
import ca.uqac.lif.json.JsonElement;
import ca.uqac.lif.json.JsonMap;
import ca.uqac.lif.json.JsonPath;

public class JsonMapToJsonProperty extends UnaryFunction<JsonMap, JsonElement> {

    private final String jsonPath;

    public JsonMapToJsonProperty(String jsonPath) {
        super(JsonMap.class, JsonElement.class);
        this.jsonPath = jsonPath;
    }

    @Override
    public JsonElement getValue(JsonMap jsonMap) {
        return JsonPath.get(jsonMap, jsonPath);
    }

}
