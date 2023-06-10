package api.test;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static io.restassured.RestAssured.*;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hamcrest.MatcherAssert;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import api.endpoints.BookingEndPoint;
import api.payload.Booking;
import api.payload.Bookingdates;
import api.payload.PartialBooking;
import api.utilities.Path;
import api.utilities.ReadData;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

@Epic("Epic-02")
@Feature("Testing create read update and delete Booking")
public class BookingTest extends BaseTest{
	int bookingid;
	public Logger logger=LogManager.getLogger(BookingTest.class);
	
	@Test(priority=1)
	@Description("Verify statuscode, statusline, header")
	@Severity(SeverityLevel.MINOR)
	public void testGetBookingIds() {
		
		logger.info("******* testGetBookingIds starts... *******");
		Response response=BookingEndPoint.getBookingIds();
//		response.then().log().all();
		
		// statuscode verification
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// header verification
		Assert.assertEquals(response.getHeader("Server"), "Cowboy");
		Assert.assertEquals(response.getHeader("Content-Type"), "application/json; charset=utf-8");
		
		
		logger.info("******* testGetBookingIds ended *******");
		
	}
	
	
	@Test(priority=2, dataProvider="getData")
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.NORMAL)
	public void testCreateBooking(Map<String,String> record) {
		
		logger.info("******* test Create booking starts... **********");
		
		String firstname=record.get("firstname");
		String lastname=record.get("lastname");
		int totalprice=Integer.parseInt(record.get("totalprice"));
		boolean depositpaid=Boolean.parseBoolean(record.get("depositpaid"));
		String checkin=record.get("checkin");
		String checkout=record.get("checkout");
		String additionalneeds=record.get("additionalneeds");
		Bookingdates bookingdates=new Bookingdates(checkin,checkout);
		
		
		booking=new Booking(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);
		Response response= BookingEndPoint.createBooking(booking);
		
		
		
//		response.then().log().all();
		
		// status code verification
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//status line verification
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
		
		// response headers verification
		Assert.assertEquals(response.header("Server"),"Cowboy");
		Assert.assertEquals(response.header("Content-Type"),"application/json; charset=utf-8");
		
		//Assignement id to bookingid 
		JSONObject jo=new JSONObject(response.asString());
		bookingid=jo.getInt("bookingid");

		
		// Response header verification
		JSONObject joBooking=jo.getJSONObject("booking");
		
	    Assert.assertEquals(joBooking.getString("firstname"), firstname);
		Assert.assertEquals(joBooking.getString("lastname"), lastname);
		Assert.assertEquals(joBooking.getInt("totalprice"), totalprice);
		Assert.assertEquals(joBooking.getBoolean("depositpaid"), depositpaid);
		Assert.assertEquals(joBooking.getString("additionalneeds"),additionalneeds);
		
		JSONObject joBookingdates=joBooking.getJSONObject("bookingdates");
		
		Assert.assertEquals(joBookingdates.getString("checkin"), checkin);
		Assert.assertEquals(joBookingdates.getString("checkout"), checkout);
	   
		// Response Schema verification	
		MatcherAssert.assertThat(response.getBody().asString(), JsonSchemaValidator.matchesJsonSchema(bookingSchema));
	
		logger.info("******* test Create booking ended *******");
		
	}
	
	@Test(priority=3, dataProvider = "getData")
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.NORMAL)
	public void testGetBooking(Map<String,String> record) {
		
		logger.info("******* testing getBooking starts *******");
		
		Response response=BookingEndPoint.getBooking(bookingid);
		
		// Verification of status code
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// Verification of status line
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
		
		// Headers verification
		Assert.assertEquals(response.header("Server"),"Cowboy");
		Assert.assertEquals(response.header("Content-Type"),"application/json; charset=utf-8");
		
		// Content Body verification
		JSONObject jo=new JSONObject(response.asString());
		Assert.assertEquals(jo.get("firstname"), record.get("firstname"));
		
		//Schema verification
		MatcherAssert.assertThat(response.getBody().asString(),
				JsonSchemaValidator.matchesJsonSchema(bookingSchemaGetRequest));
		
		
		logger.info("******* test get booking ended... *******");
		
		
		
	}
	
	@Test(priority=4, dataProvider="getData", dependsOnMethods = {"testGetBooking"})
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.NORMAL)
	public void testGetBookingIdsByName(Map<String,String> record) {
		
		logger.info("******* testGetBookingIdsByName starts... *******");
		String firstname=record.get("firstname");
		String lastname=record.get("lastname");
		Response response=BookingEndPoint.getBookingIdsByName(firstname, lastname);
//		response.then().log().all();
		
		JSONArray jArray=new JSONArray(response.asString());
		for(Object jo:jArray) {
			bookingid=((JSONObject) jo).getInt("bookingid");
			testGetBooking(record);
			
		}
		
		logger.info("******* testGetBookingIdsByName ended *******");
		
		
	}
	
	@Test(priority=5, dataProvider="getData")
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.NORMAL)
	public void testUpdateBooking(Map<String,String> record,ITestContext context) {
		
		logger.info("******* test updateBooking starts... *******");
		
		boolean depositpaid=Boolean.parseBoolean(record.get("depositpaidupdated"));
		String additionalneeds=record.get("additionalneedsupdated");
		
		booking.setDepositpaid(depositpaid);
		booking.setAdditionalneeds(additionalneeds);
		
		String token=context.getAttribute("token").toString();
		Response response=BookingEndPoint.updateBooking(bookingid, booking,token);
		
	
		
		// statuscode verification
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//statusline verification
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
		
		// Header verification
		Assert.assertEquals(response.header("Server"), "Cowboy");
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
		
		// Body content verification
		JSONObject jo=new JSONObject(response.asString());
		Assert.assertEquals(jo.getBoolean("depositpaid"),depositpaid);
		Assert.assertEquals(jo.getString("additionalneeds"), additionalneeds);
		
		// schema verification 
		MatcherAssert.assertThat(response.getBody().asString(),
									JsonSchemaValidator.matchesJsonSchema(bookingUpdatedSchema));
		logger.info("******* test updateBooking ended ********");
		
	}
	@Test(priority=6,dataProvider="getData")
	@Description("Verify statuscode, statusline, header, body elements and schema")
	@Severity(SeverityLevel.NORMAL)
	public void testPatchBooking(Map<String,String> record,ITestContext context) {
		
		logger.info("******* test PatchBooking starts... ********");
		
		String token=context.getAttribute("token").toString();
		
		int totalprice=Integer.parseInt(record.get("totalpriceupdated"));
		PartialBooking partialBooking=new PartialBooking(totalprice);
    	Response response=BookingEndPoint.partialUpdateBooking(bookingid, partialBooking, token);
//		response.then().log().all();
		
		// statuscode verification
		Assert.assertEquals(response.getStatusCode(), 200);
		
		// statusline verification
		Assert.assertEquals(response.statusLine(),"HTTP/1.1 200 OK");
		
		//header verification
		Assert.assertEquals(response.header("Server"), "Cowboy");
		Assert.assertEquals(response.header("Content-Type"), "application/json; charset=utf-8");
		
		//body verification 
		JSONObject jo=new JSONObject(response.asString());
		Assert.assertEquals(jo.getInt("totalprice"), totalprice);
		
		// schema verification
		MatcherAssert.assertThat(response.getBody().asString(),
								JsonSchemaValidator.matchesJsonSchema(bookingUpdatedSchema));
		logger.info("******* test PatchBooking ended ********");
		
	}
	
	@Test(priority=7)
	@Description("Verify statuscode, statusline, header, body")
	@Severity(SeverityLevel.NORMAL)
	public void testDeleteBooking(ITestContext context) {
		logger.info("******* test deleteBooking starts... ********");
		String token=context.getAttribute("token").toString();
		Response response=BookingEndPoint.deleteBooking(bookingid,token);
//		response.then().log().all();
		
		// statuscode verification
		Assert.assertEquals(response.getStatusCode(), 201);
		
		// satusline verification
		Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 201 Created");
		
		// header verification 
		Assert.assertEquals(response.getHeader("Server"), "Cowboy");
		Assert.assertEquals(response.getHeader("Content-Type"),"text/plain; charset=utf-8");
		
		// content body verification
	    Assert.assertEquals(response.getBody().asString(),"Created");
	
		logger.info("******* test deleteBooking ended ********");
		
	}
	
	
	@DataProvider
	public Object[] getData() {
		return ReadData.readFromExcelsheet();
	}
	
	
	
	
	

}
