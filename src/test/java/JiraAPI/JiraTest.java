package JiraAPI;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest {
    public static void main(String[] args) {
        RestAssured.baseURI="http://localhost:8080";
        SessionFilter session=new SessionFilter();

        //Login Scenario
        String Response=given().header("content-type","application/json").body("{ \"username\": \"pranav0596\", \"password\": \"05091996\" }").
                log().all().filter(session).post("/rest/auth/1/session").then().log().all().extract().response().asString();


        //Add Comment -

        String givenmessege="Hi How are you ?";

        String addCommentResponse=given().pathParam("id",10100).log().all().header("Content-Type","application/json").
                body("{\n" +
                        "    \"body\": \""+givenmessege+"\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").
                then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js =new JsonPath(addCommentResponse);
        String commentID=js.getString("id");
        System.out.println("CommentID ="+commentID);

        //Add attachements -

        given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id",10100).log().all().
                header("Content-Type","multipart/form-data").
                multiPart("file",new File("F:\\Software testing\\RestAssured\\RestAssured_Home\\src\\test\\java\\JiraAPI\\Jira.txt")).
                when().post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);

        //Get Issue -

        String issueDetails=given().filter(session).pathParam("id",10100).queryParam("fields","comment").log().all().
                when().get("/rest/api/2/issue/{id}").
                then().log().all().extract().response().asString();

        System.out.println("Issue Details"+issueDetails);

        JsonPath js1=new JsonPath(issueDetails);
        int commentsCount=js1.get("fields.comment.comments.size()");
        for (int i=0;i<commentsCount;i++){
            String comid=js1.get("fields.comment.comments["+i+"].id").toString();
            if (comid.equals(commentID)){
                String messege=js1.get("fields.comment.comments["+i+"].body").toString();
                System.out.println("Messege ="+messege);
                Assert.assertEquals(messege,givenmessege);
            }
        }
    }
}
