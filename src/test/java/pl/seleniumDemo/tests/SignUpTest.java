package pl.seleniumDemo.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pl.seleniumDemo.pages.HotelSearchPage;
import pl.seleniumDemo.pages.LoggedUserPage;
import pl.seleniumDemo.pages.SignUpPage;

import java.util.List;


public class SignUpTest extends BaseTest {

    @Test
    public void signUpTest() {

        String lastName = "Testowy";
        int randomNumber = (int) (Math.random() * 1000);

        LoggedUserPage loggedUserPage = new HotelSearchPage(driver)
                .openSingUpForm()
                .setFirstName("Kamil")
                .setLastName(lastName)
                .setPhone("511233457")
                .setEmail("testerkh" + randomNumber + "@tester.pl")
                .setPassword("Test123!")
                .setConfirmPassword("Test123!")
                .signUp();


        Assert.assertTrue(loggedUserPage.getHeadingText().contains(lastName));
        Assert.assertEquals(loggedUserPage.getHeadingText(), "Hi, Kamil Testowy");


    }

    @Test
    public void signUpEmptyFormTest() {

        SignUpPage signUpPage = new HotelSearchPage(driver).openSingUpForm();
        signUpPage.signUp();

        List<String> heading = signUpPage.getErrors();

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

        SignUpPage signUpPage = new HotelSearchPage(driver)
                .openSingUpForm()
                .setFirstName("Kamil")
                .setLastName("Testowy")
                .setPhone("511233457")
                .setEmail("email")
                .setPassword("Test123!")
                .setConfirmPassword("Test123!");

        signUpPage.signUp();

        Assert.assertTrue(signUpPage.getErrors().contains("The Email field must contain a valid email address."));

    }
}
