package api.endpoints;
import static io.restassured.RestAssured.*;

import api.payload.Admin;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RestAssuredListener;


public class AuthEndPoint {
	
	public static Response createToken(Admin payload) {
		Response response=
				given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.body(payload)
				.when()
					.post(Routes.post_auth_url)
				;
		return response;
		
		
	}

}
