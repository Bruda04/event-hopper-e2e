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

import static org.openqa.selenium.support.How.*;

public class HomePage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//p[text()='All events']")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'profile-button')]")
    private WebElement profileButton;

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



}
