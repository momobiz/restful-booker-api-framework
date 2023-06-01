package testerTalks;

import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RestAssuredListener;

@Epic("Epic_01")
@Feature("create new booking functionality")
public class Day7 extends BaseTest{
	
	
	// How to configure Allure Report 
	// How to generate Allure Report 
	
	private static final Logger logger=LogManager.getLogger(Day7.class);
	
	
	@Story("As user I want to create a new booking")
	@Test(description="end to end api test")
	@Description("End To End testing")
	
	@Severity(SeverityLevel.CRITICAL)
	
	public void createBooking() {
		String checkin="2023-01-02";
		String checkout="2023-03-05";
		
		Bookingdates bookingdates=new Bookingdates(checkin,checkout);
		Booking booking=new Booking("Ahmed","Hamouda", 1000, true, bookingdates, "breakfast");
		
		logger.info("Testing functionality create booking starts...");
		
		Response response=
				given()
				    .filter(new AllureRestAssured())
				    .filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.body(booking)
				.when()
					.post("https://restful-booker.herokuapp.com/booking")
				;
	
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("Testing create booking ends...");
		
		
		
		
	}

}
