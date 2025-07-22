package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.How.*;

public class MyEventsPage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h2")
    private WebElement heading;

    @FindBy(how = CLASS_NAME, using = "create-button")
    private WebElement createButton;


    public MyEventsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }

    public boolean hasAtLeastOneEvent() {
        try {
            WebElement hasNoEventsError = driver.findElement(By.xpath("//p[text()='You have not created any events yet.']"));
            return !hasNoEventsError.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }




    public void clickCreateButton() {
        new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(createButton)).click();
    }

    public boolean containsEventWithTitle(String eventTitle) {
        try {
            // Wait for at least one card to appear
            new WebDriverWait(driver, 10).until(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".single-card-item"))
            );

            // Try to find the mat-card-title with the expected text
            return driver.findElements(By.cssSelector("mat-card mat-card-title"))
                    .stream()
                    .anyMatch(el -> el.getText().equals(eventTitle));
        } catch (Exception e) {
            return false;
        }
    }

    public void openEventCardByTitle(String eventTitle) {
        // Locate the mat-card-title by visible text
        By cardTitleLocator = By.xpath("//mat-card//mat-card-title[normalize-space(text())='" + eventTitle + "']");
        WebElement titleElement = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(cardTitleLocator));

        // Go up to mat-card
        WebElement cardElement = titleElement.findElement(By.xpath("./ancestor::mat-card"));

        // Now find the button within that mat-card
        WebElement viewMoreButton = cardElement.findElement(By.xpath(".//button[.//text()[normalize-space()='View More']]"));

        // Click the button
        viewMoreButton.click();
    }



}
