package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.How.*;

public class HomePage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//p[text()='All events']")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'profile-button')]")
    private WebElement profileButton;

    @FindBy(css = ".all-events .search-bar .search")
    private WebElement searchInput;

    @FindBy(css = ".all-events .search-bar .search-button")
    private WebElement searchButton;

    @FindBy(css = ".all-events .filter-button")
    private WebElement filterButton;

    @FindBy(css = ".filter-panel")
    private WebElement filterPanel;

    @FindBy(css = ".all-events .filter-actions button mat-icon")
    private WebElement applyFilterButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickOnProfileButton() {
        profileButton.click();
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }

    public boolean containsCardWithTitle(String title) {
        try {
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".single-card-item"))
            );

            return driver.findElements(By.cssSelector("mat-card-title"))
                    .stream()
                    .anyMatch(el -> el.getText().trim().equals(title));
        } catch (Exception e) {
            return false;
        }
    }


    public void openCardByTitle(String title) {
        By cardLocator = By.xpath("//mat-card[.//mat-card-title[normalize-space(text())='" + title + "']]");

        WebElement cardElement = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.presenceOfElementLocated(cardLocator));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cardElement);

        WebElement viewMoreButton = cardElement.findElement(By.xpath(".//button[.//span[normalize-space(text())='View More']]"));

        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(viewMoreButton)).click();
    }

    public void searchByTitle(String title) {
        searchInput.clear();
        searchInput.sendKeys(title);
        searchButton.click();
    }

    public void searchByDescription(String description) {
        searchInput.clear();
        searchInput.sendKeys(description);
        searchButton.click();
    }

    public List<WebElement> getAllEventCards() {
        return driver.findElements(By.cssSelector(".all-events .single-card-item"));
    }


    public void openFilterPanel() {
        filterButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
                .until(ExpectedConditions.visibilityOf(filterPanel));
    }


    public void selectCity(String cityName) {
        WebElement locationSelect = filterPanel.findElement(By.cssSelector("mat-select[formControlName='city']"));
        locationSelect.click();

        List<WebElement> cityOptions = new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("div.cdk-overlay-container mat-option span")
                ));

        for (WebElement option : cityOptions) {
            if (option.getText().trim().equals(cityName)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("City option '" + cityName + "' not found!");
    }

    public void applyFilter() {
        applyFilterButton.click();
        new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
                .until(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector(".all-events .single-card-item"), 0));
    }


    public void sortByTitle() {
        WebElement sortByNameRadio = filterPanel.findElement(By.cssSelector("mat-radio-button[value='name']"));
        new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
                .until(ExpectedConditions.elementToBeClickable(sortByNameRadio));
        sortByNameRadio.click();
        applyFilter();
    }

}
