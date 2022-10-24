package _11_Discounts;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class DiscountsTest {
    Cookies cookies;
    String id;
    String code;
    String priority;
    String description;

    /**
     11- As an Admin User I should be able to Add-Edit-Delete Discounts under Parameters Setup
        URL:https://demo.mersys.io
     */
    @BeforeClass
    public void LoginCampus(){
        baseURI="https://demo.mersys.io/";
        Map<String,String> credential=new HashMap<>();
        credential.put("username","richfield.edu");
        credential.put("password","Richfield2020!");
        credential.put("rememberMe","true");

        cookies=

                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();

    }
    public String getRandomCode(){
        String randomCode= RandomStringUtils.randomNumeric(6);
        return randomCode;
    }
    public String getRandomPriority(){
        String randomPriority=RandomStringUtils.randomNumeric(1);
        return randomPriority;
    }
    public String getDescription(){
        String randomDescription=RandomStringUtils.randomAlphabetic(10);
        return randomDescription;
    }
    @Test
    public void createDiscounts(){
        code=getRandomCode();
        priority=getRandomPriority();
        description=getDescription();
        DiscountsFields df=new DiscountsFields();
        df.setCode(code);
        df.setDescription(description);
        df.setPriority(priority);
        df.setActive(true);
        id=
            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(df)

                    .when()
                    .post("school-service/api/discounts")

                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().jsonPath().getString("id")
            ;

    }
    @Test(dependsOnMethods = "createDiscounts")
    public void editDiscounts(){
        DiscountsFields df=new DiscountsFields();
        df.setPriority("3");
        df.setCode("43566");
        df.setDescription("deAlcazar");
        df.setActive(false);
        df.setId(id);

            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(df)

                    .when()
                    .put("school-service/api/discounts")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("priority",equalTo(3))
                    .body("code",equalTo("43566"))
                    .body("description",equalTo("deAlcazar"))
                    .body("active",equalTo(false));

    }
    @Test(dependsOnMethods = "editDiscounts")
    public void deleteDiscounts(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/discounts/{id}")

                .then()
                .statusCode(200)
                ;

    }



}
