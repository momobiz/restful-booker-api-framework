package api.payload;

public class Booking {
	
	String firstname;
	String lastname;
	int totalprice;
	boolean depositpaid;
	Bookingdates bookingdates;
	String additionalneeds;
	
	
	public Booking(String firstname,String lastname, int totalprice,boolean depositpaid,
			Bookingdates bookingdates, String additionalneeds) {
		
		setFirstname(firstname);
		setLastname(lastname);
		setTotalprice(totalprice);
		setDepositpaid(depositpaid);
		setBookingdates(bookingdates);
		setAdditionalneeds(additionalneeds);
		
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	public boolean isDepositpaid() {
		return depositpaid;
	}
	public void setDepositpaid(boolean depositpaid) {
		this.depositpaid = depositpaid;
	}
	public Bookingdates getBookingdates() {
		return bookingdates;
	}
	public void setBookingdates(Bookingdates bookingdates) {
		this.bookingdates = bookingdates;
	}
	public String getAdditionalneeds() {
		return additionalneeds;
	}
	public void setAdditionalneeds(String additionalneeds) {
		this.additionalneeds = additionalneeds;
	}
	

}
