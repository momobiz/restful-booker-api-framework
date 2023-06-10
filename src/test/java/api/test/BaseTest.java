package api.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import api.payload.Admin;
import api.payload.Booking;
import api.payload.Bookingdates;
import api.utilities.Path;
import api.utilities.ReadData;

public class BaseTest {
	Admin admin;
	Booking booking;
	String bookingSchemaGetRequest=ReadData.readSchema(Path.schemaBookGetRequestPath);
	String bookingSchema=ReadData.readSchema(Path.schemaBookingPath);
	String schemaAuth=ReadData.readSchema(Path.schemaAuth);
	String bookingUpdatedSchema=ReadData.readSchema(Path.bookingUdatedSchemaPath);
	public Logger logger=LogManager.getLogger(BaseTest.class);
	
	@BeforeClass
	public void setupData() {
		admin=new Admin("admin","password123");
		
		
	}
	@AfterMethod
	public void afterMetod(ITestResult result) {
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			logger.info("==> "+result.getName()+ " completely runned with success");
			
		}else if(result.getStatus()==ITestResult.FAILURE) {
			logger.info("==> "+result.getName()+" failed");
			
			Throwable th=result.getThrowable();
			StringWriter error=new StringWriter();
			th.printStackTrace(new PrintWriter(error));
			
			logger.error(error.toString());
			
		}
		
	}

}
