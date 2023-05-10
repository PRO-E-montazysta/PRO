package seleniumTests.WarehousesScreens;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;


public class AddWarehouseTest {
        private WebDriver driver;
        EdgeOptions options = new EdgeOptions();
        public WebDriverWait wait;

    @Before
        public void setUp() {

            options.addArguments("--remote-allow-origins=*");

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver(options);
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            driver.get("https://dev.emontazysta.pl/login");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement loginElement = driver.findElement(By.name("username"));

            loginElement.click();
            loginElement.sendKeys("companyAdmin1");

            WebElement loginButtonElement = driver.findElement(By.id("login-logIn"));
            loginButtonElement.click();
        }
        @After
        public void tearDown() {
            driver.quit();
        }
        @Test
        public void dodanieMagazynu()  {

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/warehouses")));
            driver.findElement(By.id("navBtn-/warehouses")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/warehouses/new")));
            driver.findElement(By.id("navMenu-/warehouses/new")).click();

            driver.findElement(By.id("name")).click();
            driver.findElement(By.id("name")).sendKeys("Testowy Magazyn 1");

            driver.findElement(By.id("description")).click();
            driver.findElement(By.id("description")).sendKeys("Magazyn stworzony do testów");

            driver.findElement(By.id("openingHours")).click();
            driver.findElement(By.id("openingHours")).sendKeys("05:00-22:00");
            driver.findElement(By.id("locationId")).click();
            driver.findElement(By.id("locationId")).sendKeys("ul. Konopna 12, 05-250 Radzymin");

            driver.findElement(By.id("formButton-save")).click();
        }

    @Test
        public void formReset() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/warehouses")));
        driver.findElement(By.id("navBtn-/warehouses")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/warehouses/new")));
        driver.findElement(By.id("navMenu-/warehouses/new")).click();

        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("Testowy Magazyn 1");

        driver.findElement(By.id("description")).click();
        driver.findElement(By.id("description")).sendKeys("Magazyn stworzony do testów");

        driver.findElement(By.id("openingHours")).click();
        driver.findElement(By.id("openingHours")).sendKeys("05:00-22:00");
        driver.findElement(By.id("locationId")).click();
        driver.findElement(By.id("locationId")).sendKeys("ul. Konopna 12, 05-250 Radzymin");

        Thread.sleep(1000);
        driver.findElement(By.id("formButton-reset")).click();
        Thread.sleep(1000);

    }

}
