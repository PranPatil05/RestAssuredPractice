import Files.payload;
import ReUsableMethods.reUsableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {
    public static void main(String[] args) throws IOException {
        RestAssured.baseURI="https://rahulshettyacademy.com";
//        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
//                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();

        //Giving JSON File path
        //Body is String Type method
        //json Coverts byte(Files.readAllBytes(Paths.get(0) ->Byte converts to String (new String)


        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(new String(Files.readAllBytes(Paths.get("F:\\Software testing\\RestAssured\\RestAssured_Home\\src\\test\\resources\\Files\\Addbookdetails.json")))).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP")).extract().response().asString();

        System.out.println(response);
        JsonPath js=new JsonPath(response); //For Parsing Json
        String placeId=js.getString("place_id");
        String status=js.getString("status");
        String scope=js.getString("scope");
        String id=js.getString("id");



        System.out.println("PlaceID ="+placeId);
        System.out.println("Status ="+status);
        System.out.println("Scope ="+scope);
        System.out.println("ID ="+id);

        //Update Place
        String newAddress="Summer Walk,Africa";

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body("{\n" +
                "\"place_id\":\""+placeId+"\",\n" +
                "\"address\":\""+newAddress+"\",\n" +
                "\"key\":\"qaclick123\"\n" +
                "}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
        .body("msg",equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse= given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println(getPlaceResponse);

        JsonPath js1=reUsableMethod.rawToJson(getPlaceResponse);
        String actualAddress=js1.getString("address");
        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress, "Summer Walk,Africa");



    }
}
