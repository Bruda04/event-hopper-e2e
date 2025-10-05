package booking.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.HomePage;
import shared.pages.LoginPage;
import shared.pages.SolutionPage;

public class BookingTests extends TestBase {

    @BeforeMethod
    public void setup(){
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.login(LoginPage.organizer3Username, LoginPage.organizer3Password);

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        Assert.assertTrue(homePage.containsCardWithTitle("Tech Consultation"),"Home page should contain 'Tech Consultation' card");
        homePage.openCardByTitle("Tech Consultation");

        SolutionPage solutionPage = new SolutionPage(driver);
        Assert.assertTrue(solutionPage.isPageOpened(), "Solution page is not opened");
        Assert.assertTrue(solutionPage.isBuyButtonPresent(), "Solution page should contain 'Buy' button");
        //dodati mozda proveru da li ima applicable evente
    }
    
    @Test(priority = 1)
    public void testSelectDate(){

    }
}
