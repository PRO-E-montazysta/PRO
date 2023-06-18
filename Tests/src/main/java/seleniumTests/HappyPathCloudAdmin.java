package seleniumTests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

public class HappyPathCloudAdmin {
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

        driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("admin");
        Thread.sleep(1000);

        driver.findElement(By.xpath("//*[@id=\"login-logIn\"]")).click();
        Thread.sleep(2000);

    }

    @After
    public void after(){
        driver.close();
    }

    @Test
    public void addNewCompany(){
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navBtn-/companies"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("navMenu-/companies/new"))).click();

        WebElement companyName = driver.findElement(By.id("companyName"));
        companyName.click();
        companyName.sendKeys("Firma testowa automatyczna");

        WebElement statusReason = driver.findElement(By.id("statusReason"));
        statusReason.click();
        statusReason.sendKeys("Firma testowa automatyczna zrobiona do testów");

        driver.findElement(By.xpath("/html/body/div/div/div/div[2]/div/div/button[2]")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("firstName"))).click();

        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.click();
        firstName.sendKeys("Jan");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.click();
        lastName.sendKeys("Testowy");

        WebElement email = driver.findElement(By.id("email"));
        email.click();
        email.sendKeys("s20439@pjwstk.edu.pl");

        WebElement password = driver.findElement(By.id("password"));
        password.click();
        password.sendKeys("P@ssw0rd1!");

        WebElement username = driver.findElement(By.id("username"));
        username.click();
        Random random = new Random();
        random.ints();
        username.sendKeys("jantestowy"+ random.ints());

        WebElement phone = driver.findElement(By.id("phone"));
        phone.click();
        phone.sendKeys("+48797162263");

        WebElement pesel = driver.findElement(By.id("pesel"));
        pesel.click();
        pesel.sendKeys("99090122695");

        WebElement save = driver.findElement(By.id("formButton-save"));
        save.click();


    }

    @Test
    public void editCompany() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("navBtn-/companies"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("navMenu-/companies"))).click();

       wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div/div/div[2]/div/div/table/tbody/tr[5]/td[1]"))).click();
       wait.until(ExpectedConditions.elementToBeClickable(By.id("formButton-edit"))).click();

       WebElement status = driver.findElement(By.id("mui-component-select-status"));
       status.click();
       driver.findElement(By.id("formSelect-status-opt-DISABLED")).click();

       driver.findElement(By.cssSelector("body")).click();
       Thread.sleep(1000);

       driver.findElement(By.id("formButton-save")).click();


    }
}
