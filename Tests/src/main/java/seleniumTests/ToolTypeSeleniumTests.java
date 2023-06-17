package seleniumTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ToolTypeSeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl/login");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("username"))).click();

        driver.findElement(By.id("username")).sendKeys("warehouseManager1");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement passwordElement = driver.findElement((By.id("password")));
        passwordElement.clear();
        passwordElement.sendKeys("password");


        driver.findElement(By.id("login-logIn")).click();

    }
    @After
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void addToolTypeTestSelenium() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/tooltypes\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/tooltypes\"]")).click();

        driver.findElement(By.xpath("//*[@id=\"navMenu-/tooltypes/new\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"name\"]")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("screwdriver");

        driver.findElement(By.xpath("//*[@id=\"criticalNumber\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"criticalNumber\"]")).sendKeys("20");

        driver.findElement(By.xpath("//*[@id=\"formButton-save\"]")).click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialogGlobal-OK\"]")));
        driver.findElement(By.xpath("//*[@id=\"dialogGlobal-OK\"]")).click();
        Thread.sleep(5000);
        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);


    }

    @Test
    public void filterListToolTypeTestSelenium() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/tooltypes\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/tooltypes\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/tooltypes\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/tooltypes\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"name\"]")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("screwdriver");
//
        driver.findElement(By.xpath("//*[@id=\"tableFilter-submit\"]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);

    }


     @Test
    public void zDeleteToolTypeTestSelenium() throws InterruptedException {

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/tooltypes")));
         driver.findElement(By.id("navBtn-/tooltypes")).click();
         Thread.sleep(1000);
         driver.findElement(By.id("navMenu-/tooltypes")).click();
         Thread.sleep(1000);

         driver.findElement(By.id("name")).click();
         driver.findElement(By.id("name")).sendKeys("screwdriver");

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tableFilter-submit")));
         driver.findElement(By.id("tableFilter-submit")).click();

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
         driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-delete")));
         driver.findElement(By.id("formButton-delete")).click();
         Thread.sleep(1000);

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-Usuń")));
         driver.findElement(By.id("dialogGlobal-Usuń")).click();
         Thread.sleep(1000);

         wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
         driver.findElement(By.id("dialogGlobal-OK")).click();
         Thread.sleep(1000);

         driver.findElement(By.id("navBtn-/tooltypes")).click();
         driver.findElement(By.id("navMenu-/tooltypes")).click();


         driver.findElement(By.id("name")).click();
         driver.findElement(By.id("name")).clear();
         driver.findElement(By.id("name")).sendKeys("screwdriver");

         Thread.sleep(5000);

         driver.findElement(By.id("navBtn-logout")).click();
         Thread.sleep(1000);

    }
}
