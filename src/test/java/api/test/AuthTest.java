package api.test;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import api.endpoints.AuthEndPoint;
import api.utilities.Path;
import api.utilities.ReadData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Epic("Epic-01")
@Feature("Testing Auth and generate Token")
public class AuthTest extends BaseTest {
	
	Logger logger=LogManager.getLogger(AuthTest.class);
	
	@Test
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.CRITICAL)
	public void generateToken(ITestContext context) {
		
		logger.info("******* test generateToken starts... *******");
		
		
		Response response=AuthEndPoint.createToken(admin);
		
		JSONObject jo=new JSONObject(response.asString());
		context.setAttribute("token","token="+jo.getString("token"));
		
//		response.then().log().all();
		
		// Statuscode verification 
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// Statusline verification 
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
		
		// Headers verification
		Assert.assertEquals(response.getHeader("Server"),"Cowboy");
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
		
		// Schema verification 		
		MatcherAssert.assertThat(response.getBody().asString(), 
									JsonSchemaValidator.matchesJsonSchema(schemaAuth));	
		
		logger.info("******* test generateToken ended *******");
		
	}

}
