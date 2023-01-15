import Files.payload;
import io.restassured.path.json.JsonPath;

public class complexJsonPath {
    public static void main(String[] args) {

        JsonPath js=new JsonPath(payload.CoursrPrice());

        //Print Number Of cources
        int count=js.getInt("courses.size()");
        System.out.println("Number Of cources ="+count);

        //print Purchase Amount

        int puramount=js.getInt("dashboard.purchaseAmount");
        System.out.println("Purchase Amount ="+puramount);

        //Print Title of First Course

        System.out.println("All Titles ="+js.getString("courses.title"));

        String firstTitle=js.getString("courses[0].title");
        System.out.println("first Titles ="+firstTitle);

        //Print all Cources and their Price

        for (int i=0;i<count;i++){
            String name=js.get("courses["+i+"].title");
            int price=js.get("courses["+i+"].price");
            System.out.println("Title ="+name+"  "+"Price ="+price);
        }

        //Print Number of copies for RPA

        System.out.println("Print Number of copies for RPA");
        for (int i=0;i<count;i++){
            String courseTitle=js.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA")){
                //COPIES Sold
                int copies=js.get("courses["+i+"].copies");
                System.out.println("Copies ="+copies);
                break;
            }
        }

        //Case - Verify sum of all elements should be equal to purchase amount

        int purchaseamount=js.get("dashboard.purchaseAmount");
        System.out.println("Puechase amount ="+purchaseamount);
        int sum=0;

        for (int i=0;i<count;i++){
            int price =js.get("courses["+i+"].price");
            sum=sum+price;
        }

        System.out.println("Sum"+sum);

        if (purchaseamount>=sum){
            System.out.println("Amount is under purchase amount");
        }
        else {
            System.out.println("Amount is above purchase amount");
        }
    }

}
