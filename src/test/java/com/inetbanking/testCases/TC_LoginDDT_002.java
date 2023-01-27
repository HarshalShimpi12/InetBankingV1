package com.inetbanking.testCases;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;

import org.junit.Assert;
import org.openqa.selenium.NoAlertPresentException;

public class TC_LoginDDT_002 extends BaseClass
{
	@Test (dataProvider="LoginData")
	public void loginDDT(String user ,String pwd) throws InterruptedException
	{
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(user);
		logger.info("User Name is Provided");
		lp.setPassword(pwd);
		logger.info("Password is Provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		if (isAlertPresent()==true)
		{
			driver.switchTo().alert().accept(); //close Alert
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("Login Failed");
		}
		else
		{
			Assert.assertTrue(true);
			logger.info("Login Passed");
			
			lp.clickLogout();
			Thread.sleep(3000);
			
			driver.switchTo().alert().accept();  //close logout alert
			driver.switchTo().defaultContent();
		}
			
	}
	
	public boolean isAlertPresent()		//USER DEFINE METHOD CREATED TO CHECK ALERT IS PRESENT OR NOT
	{
		try 
		{
		driver.switchTo().alert();
		return true;
		}
		catch(NoAlertPresentException e)
		{
			return false;
		}
	}
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException
	{
		String path=System.getProperty("user.dir")+"/src/test/java/com/inetbanking/testData/LoginData.xlsx";
		//String path="C:\\Users\\Harshal Home\\Desktop\\DETA\\inetBankingV1\\src\\test\\java\\com\\inetbanking\\testData\\LoginData.xlsx";

		
		int rownum=XLUtils.getRowCount(path,"Sheet1");
		int cocount=XLUtils.getCellCount(path,"Sheet1", 1);
		
		String logindata[][]=new String [rownum][cocount];
		for(int i=1; i<=rownum; i++)
		{
			for(int j=0; j<cocount; j++)
			{
				logindata[i-1][j]=XLUtils.getCellData(path, "Sheet1", i, j);
			}
		}
	return logindata;
	}

}
