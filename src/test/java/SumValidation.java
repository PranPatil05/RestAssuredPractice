import org.testng.Assert;
import org.testng.annotations.Test;
import Files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
    @Test
    public static void sumOfCources(){
        JsonPath js=new JsonPath(payload.CoursrPrice());
        int purchaseamount=js.get("dashboard.purchaseAmount");
        System.out.println("Puechase amount ="+purchaseamount);
        int sum=0;
        int count=js.get("courses.size()");

        for (int i=0;i<count;i++){
            int price =js.get("courses["+i+"].price");
            int copies =js.get("courses["+i+"].copies");
            int sum1=price*copies;
            System.out.println(sum1);
            sum=sum+sum1;
        }

        System.out.println("Sum"+sum);

        Assert.assertTrue(purchaseamount>=sum);

    }

}
