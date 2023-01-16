import Files.payload;
import ReUsableMethods.reUsableMethod;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
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
                .body(payload.AddBook("adsfsa","6968")).
                when().post("Library/Addbook.php").
                then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js= reUsableMethod.rawToJson(response);
        String id=js.get("ID");
        System.out.println("ID :"+id);
    }

    @DataProvider
    public Object[][] getData(){
        Object[][] data=new Object[3][2];
        data[0]=new Object[]{"ofghe","23453"};
        data[1]=new Object[]{"retdw","435243"};
        data[2]=new Object[]{"sfdsjd","43435"};
        return data;
    }
    @Test(dataProvider = "getData")
    public void addBook1(String isbn,String aisle ){
        RestAssured.baseURI="https://rahulshettyacademy.com";

        String response=given().header("Content-Type","application/json")
                .body(payload.AddBook(isbn,aisle)).
                when().post("Library/Addbook.php").
                then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath js= reUsableMethod.rawToJson(response);
        String id=js.get("ID");
        System.out.println("ID :"+id);
    }
}
