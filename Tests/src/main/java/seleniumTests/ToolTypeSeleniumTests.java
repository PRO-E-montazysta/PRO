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
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofMillis(1000));

        driver.get("https://dev.emontazysta.pl/login");
        driver.manage().window().maximize();

        wait.until(ExpectedConditions.elementToBeClickable(By.id(":r0:"))).click();

        driver.findElement(By.id(":r0:")).sendKeys("warehouseManager1");

        driver.findElement(By.id(":r1:")).sendKeys("password");

        driver.findElement(By.cssSelector(".MuiButton-contained")).click();

    }
    @After
    public void tearDown(){
        driver.quit();
    }
    @Test
    public void addToolTypeTestSelenium() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[1]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/button")).click();

        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/div/li[2]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("//*[@id=\"name\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("screwdriver");

        driver.findElement(By.xpath("//*[@id=\"criticalNumber\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"criticalNumber\"]")).sendKeys("20");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/button[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void filterListToolTypeTestSelenium() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[1]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[1]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/div/li[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("screwdriver");
//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[2]/button[1]")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }

    @Test
    public void zDeleteToolTypeTestSelenium() throws InterruptedException {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[1]/button")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/header/div/div/div[1]/div/li[1]")));
        driver.findElement(By.xpath("/html/body/div/header/div/div/div[1]/div/li[1]")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[1]")));
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"name\"]")).sendKeys("screwdriver");
        Thread.sleep(2000);
//
        driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div/form/div[2]/button[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div[2]")));
        driver.findElement(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[1]/td[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div/div")));
        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/button[2]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button[1]")).click();
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div/div[2]/div")));
        driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div/button")).click();

        Thread.sleep(2000);

        driver.findElement(By.xpath("/html/body/div/header/div/div/button")).click();

    }
}
