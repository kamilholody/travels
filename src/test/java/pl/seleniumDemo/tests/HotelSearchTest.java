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
        hotelSearchPage.setTravellers(1,2);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);

        List<String> hotelNames = resultsPage.getHotelNames();

        Assert.assertEquals("Jumeirah Beach Hotel", hotelNames.get(0));
        Assert.assertEquals("Oasis Beach Tower", hotelNames.get(1));
        Assert.assertEquals("Rose Rayhaan Rotana", hotelNames.get(2));
        Assert.assertEquals("Hyatt Regency Perth", hotelNames.get(3));

    }

    @Test
    public void searchHotelWithoutNameTest() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.setDates("24/07/2023", "31/07/2023");
        hotelSearchPage.setTravellers(0,1);
        hotelSearchPage.performSearch();

        ResultsPage resultsPage = new ResultsPage(driver);

        Assert.assertTrue(resultsPage.resultHeading.isDisplayed());
        Assert.assertEquals(resultsPage.getHeadingText(), "No Results Found");

    }
}
