package base;
 
import java.io.FileReader;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.Set;
 
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
 
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
 
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.github.bonigarcia.wdm.WebDriverManager;
 
public class BaseTest {
 
    public static AppiumDriver driver;
    public static Properties prop = new Properties();
    public static Properties loc = new Properties();
    public static FileReader frprop;
    public static FileReader frloc;
    public static SoftAssert softAssert;
    public static WebDriverWait wait;
    public static ExtentReports extentReports;
    public static ExtentTest extentTest;
    public static ExtentSparkReporter sparkReporter;
    public static ThreadLocal<String> assertionMessage;
 
    @BeforeSuite
    public void setupReport() {
        // Initialize ExtentSparkReporter
        sparkReporter = new ExtentSparkReporter("extent-report.html");
        extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        sparkReporter.config().setDocumentTitle("Test Report");
        sparkReporter.config().setReportName("Selenium Test Report");
    }
 
    @BeforeMethod
    public void setup() throws Exception {
        if (driver == null) {
            frprop = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\configFiles\\config.properties");
            prop.load(frprop);
 
            frloc = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\configFiles\\locators.properties");
            loc.load(frloc);
 
            setUp(); // Call setUp to initialize Appium driver
 
            // Initialize WebDriver for other browsers if needed
            // (Uncomment if using other browsers)
            /* if (prop.getProperty("browser").equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } 
            // Additional browser setups...
            driver.manage().window().maximize();
            driver.get(prop.getProperty("testURL"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(20)); */
        }
    }
 
    public void setUp() throws Exception {
        try {
        	DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, prop.getProperty("platformName")); // or "iOS"
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, prop.getProperty("deviceName")); // set your device name
            capabilities.setCapability(MobileCapabilityType.APP, prop.getProperty("app")); // path to your app
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("automationName"));
      /*      
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android"); // or "iOS"
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator"); // set your device name
            capabilities.setCapability(MobileCapabilityType.APP, "D:\\apk_file\\app-debug-23.apk"); // path to your app
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
*/
            driver = new AppiumDriver(new URL("http://localhost:4723/"), capabilities);
        } catch (Exception e) {
            System.err.println("Failed to initialize Appium driver: " + e.getMessage());
            throw e; // Rethrow the exception if needed
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
 
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
   //         driver.quit(); // Close the Appium session
            driver = null; // Set driver to null to ensure it's re-initialized in the next test
        } else {
            System.out.println("Driver is null, skipping close.");
        }
    }
 
    @AfterSuite
    public void tearDownReport() {
        extentReports.flush(); // Flush the report
    }
 
    public static String getAssertionMessage() {
        return assertionMessage.get();
    }
 
    public static void scrollPageDown() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.PAGE_DOWN).build().perform();
    }
 
    public static void switchToNewTab() {
        String originalWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for (String windowHandle : allWindows) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }
 
    // Method to log any caught exception into Extent Reports
    public static void logExceptionToReport(Exception e) {
        extentTest.log(Status.FAIL, "Exception occurred: " + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()) {
            extentTest.log(Status.FAIL, element.toString());
        }
    }
}