package testerTalks;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;

public class BaseTest {
	
	private static final Logger logger=LogManager.getLogger(BaseTest.class);
	
	@AfterMethod
	public void afterMethod(ITestResult result) {
		
		
		System.out.println("Name =>"+result.getName());
		
		if(result.getStatus()==ITestResult.FAILURE) {
			
			Throwable th=result.getThrowable();
			// convert th to a String
			
			StringWriter error=new StringWriter();
			th.printStackTrace(new PrintWriter(error));
			
			
			logger.info(error.toString());
			
			
		}
		
		if(result.getStatus()==ITestResult.SUCCESS) {
			logger.info(result.getName()+" functionality succeeded");
		}
		
		
		
		
	}

}
