package Pages;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BaseTest;
import io.appium.java_client.AppiumDriver;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LoginPage extends BaseTest{
	Robot robot;

	WebElement btn_Allow_Location;
	WebElement btn_Allow_Notifications;
	WebElement btn_Close_Notification;
	WebElement btn_Skip;
	WebElement txt_Enter_Phone_Number;
	WebElement btn_PhoneNumber_enter_next;
	WebElement txt_Enter_OTP;
	WebElement btn_OTP_Next;
	WebElement opt_Male;
	WebElement btn_Male_Next;
	WebElement click_On_Screen;
	
	
	//public AppiumDriver driver;
	
	public LoginPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
	
	public void btn_AllowLocation() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_Allow_Location")))).click();
	}
	
	public void btn_Allow_Notifications() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_Allow_Notifications")))).click();
	}
	
	public void btn_Close_Notification() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_Close_Notification")))).click();
	}
	
	public void btn_Skip() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_Skip")))).click();
	}
	
	public void enterPhoneNumber(String PhoneNumber) throws InterruptedException, AWTException {
		 
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("txt_Enter_Phone_Number"))))
				.click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(loc.getProperty("txt_Enter_Phone_Number")))).sendKeys(PhoneNumber+"1");
	    robot = new Robot();
		robot.keyPress(KeyEvent.VK_BACK_SPACE);
	}
	
	public void btn_PhoneNumber_enter_next() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_PhoneNumber_enter_next")))).click();
	}
	
	public void enterOTP(String OTP) throws AWTException, InterruptedException {

		for (int i = 0; i < OTP.length(); i++) {
			String txt_Enter_OTP = "//android.widget.EditText[starts-with(@resource-id, 'otp_" + i + "')]";
			
			WebElement otpField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(txt_Enter_OTP)));
			
			otpField.clear();
			otpField.sendKeys(String.valueOf(OTP.charAt(i)));
			
			robot.keyPress(KeyEvent.VK_TAB);
		}
	}
	
	public void btn_OTP_Next() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_OTP_Next")))).click();
	}
	
	public void opt_Male() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("opt_Male")))).click();
	}
	
	public void btn_Male_Next() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("btn_Male_Next")))).click();
	}
	
	public void click_On_Screen() {
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(loc.getProperty("click_On_Screen")))).click();
	}
	
}
