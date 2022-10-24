package _1_PositionCategories;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PositionCategoriesTest {

    String id;
    String name;
    Cookies cookies;

    /**
     1 - As an Admin User I should be able to Add-Edit-Delete Position Categories Under Human Resources Setup
        URL: https://demo.mersys.io
     **/
    @BeforeClass
    public void Login(){
        baseURI="https://demo.mersys.io/";

        Map<String,String>credential=new HashMap<>();
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
    public String getRandomname(){
        String randomName= RandomStringUtils.randomAlphabetic(8).toLowerCase();
        return randomName;
    }


    @Test
    public void createPositionCategories(){
        name=getRandomname();

        PositionCategoriesFields pc=new PositionCategoriesFields();
        pc.setName(name);

        id=
                given()
                        .contentType(ContentType.JSON)
                        .cookies(cookies)
                        .body(pc)

                        .when()
                        .post("school-service/api/position-category")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
                ;

    }
        @Test(dependsOnMethods = "createPositionCategories")
        public void editPositionCategories(){
            PositionCategoriesFields pc=new PositionCategoriesFields();
            pc.setName("DataScience");
            pc.setId(id);



            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(pc)

                    .when()
                    .put("school-service/api/position-category")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("name",equalTo("DataScience"))
                    .body("id",equalTo(id))
                    ;

        }
      @Test(dependsOnMethods = "editPositionCategories")
      public void deletePositionCategories(){
            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .pathParam("id",id)

                    .when()
                    .delete("school-service/api/position-category/{id}")

                    .then()
                    .statusCode(204)
                    ;

      }

}

