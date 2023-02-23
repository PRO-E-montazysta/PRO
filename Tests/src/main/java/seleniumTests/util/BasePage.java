package seleniumTests.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {


    public static final String baseUrl = "https://dev.emontazysta.pl";
    public static WebDriver driver;



    public void navigateTo(String url) {
        driver.navigate().to(url);
    }

    public void clickElement(WebElement element) {
        element.click();
    }



}
