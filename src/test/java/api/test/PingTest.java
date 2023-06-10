package api.test;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.PingEndPoint;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;

@Epic("Epic-03")
@Feature("Testing Ping ")
public class PingTest extends BaseTest{
	
	Logger logger=LogManager.getLogger(PingTest.class);

	@Test
	@Description("Verify statuscode, statusline, header, body")
	@Severity(SeverityLevel.MINOR)
	public void testGetHealthCheck() {
		logger.info("******* test testGetHealthCheck starts... ********");
		Response response=PingEndPoint.getHealthCheck();
//		response.then().log().all();
		
		// statuscode verification
		Assert.assertEquals(response.getStatusCode(), 201);
		
		//statusline verification 
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 201 Created");
		
		//headers verification 
		Assert.assertEquals(response.getHeader("Server"), "Cowboy");
		Assert.assertEquals(response.getHeader("Content-Type"), "text/plain; charset=utf-8");
		
		// content body verification 
		Assert.assertEquals(response.getBody().asString(), "Created");
		
		
		logger.info("******* test testGetHealthCheck ended ********");
	}

}
