import Files.payload;
import ReUsableMethods.reUsableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test
    public void addBook(){
        RestAssured.baseURI="https://rahulshettyacademy.com";
//        String response=given().header("Content-Type","application/json")
//                .body(payload.AddBook()).
//        when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200).extract().response().asString();

        String response=given().header("Content-Type","application/json")
                .body(payload.AddBook("adsfsa","6969")).
                when().post("Library/Addbook.php").
                then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js= reUsableMethod.rawToJson(response);
        String id=js.get("ID");
        System.out.println("ID :"+id);
    }
}
