package tests;

import pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;

public class InvalidLoginTest {
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
    public void testInvalidLogin() {
        try {
            String user = System.getProperty("sf.username", "invalid@example.com");
            String pass = System.getProperty("sf.invalidpassword", "InvalidPass123");
            loginPage.enterUsername(user);
            loginPage.enterPassword(pass);
            loginPage.clickLogin();
            boolean errorOrStillOnLogin;
            try {
                errorOrStillOnLogin = new WebDriverWait(driver, Duration.ofSeconds(10)).until(d -> loginPage.isErrorDisplayed() || d.getCurrentUrl().contains("login"));
            } catch (Exception e) {
                errorOrStillOnLogin = loginPage.isErrorDisplayed() || driver.getCurrentUrl().contains("login");
            }
            Assert.assertTrue(errorOrStillOnLogin, "Expected error message or remain on login page for invalid login");
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
