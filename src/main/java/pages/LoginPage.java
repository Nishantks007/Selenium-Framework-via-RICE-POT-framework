package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//input[@id='username']")
    private WebElement username;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement password;

    @FindBy(xpath = "//input[@id='Login']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@id='rememberUn']")
    private WebElement rememberMe;

    @FindBy(xpath = "//*[contains(@class,'error') or contains(@class,'errors') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'please check') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'invalid') or contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'incorrect')]")
    private java.util.List<WebElement> errorMessages;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String user) {
        try {
            wait.until(ExpectedConditions.visibilityOf(username));
            username.clear();
            username.sendKeys(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void enterPassword(String pass) {
        try {
            wait.until(ExpectedConditions.visibilityOf(password));
            password.clear();
            password.sendKeys(pass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clickLogin() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginButton.click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isErrorDisplayed() {
        try {
            for (WebElement el : errorMessages) {
                try {
                    if (wait.until(ExpectedConditions.visibilityOf(el)).isDisplayed()) return true;
                } catch (Exception ignore) {
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRememberMePresent() {
        try {
            return rememberMe.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
