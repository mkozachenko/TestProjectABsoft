package Common;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Listeners({Listener.class})
public class BaseClass {
    public static int implicitWaitTimeout = 10;
    public static long explicitWaitTimeout = 15;
    public static Capabilities cap;
    public static Map<String, String> allureEnvMap = new HashMap<String, String>(){{
        put("URL", "[no data]");
        put("Browser", "[no data]");
        put("Platform", "[no data]");
    }};
    public static final String runURL = "https://cirosantilli-realworld-next.herokuapp.com/";
//    public static final String runURL = "https://angularjs.realworld.io/";
//    public static final String runURL = "https://demo.realworld.io";
    private static WebDriver chromeDriver = null;
    public Assertion assertion = new Assertion();
    public SoftAssert softAssert = new SoftAssert();

    @BeforeMethod(alwaysRun = true)
    public void startTest(){
        chromeDriver = startDriver();
        getDriver().get(runURL);
        cap = ((RemoteWebDriver) getDriver()).getCapabilities();
    }

    @AfterMethod(alwaysRun = true)
    public void finishTest(){
        getDriver().quit();
        new BaseClass().finishReport();
    }

    private void finishReport(){
        allureEnvMap.put("URL", runURL);
        allureEnvMap.put("Browser", cap.getBrowserName()+" v. "+cap.getVersion());
        allureEnvMap.put("Platform", System.getProperty("os.name").toLowerCase() + " (v. "+System.getProperty("os.version")+")");
        new Utility().setEnvironment(allureEnvMap);
    }

    private WebDriver startDriver(){
        ChromeOptions options = new ChromeOptions();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        options.addArguments("--ignore-certificate-errors", "use-fake-ui-for-media-stream", "enable-logging", "--no-sandbox");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        options.merge(capabilities);
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(implicitWaitTimeout, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(180, TimeUnit.SECONDS);
        return driver;
    }

    public WebDriver getDriver(){
        return chromeDriver;
    }

}
