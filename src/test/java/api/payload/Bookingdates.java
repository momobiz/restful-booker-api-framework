package api.payload;

public class Bookingdates {
	
	String checkin;
	String checkout;
	
	public Bookingdates(String checkin,String checkout) {
		setCheckin(checkin);
		setCheckout(checkout);
	}
	
	
	public String getCheckin() {
		return checkin;
	}
	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}
	public String getCheckout() {
		return checkout;
	}
	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	
	
	

}
