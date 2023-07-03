import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {



        String lastName = "Testowy";
        int randomNumber = (int) (Math.random()*1000);
        String email = "testerkh" + randomNumber + "@tester.pl";
        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("Kamil");
        driver.findElement(By.name("lastname")).sendKeys(lastName);
        driver.findElement(By.name("phone")).sendKeys("511222323");
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.name("password")).sendKeys("Test123!");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[@type='submit' and text()=' Sign Up']")).click();
        WebElement heading = driver.findElement(By.xpath("//h3[@class='RTL']"));

        Assert.assertTrue(heading.getText().contains(lastName));
        Assert.assertEquals(heading.getText(), "Hi, Kamil Testowy");


    }

    @Test
    public void negativePathSignUpTest() {

        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                        .stream()
                        .filter(WebElement::isDisplayed)
                        .findFirst()
                        .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.xpath("//button[@type='submit' and text()=' Sign Up']")).click();
        List<String> heading = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p"))
                .stream()
                .map(el -> el.getAttribute("textContent"))
                .collect(Collectors.toList());

        Assert.assertEquals("The Email field is required.", heading.get(0));
        Assert.assertEquals("The Password field is required.", heading.get(1));
        Assert.assertEquals("The Password field is required.", heading.get(2));
        Assert.assertEquals("The First name field is required.", heading.get(3));
        Assert.assertEquals("The Last Name field is required.", heading.get(4));

        // miękka asercja
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(heading.contains("The Email field is required."));
        softAssert.assertTrue(heading.contains("The Password field is required."));
        softAssert.assertTrue(heading.contains("The Password field is required."));
        softAssert.assertTrue(heading.contains("The First name field is required."));
        softAssert.assertTrue(heading.contains("The Last Name field is required."));
        softAssert.assertAll();

    }

    @Test
    public void signUpWithWrongMailTest() {

        driver.findElements(By.xpath("//li[@id='li_myaccount']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//a[text()='  Sign Up']")).get(1).click();
        driver.findElement(By.name("firstname")).sendKeys("Kamil");
        driver.findElement(By.name("lastname")).sendKeys("Testowy");
        driver.findElement(By.name("phone")).sendKeys("511222323");
        driver.findElement(By.name("email")).sendKeys("test.pl");
        driver.findElement(By.name("password")).sendKeys("Test123!");
        driver.findElement(By.name("confirmpassword")).sendKeys("Test123!");
        driver.findElement(By.xpath("//button[@type='submit' and text()=' Sign Up']")).click();

        List<String> heading = driver.findElements(By.xpath("//div[@class='alert alert-danger']//p"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertTrue(heading.contains("The Email field must contain a valid email address."));

    }
}
