package _10_GradeLevels;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import javax.json.Json;
import javax.json.JsonObject;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class GradeLevelsTest {
    Cookies cookies;
    String id;
    String name;
    String shortName;
    String order;

    /**
    10 -  As an Admin User I should be able to Add-Edit-Delete Grade Levels under Parameters Setup
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
    public String getRandomName(){
        String randomName= RandomStringUtils.randomAlphabetic(12).toLowerCase();
        return randomName;
    }
    public String getRandomShortname(){
        String randomShortname= RandomStringUtils.randomAlphabetic(8).toLowerCase();
        return randomShortname;
    }
    public String getRandomOrder(){
        String randomOrder=RandomStringUtils.randomNumeric(2);
        return randomOrder;
    }
    @Test
    public void createGradeLevels(){
        name=getRandomName();
        shortName=getRandomShortname();
        order=getRandomOrder();
        GradeLevelsFields glf=new GradeLevelsFields();
        glf.setName(name);
        glf.setShortName(shortName);
        glf.setOrder(order);
        glf.setActive(false);

        nextGradeLevel nextGradeLevel=new nextGradeLevel();
        nextGradeLevel.setId("633de14cd58ceb28b3974c1a");
        glf.setNextGradeLevel(nextGradeLevel);

        id=
            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(glf)

                    .when()
                    .post("school-service/api/grade-levels")

                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().jsonPath().getString("id");

    }
    @Test(dependsOnMethods = "createGradeLevels")
    public void editGradeLevels(){
        GradeLevelsFields glf=new GradeLevelsFields();
        glf.setName("Jose");
        glf.setShortName("Gimenez");
        glf.setOrder("52");
        glf.setActive(true);
        glf.setId(id);

        nextGradeLevel nextGradeLevel=new nextGradeLevel();
        nextGradeLevel.setId("633de14cd58ceb28b3974c1a");
        glf.setNextGradeLevel(nextGradeLevel);

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(glf)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("Jose"))
                .body("shortName",equalTo("Gimenez"))
                .body("order",equalTo(52))
                .body("id",equalTo(id))
                .body("active",equalTo(true))
                .body("nextGradeLevel.id",equalTo("633de14cd58ceb28b3974c1a"));

    }
    @Test(dependsOnMethods = "editGradeLevels")
    public void deleteGradeLevels(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/grade-levels/{id}")

                .then()
                .statusCode(200);

    }

}
