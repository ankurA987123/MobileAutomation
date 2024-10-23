package Testcases;

import java.awt.AWTException;
import java.io.IOException;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import Pages.LoginPage;
import base.BaseTest;
import io.appium.java_client.AppiumDriver;

public class Login extends BaseTest{
	LoginPage login;
	
	@Test(dataProviderClass = Utilities.ReadXLData.class, dataProvider = "testData")
	public void LoginData(String PhoneNumber,String OTP) throws InterruptedException, IOException, AWTException {

		login = new LoginPage(driver);
		login.btn_AllowLocation();
		login.btn_Allow_Notifications();
		login.btn_Close_Notification();
		login.btn_Skip();
		login.enterPhoneNumber(PhoneNumber);
		login.btn_PhoneNumber_enter_next();
		login.enterOTP(OTP);
		login.btn_OTP_Next();
		login.opt_Male();
		login.btn_Male_Next();
    }
}
