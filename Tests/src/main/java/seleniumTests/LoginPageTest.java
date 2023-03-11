package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.FindBy;
import seleniumTests.util.BasePage;


public class LoginPageTest extends BasePage {
    public static WebDriver edgedriver = new EdgeDriver();

    private static final String login = "admin";
    private static final String password = "password";
//    @FindBy(id = ":r0:")
//    WebElement loginElement;


    @Test
    public void loginPage(){
        WebDriverManager.edgedriver().setup();

        edgedriver.get(baseUrl);
        WebElement loginElement = edgedriver.findElement(By.name("email"));
        clickElement(loginElement);
        loginElement.sendKeys(login);
        WebElement passwordElement = edgedriver.findElement(By.xpath("//*[@id=\":r1:\"]"));
        passwordElement.sendKeys(password);
        WebElement loginButtonElement = edgedriver.findElement(By.xpath("//*[@id=\"root\"]/div/form/div[3]/div[1]/button"));
        clickElement(loginButtonElement);

        edgedriver.close();
    }
}
