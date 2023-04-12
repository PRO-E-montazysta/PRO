package seleniumTests.util;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {


    public static final String baseUrl = "https://dev.emontazysta.pl";
    @Getter
    private static final String login = "admin";
    @Getter
    private static final String password = "password";
    public static WebDriver driver;



    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }



}
