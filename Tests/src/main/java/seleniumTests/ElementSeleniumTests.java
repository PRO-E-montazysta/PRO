package seleniumTests;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ElementSeleniumTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() throws InterruptedException {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl/login");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"username\"]"))).click();

        driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("warehouseManager1");
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).clear();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("password");


        driver.findElement(By.xpath("//*[@id=\"login-logIn\"]")).click();
        Thread.sleep(2000);

    }
    @After
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void addElementTestSelenium() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/elements\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/elements\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/elements/new\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/elements/new\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("brama garażowa");



        driver.findElement(By.xpath("//*[@id=\"mui-component-select-typeOfUnit\"]")).click();
        Thread.sleep(1000);
        Actions keyDown = new Actions(driver); keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"quantityInUnit\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"quantityInUnit\"]")).sendKeys("1");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"formButton-save\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialogGlobal-OK\"]")));
        driver.findElement(By.xpath("//*[@id=\"dialogGlobal-OK\"]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);

    }

    @Test
    public void filterElementToolTestSelenium() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/elements\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/elements\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/elements\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/elements\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]/div/div")));
        driver.findElement(By.xpath("//*[@id=\"code\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"code\"]")).sendKeys("E|d71245de-e78e-48e5-9e1d-d37a7f3e59d0");

//
        driver.findElement(By.xpath("//*[@id=\"tableFilter-submit\"]")).click();

        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);

    }

    // test do poprawy po wprowadzeniu zmian na froncie i wyjaśnieniu kwestii Soft Delete
    //    @Test
    public void zDeleteElementTypeTestSelenium() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/elements\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/elements\"]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/elements\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/elements\"]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]/div/div")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("brama garażowa");
        Thread.sleep(2000);

//
        driver.findElement(By.xpath("//*[@id=\"tableFilter-submit\"]")).click();
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("//*[@id=\"formButton-delete\"]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialogGlobal-Usuń\"]")));
        driver.findElement(By.xpath("//*[@id=\"dialogGlobal-Usuń\"]")).click();
        Thread.sleep(2000);

//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
//        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();
//
//        Thread.sleep(5000);
//
//        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

}
