package api.endpoints;

public class Routes {
	
	public static String baseUrl="https://restful-booker.herokuapp.com";
	
	//Auth Model 
	public static String post_auth_url=baseUrl+"/auth";
	
	// Booking Model
	public static String get_bookingids_url=baseUrl+"/booking";
	public static String get_booking_url=baseUrl+"/booking/{bookingId}";
	public static String post_createBooking_url=baseUrl+"/booking";
	public static String put_booking_url=baseUrl+"/booking/{bookingId}";
	public static String patch_booking_url=baseUrl+"/booking/{bookingId}";
	public static String delete_booking_url=baseUrl+"/booking/{bookingId}";
	
	// Ping 
	public static String get_healthCheck_url=baseUrl+"/ping";
	

}
