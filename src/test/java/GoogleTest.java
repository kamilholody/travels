import org.testng.annotations.Test;

public class GoogleTest {

    @Test
    public void googleSearchTest() {

        GoogleHomePage googleHomePage = new GoogleHomePage();
        googleHomePage.searchInGoogle("Selenium");
    }

    @Test
    public void googleSearchTest2() {

        GoogleHomePage googleHomePage = new GoogleHomePage();
        googleHomePage.searchInGoogle("Selenium");
    }

    @Test
    public void googleSearchTest3() {

        GoogleHomePage googleHomePage = new GoogleHomePage();
        googleHomePage.searchInGoogle("Selenium");
    }

}
