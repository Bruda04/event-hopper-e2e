package budget.tests;


import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.*;

public class BudgetingTests extends TestBase {

    private MyEventsPage myEventsPage;

    @BeforeMethod
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

        myEventsPage = new MyEventsPage(driver);
        Assert.assertTrue(myEventsPage.isPageOpened(), "My Events page is not opened");
        Assert.assertTrue(myEventsPage.hasAtLeastOneEvent(), "At least one event should be present");
    }

    @Test(priority = 1)
    public void testAddBudgetingItem() {
        Assert.assertTrue(myEventsPage.containsEventWithTitle("Tech Meetup"), "Event 'Tech Meetup' should be present");
        myEventsPage.openEventCardByTitle("Tech Meetup");

        SingleEventPage singleEventPage = new SingleEventPage(driver);
        Assert.assertTrue(singleEventPage.isPageOpened(), "Single Event page is not opened");
        singleEventPage.clickEditBudgetButton();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened");

        budgetingPage.addCategory(0, 5000);

        budgetingPage.clickSaveButton();
        Assert.assertEquals(budgetingPage.numOfAddedCategories(), 1, "Number of added categories should be 1");
        Assert.assertEquals(budgetingPage.getLeftToSpendAmount(), "5000 €", "Left to spend amount should be 8000 €");
    }

    @Test(priority = 2, dependsOnMethods = {"testAddBudgetingItem"})
    public void testAddMultipleBudgetingItems() {
        Assert.assertTrue(myEventsPage.containsEventWithTitle("Spring Fair"), "Event 'Spring Fair' should be present");
        myEventsPage.openEventCardByTitle("Spring Fair");

        SingleEventPage singleEventPage = new SingleEventPage(driver);
        Assert.assertTrue(singleEventPage.isPageOpened(), "Single Event page is not opened");
        singleEventPage.clickEditBudgetButton();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened");

        budgetingPage.addMultipleCategories(new int[]{0, 1}, new int[]{5000, 100});

        budgetingPage.clickSaveButton();
        Assert.assertEquals(budgetingPage.numOfAddedCategories(), 2, "Number of added categories should be 2");
        Assert.assertEquals(budgetingPage.getLeftToSpendAmount(), "5100 €", "Left to spend amount should be 5100 €");
    }

    @Test(priority = 3, dependsOnMethods = {"testAddMultipleBudgetingItems"})
    public void testRemoveBudgetingItems() {
        Assert.assertTrue(myEventsPage.containsEventWithTitle("Spring Fair"), "Event 'Spring Fair' should be present");
        myEventsPage.openEventCardByTitle("Spring Fair");

        SingleEventPage singleEventPage = new SingleEventPage(driver);
        Assert.assertTrue(singleEventPage.isPageOpened(), "Single Event page is not opened");
        singleEventPage.clickEditBudgetButton();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened");

        budgetingPage.removeBudgetItem(0);
        budgetingPage.clickSaveButton();
        Assert.assertEquals(budgetingPage.numOfAddedCategories(), 1, "Number of added categories should be 1");
    }

    @Test(priority = 4, dependsOnMethods = {"testAddBudgetingItem"})
    public void testBuyProduct() {
        driver.navigate().back();
        driver.navigate().back();

        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isPageOpened(), "Home page is not opened");

        Assert.assertTrue(homePage.containsCardWithTitle("Art Canvases"), "Home page should contain 'Art Canvases' card");
        homePage.openCardByTitle("Art Canvases");

        SolutionPage solutionPage = new SolutionPage(driver);
        Assert.assertTrue(solutionPage.isPageOpened(), "Solution page is not opened");

        solutionPage.buyProduct();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        budgetingPage.goToBudgetingExample();


        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened after buying product");
        Assert.assertEquals(budgetingPage.getLeftToSpendAmount(), "4730 €", "Left to spend amount should be 4730 € after buying product");
    }

    @Test(priority = 5, dependsOnMethods = {"testBuyProduct"})
    public void testLowerBudgetAfterBuyingProduct() {
        Assert.assertTrue(myEventsPage.containsEventWithTitle("Tech Meetup"), "Event 'Tech Meetup' should be present");
        myEventsPage.openEventCardByTitle("Tech Meetup");

        SingleEventPage singleEventPage = new SingleEventPage(driver);
        Assert.assertTrue(singleEventPage.isPageOpened(), "Single Event page is not opened");
        singleEventPage.clickEditBudgetButton();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened");

        budgetingPage.setBudget(0, 100);

        Assert.assertTrue(budgetingPage.validationErrorExists(0), "Validation error should exist after setting budget to 100 €");
    }

    @Test(priority = 5, dependsOnMethods = {"testBuyProduct"})
    public void testRaiseBudgetAfterBuyingProduct() {
        Assert.assertTrue(myEventsPage.containsEventWithTitle("Tech Meetup"), "Event 'Tech Meetup' should be present");
        myEventsPage.openEventCardByTitle("Tech Meetup");

        SingleEventPage singleEventPage = new SingleEventPage(driver);
        Assert.assertTrue(singleEventPage.isPageOpened(), "Single Event page is not opened");
        singleEventPage.clickEditBudgetButton();

        BudgetingPage budgetingPage = new BudgetingPage(driver);
        Assert.assertTrue(budgetingPage.isPageOpened(), "Budgeting page is not opened");

        budgetingPage.setBudget(0, 5270);

        Assert.assertFalse(budgetingPage.validationErrorExists(0), "Validation error should not exist after setting budget to 5000 €");
        Assert.assertEquals(budgetingPage.getLeftToSpendAmount(), "5000 €", "Left to spend amount should be 5000 € after raising budget");
    }

}
