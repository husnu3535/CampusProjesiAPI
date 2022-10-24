package _5_Positions;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PositionsTest {
    String id;
    String name;
    String shortName;
    Cookies cookies;
    String tenantId="5fe0786230cc4d59295712cf";

    /**
     5-	As an Admin User I should be able to Add-Edit-Delete Positions under Human Resources Setup
        URL:https://demo.mersys.io
     */
    @BeforeClass
    public void LoginCampus(){
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
    public String getRandomName(){
        String randomName= RandomStringUtils.randomAlphabetic(9).toLowerCase();
        return randomName;
    }
    public String getRandomShortname(){
        String randomShortname= RandomStringUtils.randomAlphabetic(6).toLowerCase();
        return randomShortname;
    }
    @Test
    public void createPositions(){
        name=getRandomName();
        shortName=getRandomShortname();
        PositionsFields pf=new PositionsFields();
        pf.setName(name);
        pf.setShortName(shortName);
        pf.setTenantId(tenantId);
        pf.setActive(false);

        id=
                given()
                        .contentType(ContentType.JSON)
                        .cookies(cookies)
                        .body(pf)

                        .when()
                        .post("school-service/api/employee-position")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");


    }
    @Test(dependsOnMethods = "createPositions")
    public void editPositions(){
        PositionsFields pf=new PositionsFields();
        pf.setName("Tarkowski");
        pf.setShortName("Soria");
        pf.setId(id);
        pf.setTenantId(tenantId);
        pf.setActive(true);

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(pf)

                .when()
                .put("school-service/api/employee-position")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("Tarkowski"))
                .body("shortName",equalTo("Soria"))
                .body("id",equalTo(id))
                .body("tenantId",equalTo(tenantId))
                .body("active",equalTo(true));

    }
    @Test(dependsOnMethods = "editPositions")
    public void deletePositions() {
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id", id)

                .when()
                .delete("school-service/api/employee-position/{id}")

                .then()
                .statusCode(204);

    }

}
