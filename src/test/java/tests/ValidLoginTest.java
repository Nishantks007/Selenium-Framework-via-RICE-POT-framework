package tests;

import pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class ValidLoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            driver.get("https://login.salesforce.com/?locale=in");
            loginPage = new LoginPage(driver);
        } catch (Exception e) {
            if (driver != null) driver.quit();
            throw e;
        }
    }

    @Test
    public void testValidLogin() {
        String user = System.getProperty("sf.username", "");
        String pass = System.getProperty("sf.password", "");
        if (user.isEmpty() || pass.isEmpty() || "placeholder_user".equals(user) || "placeholder_pass".equals(pass)) {
            throw new SkipException("Valid credentials not provided; skipping valid login test");
        }
        try {
            loginPage.enterUsername(user);
            loginPage.enterPassword(pass);
            loginPage.clickLogin();
            boolean loggedIn;
            try {
                loggedIn = wait.until(d -> d.getCurrentUrl().contains("home") || d.getCurrentUrl().contains("lightning"));
            } catch (Exception e) {
                loggedIn = false;
            }
            Assert.assertTrue(loggedIn, "Expected to be logged in and redirected to home/lightning URL");
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @AfterTest
    public void tearDown() {
        try {
            if (driver != null) driver.quit();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
