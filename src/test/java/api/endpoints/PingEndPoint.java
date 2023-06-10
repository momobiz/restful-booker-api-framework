package api.endpoints;
import static io.restassured.RestAssured.*;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import listeners.RestAssuredListener;

public class PingEndPoint {
	
	public static Response getHealthCheck() {
		
		Response response=
				given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
				.when()
					.get(Routes.get_healthCheck_url)
				;
		return response;
		
	}


}
