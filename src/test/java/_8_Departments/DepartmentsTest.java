package _8_Departments;

import com.sun.tools.xjc.model.CArrayInfo;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.formula.functions.Match;
import org.apache.poi.ss.formula.functions.MatrixFunction;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class DepartmentsTest {
    Cookies cookies;
    String id;
    String name;
    String name2;
    String code;
    String shortName;
    String key;
    String value;
    int index;
    int index2;

    /**
     8 -  As an Admin User I should be able to Add-Edit-Delete Departments under School Setup
     URL:https://demo.mersys.io
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
    public String getRandomName(){
        String randomName= RandomStringUtils.randomAlphabetic(11).toLowerCase();
        return randomName;
    }
    public String getRandomCode(){
        String randomCode= RandomStringUtils.randomNumeric(6);
        return randomCode;
    }
    public String getRandomName2(){
        String randomName2=RandomStringUtils.randomAlphabetic(10).toLowerCase();
        return randomName2;
    }
    public String getRandomShortName(){
        String randomShortname=RandomStringUtils.randomAlphabetic(8).toUpperCase();
        return randomShortname;
    }
    public String getRandomKey(){
        String randomKey=RandomStringUtils.randomAlphabetic(7).toUpperCase();
        return randomKey;
    }
    public String getRandomValue(){
        String randomValue=RandomStringUtils.randomAlphabetic(7).toLowerCase();
        return randomValue;
    }
    public int getRandomIndex(){
        int randomIndex=(int) (Math.random()*1);
        return randomIndex;
    }
    public int getRandomIndex2(){
        int randomIndex=(int) (Math.random()*2);
        return randomIndex;
    }

    @Test
    public void createDepartments(){
        name=getRandomName();
        code=getRandomCode();
        DepartmentsFields df=new DepartmentsFields();
        df.setName(name);
        df.setCode(code);
        df.setActive(false);

        school school=new school();
        school.setId("6343bf893ed01f0dc03a509a");
        df.setSchool(school);

        name2=getRandomName2();
        shortName=getRandomShortName();
        index=getRandomIndex();
        sections sections=new sections();
        sections.setName(name2);
        sections.setShortName(shortName);
        sections.setIndex(index);
        sections.setActive(true);

        ArrayList<sections>sectionsArrayList=new ArrayList<>();
        sectionsArrayList.add(sections);


        df.setSections(sectionsArrayList);

        key=getRandomKey();
        value=getRandomValue();
        index2=getRandomIndex2();
        constants constants=new constants();
        constants.setKey(key);
        constants.setValue(value);
        constants.setIndex(index2);

        ArrayList<constants>constantsArrayList=new ArrayList<>();
        constantsArrayList.add(constants);

        df.setConstants(constantsArrayList);

        id=
          given()
                  .contentType(ContentType.JSON)
                  .cookies(cookies)
                  .body(df)

                  .when()
                  .post("school-service/api/department")

                  .then()
                  .log().body()
                  .statusCode(201)
                  .extract().jsonPath().getString("id");

    }
    @Test(dependsOnMethods = "createDepartments")
    public void editDepartments(){
        DepartmentsFields df=new DepartmentsFields();
        df.setName("MarketDepartment");
        df.setCode("564000");
        df.setActive(true);
        df.setId(id);

        school school=new school();
        school.setId("6343bf893ed01f0dc03a509a");
        df.setSchool(school);

        sections sections=new sections();
        sections.setName("Juanmi");
        sections.setShortName("Rodriguez");
        sections.setIndex(1);
        sections.setActive(true);

        ArrayList<sections>sectionsArrayList=new ArrayList<>();
        sectionsArrayList.add(sections);

        df.setSections(sectionsArrayList);

        constants constants=new constants();
        constants.setKey("Oihan");
        constants.setValue("Sancet");
        constants.setIndex(1);

        constants constants1=new constants();
        constants1.setKey("Alex");
        constants1.setValue("Berenguer");
        constants1.setIndex(2);

        ArrayList<constants>constantsArrayList=new ArrayList<>();
        constantsArrayList.add(constants);
        constantsArrayList.add(constants1);

        df.setConstants(constantsArrayList);

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(df)

                .when()
                .put("school-service/api/department")


                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("MarketDepartment"))
                .body("code",equalTo("564000"))
                .body("active",equalTo(true))
                .body("id",equalTo(id))
                .body("sections[0].name",equalTo("Juanmi"))
                .body("sections[0].shortName",equalTo("Rodriguez"))
                .body("sections[0].active",equalTo(true))
                .body("constants[0].key",equalTo("Oihan"))
                .body("constants[0].value",equalTo("Sancet"))
                .body("constants[1].key",equalTo("Alex"))
                .body("constants[1].value",equalTo("Berenguer"));

    }
    @Test(dependsOnMethods = "editDepartments")
    public void deleteDepartments(){
        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/department/{id}")

                .then()
                .statusCode(204);

    }


}
