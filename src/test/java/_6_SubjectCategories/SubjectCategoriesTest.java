package _6_SubjectCategories;

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

public class SubjectCategoriesTest {
    String id;
    String name;
    String code;
    Cookies cookies;

    /**
      6- As an Admin User I should be able to Add-Edit-Delete Subject Categories under Education Setup
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
    public String getRandomCode(){
        String randomCode= RandomStringUtils.randomNumeric(8);
        return randomCode;
    }


    @Test
    public void createSubjectCategories(){
        name=getRandomName();
        code=getRandomCode();
        SubjectCategoriesFields sbf=new SubjectCategoriesFields();
        sbf.setName(name);
        sbf.setCode(code);
        sbf.setActive(false);

      id=
          given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(sbf)

                .when()
                .post("school-service/api/subject-categories")

                .then()
                .log().body()
                .statusCode(201)
                .extract().jsonPath().getString("id");

    }
    @Test (dependsOnMethods = "createSubjectCategories")
    public void editSubjectCategories(){
        SubjectCategoriesFields sbf=new SubjectCategoriesFields();
        sbf.setName("Murteza");
        sbf.setCode("35034222");
        sbf.setId(id);
        sbf.setActive(true);

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(sbf)

                .when()
                .put("school-service/api/subject-categories")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("Murteza"))
                .body("code",equalTo("35034222"))
                .body("id",equalTo(id))
                .body("active",equalTo(true));


    }
    @Test(dependsOnMethods = "editSubjectCategories")
    public void deleteSubjectCategories(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/subject-categories/{id}")

                .then()
                .statusCode(200)
                ;

    }



}
