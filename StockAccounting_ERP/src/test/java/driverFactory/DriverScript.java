package driverFactory;
import org.openqa.selenium.WebDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import commonFunctions.functionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
	public static WebDriver driver;

	String inputpath="D:\\lab1\\eclipse-workspace\\StockAccounting_ERP\\FileInput\\DataEngine.xlsx";
	String outputpath="D:\\lab1\\eclipse-workspace\\StockAccounting_ERP\\FileOutput\\HybridResult.xlsx";
	ExtentReports reports;
	ExtentTest test;

	public void startTest() throws Throwable
	{
		String moduleStatus="";
		//creating reference variable
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		//iterate all rows in master testcases sheet
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++)
		{
			if(xl.getCellData("MasterTestCases",i,2).equalsIgnoreCase("Y"))
			{
				//store corresponding sheet into TC_module variable
				String Tc_module=xl.getCellData("MasterTestCases", i, 1);

				//iterate all rows in TC_modue sheet
				for(int j=1;j<=xl.rowCount(Tc_module);j++)
				{			

					String Description=xl.getCellData(Tc_module, j, 0);
					String Object_Type=xl.getCellData(Tc_module, j, 1);
					String Locator_Type=xl.getCellData(Tc_module, j, 2);
					String  Locator_Value=xl.getCellData(Tc_module, j, 3);
					String Test_Data=xl.getCellData(Tc_module, j, 4);
					
					//Code to get the Extent reports
					reports=new ExtentReports("./ExtentReports/"+Description+Tc_module+functionLibrary.generateTime()+".html");
					test=reports.startTest(Tc_module);
					test.assignAuthor("Srikant");
					test.assignCategory("Functional");
					
					try{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=functionLibrary.startBrowser();
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("openApplication"))
						{
							functionLibrary.openApplication(driver);
							test.log(LogStatus.INFO, Description);
						}
						else if(Object_Type.equalsIgnoreCase("waitForElement"))
						{
							functionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							functionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							functionLibrary.clickAction(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							functionLibrary.validateTitle(driver, Test_Data);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							functionLibrary.closeBrowser(driver);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("mouseClick"))
						{
							functionLibrary.mouseClick(driver);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("categoryTable"))
						{
							functionLibrary.categoryTable(driver, Test_Data);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("captureData"))
						{
							functionLibrary.captureData(driver, Locator_Type, Locator_Value);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("SupplierTable"))
						{
							functionLibrary.supplierTable(driver);
							test.log(LogStatus.INFO,Description);
						}
						else if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							functionLibrary.customerTable(driver);
							test.log(LogStatus.INFO,Description);
						}



						//write as pass in status cell
						xl.setCellData(Tc_module, j, 5, "pass", outputpath);
						test.log(LogStatus.PASS,Description);
						moduleStatus="True";

					} catch (Exception e) 
					{
						System.out.println(e.getMessage());
						xl.setCellData(Tc_module, j, 5, "fail", outputpath);
						test.log(LogStatus.FAIL,Description); 
						moduleStatus="False";



					}
					if(moduleStatus.equalsIgnoreCase("True"))
					{
						xl.setCellData("MasterTestCases", i,3, "pass", outputpath);
						moduleStatus="True";

					}else
					{
						xl.setCellData("MasterTestCases", i, 3, "fail", outputpath);
					}
				}
				reports.endTest(test);
				reports.flush();
			}
			else
			{
				//which testcase flag to N write as Blocked in status cell
				xl.setCellData("MasterTestCases", i, 3,"Blocked", outputpath);
			}
		}


	}



}
