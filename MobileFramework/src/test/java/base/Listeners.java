package base;
 
import java.io.IOException;
 
import org.testng.ITestListener;
import org.testng.ITestResult;
 
import com.aventstack.extentreports.Status;
 
import Utilities.TakeScreenshot;
 
public class Listeners extends TakeScreenshot implements ITestListener {
 
	public void onTestStart(ITestResult result) {
		// Create a new test in the extent report
		extentTest = extentReports.createTest(result.getMethod().getMethodName());
 
		String description = result.getMethod().getDescription();
		if (description != null && !description.isEmpty()) {
			extentTest.log(Status.INFO, "Test Description: " + description);
		}
	}
 
	public void onTestSuccess(ITestResult result) {
		// Log success
		extentTest.log(Status.PASS, "Test passed");
	}
 
	public void onTestFailure(ITestResult result) {
 
		// Log failure
		extentTest.log(Status.FAIL, "Test failed");
 
		String assertionMsg = BaseTest.getAssertionMessage();
 
//		if (assertionMsg != null) {
//			extentTest.log(Status.FAIL, "Assertion Details : " + assertionMsg);
//
//		} else {
//			extentTest.log(Status.FAIL, "No assertion details available");
//		}
 
		// Capture exception details
		Throwable throwable = result.getThrowable();
		if (throwable instanceof AssertionError) {
			// Log only the assertion error details
			extentTest.log(Status.FAIL, "Assertion Failed: " + assertionMsg);
		} else {
			// Log other exceptions (like Selenium exceptions)
			extentTest.log(Status.FAIL, "Test failed with exception: " + throwable.getMessage());
			BaseTest.logExceptionToReport(new Exception(throwable));
		}
		assertionMessage.remove();
 
		String testCaseName = result.getMethod().getMethodName();
		try {
			takeScreenshot(testCaseName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertionMessage.remove();
	}
 
	public void onTestSkipped(ITestResult result) {
		// Log skipped tests
		extentTest.log(Status.SKIP, "Test skipped");
	}
 
//	public String getError(Throwable throwable) {
//		StringBuilder sb = new StringBuilder();
//		for (StackTraceElement element : throwable.getStackTrace()) {
//			sb.append(element.toString()).append("/n");
//		}
//
//		return sb.toString();
//	}
 
}