package ReUsableMethods;

import io.restassured.path.json.JsonPath;

public class reUsableMethod {
    public static JsonPath rawToJson(String response){
        JsonPath js=new JsonPath(response);
        return js;
    }
}
