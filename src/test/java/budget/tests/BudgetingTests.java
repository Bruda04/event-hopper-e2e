package budget.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.HomePage;
import shared.pages.LoginPage;
import shared.pages.MyEventsPage;
import shared.pages.ProfilePage;
import utils.Helper;

public class BudgetingTests extends TestBase {
    @Test
    public void setup() {
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isPageOpened(), "Login page is not opened");
        loginPage.login(LoginPage.organizer3Username, LoginPage.organizer3Password);

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");
        homePage.clickOnProfileButton();


        ProfilePage profilePage = new ProfilePage(driver);
        Assert.assertTrue(profilePage.isPageOpened(), "Profile page is not opened");
        profilePage.clickOnMyEventsButton();

        MyEventsPage myEventsPage = new MyEventsPage(driver);
        Assert.assertTrue(myEventsPage.isPageOpened(), "My Events page is not opened");
        Assert.assertTrue(myEventsPage.hasAtLeastOneEvent(), "At least one event should be present");
//       myEventsPage.clickOnFirstEvent();



        Helper.takeScreenshoot(driver, "budgeting_tests_setup");
    }


}
