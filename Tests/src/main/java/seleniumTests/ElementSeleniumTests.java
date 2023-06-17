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
    public void setUp() {
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
    public void addElementTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/elements")));
        driver.findElement(By.id("navBtn-/elements")).click();

        driver.findElement(By.id("navMenu-/elements/new")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("brama garażowa");



        driver.findElement(By.id("mui-component-select-typeOfUnit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formSelect-typeOfUnit-opt-PIECE")));
        driver.findElement(By.id("formSelect-typeOfUnit-opt-PIECE")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("quantityInUnit")));

        Thread.sleep(2000);
        driver.findElement(By.id("quantityInUnit")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("quantityInUnit")).sendKeys("1");


        driver.findElement(By.id("formButton-save")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();


    }

    @Test
    public void filterElementToolTestSelenium() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/elements")));
        driver.findElement(By.id("navBtn-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/elements")));
        driver.findElement(By.id("navMenu-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("code")));
        driver.findElement(By.id("code")).click();
        driver.findElement(By.id("code")).sendKeys("E|62d245c1-ffb9-420c-9d91-17823ea6e12c");

        driver.findElement(By.id("tableFilter-submit")).click();

    }

    @Test
    public void zDeleteElementTypeTestSelenium() throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/elements")));
        driver.findElement(By.id("navBtn-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/elements")));
        driver.findElement(By.id("navMenu-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("brama garażowa");


        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(2000);
//        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
//        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("formButton-delete")));
        driver.findElement(By.id("formButton-delete")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-Usuń")));
        driver.findElement(By.id("dialogGlobal-Usuń")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dialogGlobal-OK")));
        driver.findElement(By.id("dialogGlobal-OK")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navBtn-/elements")));
        driver.findElement(By.id("navBtn-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("navMenu-/elements")));
        driver.findElement(By.id("navMenu-/elements")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys("brama garażowa");

        Thread.sleep(5000);

        driver.findElement(By.id("navBtn-logout")).click();
        Thread.sleep(1000);

    }

}
