package Utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import base.BaseTest;

public class TakeScreenshot extends BaseTest {

    public void takeScreenshot(String testCaseName) throws IOException {
        // Take screenshot with AppiumDriver
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String filePath = System.getProperty("user.dir") + "/screenshots/" + testCaseName + ".png";
        FileUtils.copyFile(screenshot, new File(filePath));

        // Attach screenshot to Extent Report
        extentTest.addScreenCaptureFromPath(filePath);
    }
}
