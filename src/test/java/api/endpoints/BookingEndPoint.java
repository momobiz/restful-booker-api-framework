package api.endpoints;
import static io.restassured.RestAssured.*;

import api.payload.Booking;
import api.payload.PartialBooking;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import listeners.RestAssuredListener;

public class BookingEndPoint {
	
	
	public static Response getBookingIds() {
		
		Response response=
					given()
						.filter(new AllureRestAssured())
						.filter(new RestAssuredListener())
					.when()
						.get(Routes.get_bookingids_url);
		return response;
		
	}
	
	public static Response getBookingIdsByName(String firstname,String lastname) {
		Response response=
				given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
					.queryParam("firstname", firstname)
					.queryParam("lastname", lastname)
				.when()
					.get(Routes.get_bookingids_url)
				;
		return response;
	}
	
	public static Response getBooking(int bookingId) {
		
		Response response=
					given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.pathParam("bookingId", bookingId)
					.when()
						.get(Routes.get_booking_url)
					;
		return response;
	}
	
	public static Response createBooking(Booking booking) {
		
		Response response=
					given()
						.filter(new AllureRestAssured())
						.filter(new RestAssuredListener())
						.contentType(ContentType.JSON)
						.body(booking)
					.when()
						.post(Routes.post_createBooking_url)
						
					;
		return response;
		
	}
	
	public static Response updateBooking(int bookingid, Booking booking, String token) {
		Response response=
					given()
						.filter(new AllureRestAssured())
						.filter(new RestAssuredListener())
						.contentType(ContentType.JSON)
						.header("Cookie", token)
						.pathParam("bookingId", bookingid)
						.body(booking)
					.when()
						.put(Routes.put_booking_url)
					;
		return response;
		
		
	}
	public static Response partialUpdateBooking(int bookingid, PartialBooking payload,String token) {
			Response response=
				given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.header("Cookie", token)
					.pathParam("bookingId", bookingid)
					.body(payload)
				.when()
					.patch(Routes.patch_booking_url)
				;
		return response;
		
	}
	public static Response deleteBooking(int bookingid,String token) {
		Response response=
				given()
					.filter(new AllureRestAssured())
					.filter(new RestAssuredListener())
					.contentType(ContentType.JSON)
					.header("Cookie", token)
					.pathParam("bookingId", bookingid)
				.when()
					.delete(Routes.delete_booking_url)
				;
		return response;
	}
	
	
}
