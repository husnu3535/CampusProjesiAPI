package _9_BankAccounts;

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

public class BankAccountsTest {
    Cookies cookies;
    String id;
    String name;
    String integrationCode;
    String schoolId="6343bf893ed01f0dc03a509a";
    /**
     9 - As an Admin User I should be able to Add-Edit-Delete Bank Accounts under Parameters Setup
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
    public String getRandomIntegrationCode(){
        String randomCode= RandomStringUtils.randomNumeric(5);
        return randomCode;
    }
    @Test
    public void createBankAccounts(){
        name=getRandomName();
        integrationCode=getRandomIntegrationCode();
        BankAccountsFields baf=new BankAccountsFields();
        baf.setName(name);
        baf.setIntegrationCode(integrationCode);
        baf.setActive(true);
        baf.setCurrency("EUR");
        baf.setIban("HG23 2132 1341 2314 3713 12");
        baf.setSchoolId(schoolId);

    id=
            given()
                    .contentType(ContentType.JSON)
                    .cookies(cookies)
                    .body(baf)

                    .when()
                    .post("school-service/api/bank-accounts")

                    .then()
                    .log().body()
                    .statusCode(201)
                    .extract().jsonPath().getString("id")
                    ;

    }
    @Test(dependsOnMethods = "createBankAccounts")
    public void editBankAccounts(){
        BankAccountsFields baf=new BankAccountsFields();
        baf.setName("Jaime");
        baf.setId(id);
        baf.setIntegrationCode("346554");
        baf.setActive(false);
        baf.setIban("RM34 3455 5643 3213 1234 43");
        baf.setSchoolId(schoolId);
        baf.setCurrency("TRY");

        given()
                .contentType(ContentType.JSON)
                .cookies(cookies)
                .body(baf)

                .when()
                .put("school-service/api/bank-accounts")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo("Jaime"))
                .body("id",equalTo(id))
                .body("integrationCode",equalTo("346554"))
                .body("active",equalTo(false))
                .body("iban",equalTo("RM34 3455 5643 3213 1234 43"))
                .body("schoolId",equalTo(schoolId))
                .body("currency",equalTo("TRY"));

    }
    @Test(dependsOnMethods = "editBankAccounts")
    public void deleteBankAccounts(){
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("id",id)

                .when()
                .delete("school-service/api/bank-accounts/{id}")

                .then()
                .statusCode(200);

    }
}
