package filter.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.HomePage;
import shared.pages.LoginPage;

import org.testng.Assert;
import utils.Helper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class FilterSearchTests extends TestBase {

    @BeforeMethod
    private void setup(){
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened(), "Login page failed.");
        loginPage.login(LoginPage.organizer3Username, LoginPage.organizer3Password);

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened(), "Home page failed.");

    }

    @Test(priority = 1)
    public  void testEventsAreDisplayedInitially(){
        List<WebElement> events = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        Assert.assertTrue(!events.isEmpty(), "At least one event should be displayed.");
        Assert.assertTrue(events.size() <= 10, "There should be at most 10 events per page.");
    }

    @Test(priority = 2, dependsOnMethods = {"testEventsAreDisplayedInitially"})
    public  void testSearchByTitle(){
        WebElement searchInput = driver.findElement(By.cssSelector(".all-events .search-bar .search"));
        searchInput.clear();
        searchInput.sendKeys("Winter Fest");
        WebElement searchButton = driver.findElement(By.cssSelector(".all-events .search-bar .search-button"));
        searchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(2).getSeconds())
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".all-events .single-card-item"), 0));

        List<WebElement> events = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        Helper.takeScreenshoot(driver,"Search by name");
        Assert.assertTrue(!events.isEmpty(), "At least one event should match the search query.");


        boolean found = events.stream().anyMatch(e -> {
            WebElement title = e.findElement(By.cssSelector("mat-card-title"));
            return title.getText().equals("Winter Fest");
        });

        Assert.assertTrue(found, "Event with name 'Winter Fest' should be present after search");

    }

    @Test(priority = 3)
    public void testSearchByDescription(){
        WebElement searchInput = driver.findElement(By.cssSelector(".all-events .search-bar .search"));
        searchInput.clear();
        searchInput.sendKeys("join");
        WebElement searchButton = driver.findElement(By.cssSelector(".all-events .search-bar .search-button"));
        searchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(2).getSeconds())
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".all-events .single-card-item"), 0));


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        List<WebElement> events = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.cssSelector(".all-events .single-card-item")));

        Helper.takeScreenshoot(driver,"Search by description");
        Assert.assertTrue(!events.isEmpty(), "At least one event should match the search query.");


        boolean found = events.stream().anyMatch(e -> {
            WebElement description = e.findElement(By.cssSelector("mat-card-content p:nth-of-type(2)"));
            return description.getText().toUpperCase().contains("join".toUpperCase());
        });

        Assert.assertTrue(found, "Event with description containing 'join' should be present after search");
    }

    @Test(priority = 4)
    public void testSearchNoResults(){
        WebElement searchInput = driver.findElement(By.cssSelector(".all-events .search-bar .search"));
        searchInput.clear();
        searchInput.sendKeys("aaaaaaaaa");
        WebElement searchButton = driver.findElement(By.cssSelector(".all-events .search-bar .search-button"));
        searchButton.click();

        new WebDriverWait(driver, Duration.ofSeconds(2).getSeconds())
                .until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(".all-events .single-card-item"), 0));

        List<WebElement> events = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        Helper.takeScreenshoot(driver,"Search no results");
        Assert.assertTrue(events.isEmpty(), "List should be empty.");

    }

    @Test(priority = 5)
    public void testFilterByLocation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        WebElement searchInput = driver.findElement(By.cssSelector(".all-events .search-bar .search"));
        searchInput.clear();

        WebElement filterButton = driver.findElement(By.cssSelector(".all-events .filter-button"));
        filterButton.click();

        WebElement filterPanel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filter-panel"))
        );

        WebElement locationSelect = driver.findElement(By.cssSelector("mat-select[formControlName='city']"));
        locationSelect.click();

        List<WebElement> cityOptions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("div.cdk-overlay-container mat-option span"))
        );

        boolean clicked = false;
        for (WebElement option : cityOptions) {
            if (option.getText().trim().equals("New York")) {
                option.click();
                clicked = true;
                break;
            }
        }
        Assert.assertTrue(clicked, "City option 'New York' should be present and clicked");


        WebElement applyFilter = driver.findElement(By.cssSelector(".all-events .filter-actions button mat-icon"));
        applyFilter.click();

        Helper.takeScreenshoot(driver,"Filter by location");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".all-events .single-card-item"), 0));

        List<WebElement> events = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        boolean allMatch = events.stream().allMatch(e -> {
            WebElement location = e.findElement(By.cssSelector("mat-card-content p:first-of-type"));
            return location.getText().contains("New York");
        });

        Assert.assertTrue(allMatch, "All events should be in New York");
    }

    @Test(priority = 6, dependsOnMethods = {"testSearchByTitle", "testFilterByLocation"})
    public void testFilterByLocationAndTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());
        WebElement searchInput = driver.findElement(By.cssSelector(".all-events .search-bar .search"));
        searchInput.clear();
        searchInput.sendKeys("Halloween Party");

        WebElement filterButton = driver.findElement(By.cssSelector(".all-events .filter-button"));
        filterButton.click();

        WebElement filterPanel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filter-panel"))
        );

        WebElement locationSelect = driver.findElement(By.cssSelector("mat-select[formControlName='city']"));
        locationSelect.click();

        List<WebElement> cityOptions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("div.cdk-overlay-container mat-option span"))
        );

        boolean clicked = false;
        for (WebElement option : cityOptions) {
            if (option.getText().trim().equals("New York")) {
                option.click();
                clicked = true;
                break;
            }
        }
        Assert.assertTrue(clicked, "City option 'New York' should be present and clicked");

        WebElement applyFilter = driver.findElement(By.cssSelector(".all-events .filter-actions button mat-icon"));
        applyFilter.click();

        Helper.takeScreenshoot(driver,"Filter by location and title");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".all-events .single-card-item"), 0));

        List<WebElement> events = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        boolean allMatch = events.stream().allMatch(e -> {
            WebElement location = e.findElement(By.cssSelector("mat-card-content p:first-of-type"));
            WebElement title = e.findElement(By.cssSelector("mat-card-title"));
            return location.getText().contains("New York") &&
                    title.getText().equals("Halloween Party");
        });

        Assert.assertTrue(allMatch, "All events should be in New York and have title 'Halloween Party'");
    }

    @Test(priority = 7)
    public void testSortByTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds());

        WebElement filterButton = driver.findElement(By.cssSelector(".all-events .filter-button"));
        filterButton.click();

        WebElement filterPanel = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".filter-panel"))
        );

        WebElement sortByNameRadio = filterPanel.findElement(
                By.cssSelector("mat-radio-button[value='name']")
        );
        wait.until(ExpectedConditions.elementToBeClickable(sortByNameRadio));
        sortByNameRadio.click();

        WebElement applyFilterButton = filterPanel.findElement(
                By.cssSelector(".filter-actions button mat-icon")
        );
        applyFilterButton.click();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.cssSelector(".all-events .single-card-item"), 0));

        List<WebElement> eventCards = driver.findElements(By.cssSelector(".all-events .single-card-item"));
        List<String> titles = new ArrayList<>();

        for (WebElement card : eventCards) {
            WebElement titleElement = card.findElement(By.cssSelector("mat-card-title"));
            titles.add(titleElement.getText().trim());
        }

        List<String> sortedTitles = new ArrayList<>(titles);
        Collections.sort(sortedTitles, String.CASE_INSENSITIVE_ORDER);

        System.out.println("Displayed titles: " + titles);
        System.out.println("Sorted titles: " + sortedTitles);

        Assert.assertEquals(titles, sortedTitles, "Events should be sorted alphabetically by title (A–Z)");

        Helper.takeScreenshoot(driver, "Sort by Title");
    }


}
