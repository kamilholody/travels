import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


public class SignUpTest {

    @Test
    public void signUp() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

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
        driver.quit();

    }

    @Test
    public void negativePathSignUp() {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

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
        driver.quit();

    }

    @Test
    public void signUpWithWrongMail() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10L, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("http://www.kurs-selenium.pl/demo/");

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
        WebElement heading = driver.findElement(By.xpath("//div[@class='alert alert-danger']//p"));

        Assert.assertTrue(heading.isDisplayed());
        Assert.assertEquals(heading.getText(), "The Email field must contain a valid email address.");
        driver.quit();
    }
}
