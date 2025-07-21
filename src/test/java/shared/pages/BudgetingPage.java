package shared.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.How.XPATH;

public class BudgetingPage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h3")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'save-button')]")
    private WebElement saveButton;

    public BudgetingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickSaveButton() {
        saveButton.click();
    }

    public void goToBudgetingExample() {
        driver.get("http://localhost:4200/3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a/budgeting");
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }
}
