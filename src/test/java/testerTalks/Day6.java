package testerTalks;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RestAssuredListener;


public class Day6 extends BaseTest{
	
	
	private static final Logger logger=LogManager.getLogger(Day6.class);
	
	@Test
	public void createBooking() {
		String checkin="2023-01-02";
		String checkout="2023-03-05";
		
		Bookingdates bookingdates=new Bookingdates(checkin,checkout);
		Booking booking=new Booking("Ahmed","Hamouda", 1000, true, bookingdates, "breakfast");
		
		logger.info("Testing functionality create booking starts...");
		
		Response response=
				given()
				    .filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.body(booking)
				.when()
					.post("https://restful-booker.herokuapp.com/booking")
				;
	
		Assert.assertEquals(response.getStatusCode(),400);
		
		logger.info("Testing create booking ends...");
		
		
		
		
	}

}
