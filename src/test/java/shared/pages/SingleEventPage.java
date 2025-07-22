package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SingleEventPage {

    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//div[@class='header']/h1")
    private WebElement eventTitle;

    @FindBy(how = How.XPATH, using = "//div[@class='event-details']/p")
    private WebElement eventDescription;

    @FindBy(how = How.XPATH, using = "//div[@class='info-banner']//h3[preceding-sibling::mat-icon[text()='place']]")
    private WebElement eventAddress;

    public SingleEventPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened() {
        try {
            return new WebDriverWait(driver, 10)
                    .until(ExpectedConditions.visibilityOf(eventTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getEventTitle() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(eventTitle))
                .getText().trim();
    }

    public String getEventDescription() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(eventDescription))
                .getText().trim();
    }

    public String getEventAddress() {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOf(eventAddress))
                .getText().trim();
    }
}
