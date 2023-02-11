package OAuth;

import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static io.restassured.RestAssured.given;

public class OauthTest {
    public static void main(String[] args) throws InterruptedException {

//        String baseDir = System.getProperty("user.dir");
//        System.setProperty("webdriver.chrome.driver",baseDir + "\\src\\test\\resources\\drivers\\chromedriver.exe");
//        WebDriver driver =new ChromeDriver();
//
//        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("pranav.patil0596@gmail.com");
//        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//        Thread.sleep(3000);
//
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("05091996@Pnp");
//        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//        Thread.sleep(6000);
//
//        String url=driver.getCurrentUrl();

        String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AWtgzh5DPSzBRCmqWTcab13asgfwvb2azK4BbZoNXDcq0vIEiKmgUABkuQYciU51MOnkHQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";



        String partialcode=url.split("code=")[1];
        String code=partialcode.split("&scope")[0];
        System.out.println("code="+code);



        String access_token=given().urlEncodingEnabled(false).queryParams("code",code).
                queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com").
                queryParams("client_secret","erZOWM9g3UtwNRj3340YYaK_W").
                queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php").
                queryParams("grant_type","authorization_code").
                when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath js=new JsonPath(access_token);
        String accessToken=js.getString("access_token");


        String response=given().queryParam("access_token",accessToken).
                when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();

        System.out.println(response);
    }
}
