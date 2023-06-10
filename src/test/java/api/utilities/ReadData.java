package api.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ReadData {

	public static Object[] readFromExcelsheet() {
		Map<String,String> testData=null;
		List<Map<String,String>> testDataList=null;
		
		Fillo fillo=new Fillo();
		Recordset recordset=null;
		Connection connection=null;
		String query="select * from Sheet1";
		Object[] objectArray=null;
		
		try {
			connection=fillo.getConnection(Path.pathToData);
			recordset=connection.executeQuery(query);
			testDataList=new ArrayList<Map<String,String>>();
			while(recordset.next()){
				testData=new TreeMap<String,String>(String.CASE_INSENSITIVE_ORDER);
				for(String field:recordset.getFieldNames()) {
					testData.put(field,recordset.getField(field));
				}
				testDataList.add(testData);
				
			}
			objectArray=new Object[testDataList.size()];
			for(int i=0;i<objectArray.length;i++) {
				objectArray[i]=testDataList.get(i);
				
			}
			
			
			
		} catch (FilloException e) {
			
			e.printStackTrace();
		}
		
	
		return objectArray;
		
	}
	public static String readSchema(String schemaPath) {
		
		String bookingSchema=null;
		
		try {
			bookingSchema= FileUtils.readFileToString(new File(schemaPath),"UTF-8");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return bookingSchema;
		
	}

}
