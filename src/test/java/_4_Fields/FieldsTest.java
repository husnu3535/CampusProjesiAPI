package _4_Fields;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FieldsTest {
	String id;
	String name;
	String code;
	String schoolId="6343bf893ed01f0dc03a509a";
	Cookies cookies;
    /**
     4 - As an Admin User I should be able to Add-Edit-Delete Fields under Parameters Setup
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
		String randomName= RandomStringUtils.randomAlphabetic(13).toLowerCase();
		return randomName;
	}
	public String getRandomCode(){
		String randomCode= RandomStringUtils.randomAlphabetic(8).toLowerCase();
		return randomCode;
	}

	@Test
	public void createFields(){
		name=getRandomName();
		code=getRandomCode();
		FieldsAndFields fields=new FieldsAndFields();
		fields.setName(name);
		fields.setCode(code);
		fields.setSchoolId(schoolId);
		fields.setType("COMPOSITE");

		id=
				given()
						.contentType(ContentType.JSON)
						.cookies(cookies)
						.body(fields)

						.when()
						.post("school-service/api/entity-field")

						.then()
						.log().body()
						.statusCode(201)
						.extract().jsonPath().getString("id");

	}
	@Test(dependsOnMethods = "createFields")
	public void editFields(){
		FieldsAndFields fields=new FieldsAndFields();
		fields.setName("MuratPasa");
		fields.setCode("346576762");
		fields.setId(id);
		fields.setSchoolId(schoolId);
		fields.setType("TEXTAREA");

		given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.body(fields)

				.when()
				.put("school-service/api/entity-field")

				.then()
				.log().body()
				.statusCode(200)
				.body("name",equalTo("MuratPasa"))
				.body("code",equalTo("346576762"))
				.body("id",equalTo(id))
				.body("schoolId",equalTo(schoolId))
				.body("type",equalTo("TEXTAREA"));

	}
	@Test(dependsOnMethods = "editFields")
	public void deleteFields(){
		given()
				.cookies(cookies)
				.contentType(ContentType.JSON)
				.pathParam("id",id)

				.when()
				.delete("school-service/api/entity-field/{id}")

				.then()
				.statusCode(204);

	}

}
