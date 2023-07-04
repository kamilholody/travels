package pl.seleniumDemo.pages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelSearchPage {

        @FindBy(xpath = "//span[text()='Search by Hotel or City Name']")
        private WebElement searchHotelSpan;

        @FindBy(xpath = "//div[@id='select2-drop']//input" )
        private WebElement searchHotelIntput;

        @FindBy(xpath = "//span[@class='select2-match' and text()='Dubai']" )
        private WebElement hotelMatch;

        @FindBy(name = "checkin")
        private WebElement checkinInput;

        @FindBy(name = "checkout")
        private WebElement checkoutInput;

        @FindBy(id = "travellersInput");
        private WebElement travellersInput;

        @FindBy(id = "adultPlusBtn");
        private WebElement adultPlusBtn;

        @FindBy(id = "childPlusBtn");
        private WebElement childPlusBtn;

        @FindBy(xpath = "//button[text()=' Search']");
        private WebElement searchButton;


        public void setCity(String cityName) {
            searchHotelSpan.click();
            searchHotelIntput.sendKeys(cityName);
            hotelMatch.click();
        }

        public void setDates(String checkin, String checkout) {
            checkinInput.sendKeys(checkin);
            checkoutInput.sendKeys(checkout);
        }

        public void setTravellers() {
            travellersInput.click();
            adultPlusBtn.click();
            childPlusBtn.click();
        }

        public void performSearch() {
            searchButton.click();
        }

}
