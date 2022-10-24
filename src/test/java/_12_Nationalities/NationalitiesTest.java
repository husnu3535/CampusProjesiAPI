package _12_Nationalities;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.soap.SAAJResult;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class NationalitiesTest {
    Cookies cookies;
    String id;
    String name;

    /**
     12 - As an Admin User I should be able to Add-Edit-Delete Nationalities under Parameters Setup
         URL:https://demo.mersys.io/
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
        String randomName= RandomStringUtils.randomAlphabetic(11).toLowerCase();
        return randomName;
    }
    @Test
    public void createNationalities(){
        name=getRandomName();
        NationalitiesFields nf=new NationalitiesFields();
        nf.setName(name);

        id=
            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(nf)

                    .when()
                    .post("school-service/api/nationality")

                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().jsonPath().getString("id")
                    ;
    }
    @Test
    public void editNationalities(){
        NationalitiesFields nf=new NationalitiesFields();
        nf.setName("Zanzibar");
        nf.setId(id);

            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(nf)

                    .when()
                    .put("school-service/api/nationality")

                    .then()
                    .log().body()
                    .statusCode(200)
                    .body("name",equalTo("Zanzibar"))
                    .body("id",equalTo(id));

    }
    @Test(dependsOnMethods = "editNationalities")
    public void deleteNationalities(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/nationality/{id}")

                .then()
                .statusCode(200)
                ;

    }
    @Test(dependsOnMethods = "deleteNationalities")
    public void getNationalitiesAndNegative(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .post("school-service/api/nationality/{id}")

                .then()
                .statusCode(405);

    }

}
