package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
	
	public static Properties conprop;
	
	public static String getValueForKey(String key) throws Throwable
	{
		
		conprop=new Properties();
		conprop.load(new FileInputStream("D:\\lab1\\eclipse-workspace\\StockAccounting_ERP\\PropertyFiles\\environment.properties"));
		return conprop.getProperty(key);
	}

}
