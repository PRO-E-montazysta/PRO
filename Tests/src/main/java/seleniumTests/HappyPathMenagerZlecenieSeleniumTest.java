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

public class HappyPathMenagerZlecenieSeleniumTest {

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

        driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("Manager1");
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
    public void menagerOrdersHappyPathTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/orders\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/orders\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/orders\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/orders\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(1000);
// w tym miejscu trzeba dodać test dotyczący wyświetlania etapu zlecenia

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"formButton-edit\"]")));
        driver.findElement(By.xpath("//*[@id=\"formButton-edit\"]")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"mui-component-select-typeOfPriority\"]")));
        driver.findElement(By.xpath("//*[@id=\"mui-component-select-typeOfPriority\"]")).click();
        Thread.sleep(1000);
        Actions keyDown = new Actions(driver); keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"mui-component-select-foremanId\"]")).click();
        Thread.sleep(1000);
        Actions keyDown1 = new Actions(driver); keyDown1.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"formButton-save\"]")));
        driver.findElement(By.xpath("//*[@id=\"formButton-save\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialogGlobal-OK\"]")));
        driver.findElement(By.xpath("//*[@id=\"dialogGlobal-OK\"]")).click();
        Thread.sleep(1000);

        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);

    }

    @Test
    public void menagerUsterkiHappyPathTest() throws InterruptedException {


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/orders\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/orders\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/orders\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/orders\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navBtn-/events\"]")));
        driver.findElement(By.xpath("//*[@id=\"navBtn-/events\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"navMenu-/events\"]")));
        driver.findElement(By.xpath("//*[@id=\"navMenu-/events\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[3]/td[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[3]/td[1]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"formButton-edit\"]")));
        driver.findElement(By.xpath("//*[@id=\"formButton-edit\"]")).click();
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"description\"]")));
        driver.findElement(By.xpath("//*[@id=\"description\"]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"description\"]")).clear();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@id=\"description\"]")).sendKeys("magiczna usterka");
        Thread.sleep(1000);


        driver.findElement(By.xpath("//*[@id=\"mui-component-select-status\"]")).click();
        Thread.sleep(1000);
        Actions keyDown = new Actions(driver); keyDown.sendKeys(Keys.chord(Keys.DOWN,Keys.DOWN,Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"formButton-save\"]")));
        driver.findElement(By.xpath("//*[@id=\"formButton-save\"]")).click();
        Thread.sleep(1000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dialogGlobal-OK\"]")));
        driver.findElement(By.xpath("//*[@id=\"dialogGlobal-OK\"]")).click();
        Thread.sleep(1000);

        Thread.sleep(5000);

        driver.findElement(By.xpath("//*[@id=\"navBtn-logout\"]")).click();
        Thread.sleep(1000);

    }



}
