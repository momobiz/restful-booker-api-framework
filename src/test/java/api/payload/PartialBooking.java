package api.payload;

public class PartialBooking {
	
	int totalprice;
	
	public PartialBooking(int totalPrice) {
		setTotalprice(totalPrice);
	}

	public int getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	

}
