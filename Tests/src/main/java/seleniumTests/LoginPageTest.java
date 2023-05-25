package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.util.BasePage;

import java.time.Duration;


public class LoginPageTest extends BasePage {
    public static WebDriver firefoxDriver = new FirefoxDriver();
    public WebDriverWait wait = new WebDriverWait(firefoxDriver, Duration.ofSeconds(5));




    @Test
    public void loginPage() throws InterruptedException {
        WebDriverManager.edgedriver().setup();

        firefoxDriver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement loginElement = firefoxDriver.findElement(By.name("username"));


        clickElement(loginElement);
        loginElement.sendKeys(getLogin());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement passwordElement = firefoxDriver.findElement((By.id("password")));
        passwordElement.clear();
        passwordElement.sendKeys(getPassword());

        WebElement loginButtonElement = firefoxDriver.findElement(By.id("login-logIn"));
        clickElement(loginButtonElement);


        Thread.sleep(2000);

        firefoxDriver.close();
    }
}
