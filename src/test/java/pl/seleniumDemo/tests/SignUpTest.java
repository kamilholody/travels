package pl.seleniumDemo.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumDemo.pages.HotelSearchPage;
import pl.seleniumDemo.pages.LoggedUserPage;
import pl.seleniumDemo.pages.SignUpPage;

import java.util.List;
import java.util.stream.Collectors;


public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {

        String lastName = "Testowy";
        int randomNumber = (int) (Math.random() * 1000);
        String email = "testerkh" + randomNumber + "@tester.pl";

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSingUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Kamil");
        signUpPage.setLastName(lastName);
        signUpPage.setPhone("12323232");
        signUpPage.setEmail(email);
        signUpPage.setPassword("Test123");
        signUpPage.setConfirmPassword("Test123");
        signUpPage.signUp();

        LoggedUserPage loggedUserPage = new LoggedUserPage(driver);


        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Kamil Testowy");


    }

    @Test
    public void signUpEmptyFormTest() {
        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSingUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.signUp();

        List<String> errors = signUpPage.getErrors();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(errors.contains("The Email field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The Password field is required."));
        softAssert.assertTrue(errors.contains("The First name field is required."));
        softAssert.assertTrue(errors.contains("The Last Name field is required."));
        softAssert.assertAll();

    }

    @Test
    public void signUpInvalidEmail() {

        HotelSearchPage hotelSearchPage = new HotelSearchPage(driver);
        hotelSearchPage.openSingUpForm();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.setFirstName("Kamil");
        signUpPage.setLastName("Testowy");
        signUpPage.setEmail("email");
        signUpPage.setPhone("12323232");
        signUpPage.setPassword("Test123");
        signUpPage.setConfirmPassword("Test123");
        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));

    }
}
