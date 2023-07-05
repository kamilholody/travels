package pl.seleniumDemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pl.seleniumDemo.pages.HotelSearchPage;
import pl.seleniumDemo.pages.ResultsPage;

import java.util.List;
import java.util.stream.Collectors;

public class HotelSearchTest extends BaseTest {


    @Test
    public void searchHotelTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setCity("Dubai");
        hotelSearchPage.setDates("27/04/2023", "29/04/2023");
        hotelSearchPage.setTravellers();
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);

        List<String> hotelNames = resultsPage.getHotelNames();

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));

    }

    @Test
    public void negativePathSearchHotelTest() {

        driver.findElement(By.name("checkin")).sendKeys("24/07/2023");
        driver.findElement(By.name("checkout")).click();
        driver.findElements(By.xpath("//th[@class='next']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);
        driver.findElements(By.xpath("//td[@class='day ' and text()='31']"))
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .ifPresent(WebElement::click);

        driver.findElement(By.name("travellers")).click();
        driver.findElement(By.id("childPlusBtn")).click();
        driver.findElement(By.xpath("//button[@type='submit' and text()=' Search']")).click();

        WebElement message = driver.findElement(By.xpath("//h2[text()='No Results Found']"));

        Assert.assertTrue(message.isDisplayed());
        Assert.assertEquals(message.getText(), "No Results Found");

    }
}
