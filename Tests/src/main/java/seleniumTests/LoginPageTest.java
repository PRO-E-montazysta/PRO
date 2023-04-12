package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumTests.util.BasePage;

import java.time.Duration;


public class LoginPageTest extends BasePage {
    public static WebDriver edgedriver = new EdgeDriver();
    public WebDriverWait wait = new WebDriverWait(edgedriver, Duration.ofSeconds(5));




    @Test
    public void loginPage() throws InterruptedException {
        WebDriverManager.edgedriver().setup();

        edgedriver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement loginElement = edgedriver.findElement(By.name("email"));


        clickElement(loginElement);
        loginElement.sendKeys(getLogin());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\":r1:\"]")));
        WebElement passwordElement = edgedriver.findElement(By.xpath("//*[@id=\":r1:\"]"));
        passwordElement.sendKeys(getPassword());

        WebElement loginButtonElement = edgedriver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[3]/div[1]/button"));
        clickElement(loginButtonElement);


        Thread.sleep(2000);

        edgedriver.close();
    }
}
