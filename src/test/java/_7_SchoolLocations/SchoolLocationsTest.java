package _7_SchoolLocations;

import com.fasterxml.jackson.annotation.JsonProperty;
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

public class SchoolLocationsTest {
    Cookies cookies;
    String id;
    String name;
    String shortName;
    String capacity;

    /**
     * 7 - As an Admin User I should be able to Add-Edit-Delete School Locations under School Setup
     * URL:https://demo.mersys.io
     */

    @BeforeClass
    public void LoginCampus() {
        baseURI = "https://demo.mersys.io/";

        Map<String, String> credential = new HashMap<>();
        credential.put("username", "richfield.edu");
        credential.put("password", "Richfield2020!");
        credential.put("rememberMe", "true");

        cookies =

                given()
                        .contentType(ContentType.JSON)
                        .body(credential)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();

    }

    public String getRandomName() {
        String randomName = RandomStringUtils.randomAlphabetic(8).toLowerCase();
        return randomName;
    }

    public String getRandomShortname() {
        String randomShortname = RandomStringUtils.randomAlphabetic(9).toLowerCase();
        return randomShortname;
    }

    public String getRandomCapacity() {
        String randomCapacity = RandomStringUtils.randomNumeric(3);
        return randomCapacity;
    }

    @Test
    public void createSchoolLocations() {
        name = getRandomName();
        shortName = getRandomShortname();
        capacity = getRandomCapacity();
        SchoolLocationsFields sf = new SchoolLocationsFields();
        sf.setName(name);
        sf.setShortName(shortName);
        sf.setCapacity(capacity);
        sf.setActive(true);
        sf.setType("OTHER");

        school school = new school();
        school.setId("6343bf893ed01f0dc03a509a");
        sf.setSchool(school);

        id =

                given()
                        .contentType(ContentType.JSON)
                        .cookies(cookies)
                        .body(sf)

                        .when()
                        .post("school-service/api/location")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");

    }

    @Test(dependsOnMethods = "createSchoolLocations")
    public void editSchoolLocations() {
        SchoolLocationsFields sf = new SchoolLocationsFields();
        sf.setName("Barca");
        sf.setShortName("Real");
        sf.setActive(false);
        sf.setType("CLASS");
        sf.setId(id);
        sf.setCapacity("13");

        school school=new school();
        school.setId("6343bf893ed01f0dc03a509a");
        sf.setSchool(school);


        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(sf)

                .when()
                .put("school-service/api/location")

                .then()
                .log().body()
                .statusCode(200)
                .body("name", equalTo("Barca"))
                .body("shortName", equalTo("Real"))
                .body("id", equalTo(id))
                .body("capacity", equalTo(13))
                .body("active", equalTo(false))
                .body("type", equalTo("CLASS"))
                .body("school.id", equalTo("6343bf893ed01f0dc03a509a"));
    }

    @Test(dependsOnMethods = "editSchoolLocations")
    public void deleteSchoolLocations(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/location/{id}")

                .then()
                .statusCode(200);
    }

}