package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Properties p;
    private static final Logger logger = LogManager.getLogger(BaseClass.class);

    public static WebDriver getDriver() {
        return driver.get();
    }

    @BeforeClass(groups = { "sanity", "regression", "datadriven" })
    @Parameters({ "os", "browser" })
    public void openApp(String os, String br) {
        System.out.println("Running test with OS: " + os + ", Browser: " + br);

        try (FileReader file = new FileReader(".//src//test//resources//config.properties")) {
            p = new Properties();
            p.load(file);
            logger.debug("Loaded configuration properties");

            WebDriver localDriver = null;

            if (p.getProperty("execution_env").equalsIgnoreCase("remote")) {
                DesiredCapabilities capabilities = new DesiredCapabilities();

                if (os.equalsIgnoreCase("windows")) {
                    capabilities.setPlatform(Platform.WINDOWS);
                } else if (os.equalsIgnoreCase("mac")) {
                    capabilities.setPlatform(Platform.MAC);
                } else {
                    logger.error("Unsupported OS: {}", os);
                    return;
                }

                String gridURL = p.getProperty("gridURL");

                switch (br.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.merge(capabilities);
                        localDriver = new RemoteWebDriver(new URL(gridURL), chromeOptions);
                        break;

                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.merge(capabilities);
                        localDriver = new RemoteWebDriver(new URL(gridURL), firefoxOptions);
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        edgeOptions.merge(capabilities);
                        localDriver = new RemoteWebDriver(new URL(gridURL), edgeOptions);
                        break;

                    default:
                        logger.error("Unsupported remote browser: {}", br);
                        return;
                }

                logger.info("Remote WebDriver launched via Selenium Grid");

            } else if (p.getProperty("execution_env").equalsIgnoreCase("local")) {
                switch (br.toLowerCase()) {
                    case "chrome":
                        localDriver = new ChromeDriver();
                        break;
                    case "firefox":
                        localDriver = new FirefoxDriver();
                        break;
                    case "edge":
                        localDriver = new EdgeDriver();
                        break;
                    default:
                        logger.error("Unsupported local browser: {}", br);
                        return;
                }

                logger.info("Local WebDriver launched");
            }

            driver.set(localDriver);

            // ✅ Debug: Show which WebDriver is being used
            System.out.println("Using WebDriver class: " + getDriver().getClass().getName());

            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
            getDriver().manage().window().maximize();
            getDriver().get(p.getProperty("appURL"));

            logger.info("Application launched: {}", p.getProperty("appURL"));
            System.out.println("App launched successfully: " + p.getProperty("appURL"));

        } catch (IOException e) {
            logger.error("Failed to load config.properties", e);
        } catch (Exception e) {
            logger.error("Unexpected error during driver initialization", e);
            e.printStackTrace();
        }
    }

    @AfterClass(groups = { "sanity", "regression", "datadriven" })
    public void closeApp() {
        try {
            getDriver().quit();
            logger.info("Browser session closed successfully");
            System.out.println("Test completed. Browser closed.");
        } catch (Exception e) {
            logger.error("Failed to close browser session", e);
        }
    }

    public String captureScreen(String tname) {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String path = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";

        try {
            File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
            File target = new File(path);
            org.openqa.selenium.io.FileHandler.copy(src, target);
            logger.info("Screenshot captured: {}", path);
        } catch (Exception e) {
            logger.error("Screenshot capture failed", e);
        }

        return path;
    }
}
