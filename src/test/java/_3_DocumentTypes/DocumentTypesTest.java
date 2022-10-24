package _3_DocumentTypes;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.sl.draw.binding.STTextShapeType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.ws.Response;
import java.awt.geom.RectangularShape;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DocumentTypesTest {
	String id;
	String name;
	String description;
	String schoolId="6343bf893ed01f0dc03a509a";
	Cookies cookies;

	/**
	 3 - As an Admin User I should be able to Add-Edit-Delete Document Types under Parameters Setup
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
		String randomName= RandomStringUtils.randomAlphabetic(12).toLowerCase();
		return randomName;
	}
	public String getRandomDescription(){
		String randomDescription=RandomStringUtils.randomAlphabetic(21);
		return randomDescription;
	}


	@Test
	public void createDocumentTypes(){
		name=getRandomName();
		description=getRandomDescription();

		DocumentTypesFields dtf=new DocumentTypesFields();
		dtf.setName(name);
		dtf.setDescription(description);
		dtf.setSchoolId(schoolId);
		dtf.setAttachmentStages(new int[]{2});

		id=
				given()
						.contentType(ContentType.JSON)
						.cookies(cookies)
						.body(dtf)

						.when()
						.post("school-service/api/attachments")

						.then()
						.log().body()
						.statusCode(201)
						.extract().jsonPath().getString("id");
	}
	@Test(dependsOnMethods = "createDocumentTypes")
	public void editDocumentTypes(){
		description=getRandomDescription();

		DocumentTypesFields dtf=new DocumentTypesFields();
		dtf.setName("Carmelaw");
		dtf.setSchoolId(schoolId);
		dtf.setDescription(description);
		dtf.setId(id);
		dtf.setAttachmentStages(new int[]{0,1,2});

		given()
				.contentType(ContentType.JSON)
				.cookies(cookies)
				.body(dtf)

				.when()
				.put("school-service/api/attachments")

				.then()
				.statusCode(200)
				.log().body()
				.body("name",equalTo("Carmelaw"))
				.body("description",equalTo(description))
				.body("id",equalTo(id))
				.body("attachmentStages[0]",equalTo("EXAMINATION"))
				.body("attachmentStages[1]",equalTo("STUDENT_REGISTRATION"))
				.body("attachmentStages[2]",equalTo("EMPLOYMENT"));

	}

	@Test(dependsOnMethods = "editDocumentTypes")
	public void deleteDocumentTypes(){
		given()
				.cookies(cookies)
				.contentType(ContentType.JSON)
				.pathParam("id",id)

				.when()
				.delete("school-service/api/attachments/{id}")

				.then()
				.statusCode(200)
				;

	}

}