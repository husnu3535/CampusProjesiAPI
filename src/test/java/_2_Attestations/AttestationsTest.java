package _2_Attestations;

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

public class AttestationsTest {
	String id;
	String name;
	Cookies cookies;
    /**
     2 - As an Admin User I should be able to Add-Edit-Delete Attestations under Human Resources Setup
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
		String randomName= RandomStringUtils.randomAlphabetic(11).toLowerCase();
		return randomName;
	}

	@Test
	public void createAttestations(){
		name=getRandomName();
		AttestationsFields af=new AttestationsFields();
		af.setName(name);

		id=
				given()
						.contentType(ContentType.JSON)
						.cookies(cookies)
						.body(af)

						.when()
						.post("school-service/api/attestation")

						.then()
						.log().body()
						.statusCode(201)
						.extract().jsonPath().getString("id")

				;
	}
	@Test(dependsOnMethods = "createAttestations")
	public void editAttestations(){
		AttestationsFields af=new AttestationsFields();
		af.setName("SacramentDocument");
		af.setId(id);

		given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.body(af)

				.when()
				.put("school-service/api/attestation")

				.then()
				.log().body()
				.statusCode(200)
				.body("name",equalTo("SacramentDocument"))
				.body("id",equalTo(id))
			;

	}
	@Test(dependsOnMethods = "editAttestations")
	public void deleteAttestations(){
		given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.pathParam("id",id)

				.when()
				.delete("school-service/api/attestation/{id}")

				.then()
				.statusCode(204)
		;

	}


}
