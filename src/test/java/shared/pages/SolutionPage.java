package shared.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Helper;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.How.XPATH;

public class SolutionPage {
    private WebDriver driver;

    @FindBy(how = XPATH, using = "//h1")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'buy-btn')]")
    private WebElement buyButton;

    @FindBy(how = XPATH, using = "//button[.//span[text()='BUY']]")
    private WebElement confirmationBuyButton;

    public SolutionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }

    public void goToSolution(String solutionId) {
        driver.get("http://localhost:4200/solutions/" + solutionId);
        PageFactory.initElements(driver, this);
    }

    public boolean isBuyButtonPresent(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOf(buyButton));
            return buyButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void buyProduct() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", buyButton);

        // Wait for visibility + clickability
        wait.until(ExpectedConditions.visibilityOf(buyButton));
        wait.until(ExpectedConditions.elementToBeClickable(buyButton)).click();

        Helper.takeScreenshoot(driver, "solution_page_buy_button_clicked");

        // Wait for dialog to appear
        wait.until(ExpectedConditions.visibilityOf(confirmationBuyButton));
        wait.until(ExpectedConditions.elementToBeClickable(confirmationBuyButton)).click();

        Helper.takeScreenshoot(driver, "solution_page_confirmation_buy_button_clicked");
    }

    public void clickBuyButton(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        wait.until(ExpectedConditions.elementToBeClickable(buyButton)).click();
    }

    public void bookService(){

    }
}
