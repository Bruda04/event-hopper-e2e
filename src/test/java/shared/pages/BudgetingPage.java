package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.How.CSS;
import static org.openqa.selenium.support.How.XPATH;

public class BudgetingPage {
    private WebDriver driver;

    @FindBy(how = How.XPATH, using = "//h3")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'save-button')]")
    private WebElement saveButton;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'add-button')]")
    private WebElement addCategoryButton;

    @FindBy(how = CSS, using = "mat-select[formcontrolname='category']")
    private WebElement categorySelect;

    @FindBy(how = CSS, using = "mat-option")
    private List<WebElement> categoryOptions;

    @FindBy(how = CSS, using = "input[formcontrolname='amount']")
    private List<WebElement> amountInputs;

    @FindBy(how = XPATH, using = "//button[contains(@class, 'delete-button')]")
    private List<WebElement> removeButtons;

    public BudgetingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickSaveButton() {
        saveButton.click();
    }

    public void goToBudgetingExample() {
        driver.get("http://localhost:4200/events/3f7b2c9e-4a6f-4d5b-b8c1-7a2f9e3b6d4a/budgeting");
        PageFactory.initElements(driver, this);
    }

    public void goToBudgetingExample2() {
        driver.get("http://localhost:4200/events/4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b/budgeting");
        PageFactory.initElements(driver, this);
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }


    public void addCategory(int categoryIdx, int amount) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(categorySelect)).click();
        wait.until(ExpectedConditions.visibilityOfAllElements(categoryOptions)).get(categoryIdx).click();

        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);

        addCategoryButton.click();

        wait.until(ExpectedConditions.visibilityOfAllElements(amountInputs)).get(categoryIdx).clear();
        amountInputs.get(categoryIdx).sendKeys(String.valueOf(amount));
    }

    public void addMultipleCategories(int[] categoryIndices, int[] amounts) {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        wait.until(ExpectedConditions.elementToBeClickable(categorySelect)).click();
        for (int categoryIdx : categoryIndices) {
            wait.until(ExpectedConditions.visibilityOfAllElements(categoryOptions)).get(categoryIdx).click();
        }

        driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);

        addCategoryButton.click();

        for (int i = 0; i < categoryIndices.length; i++) {
            int categoryIdx = categoryIndices[i];
            int amount = amounts[i];
            wait.until(ExpectedConditions.visibilityOfAllElements(amountInputs)).get(categoryIdx).clear();
            amountInputs.get(categoryIdx).sendKeys(String.valueOf(amount));
        }

    }

    public void removeBudgetItem(int idx) {
        if (!removeButtons.isEmpty()) {
            removeButtons.get(idx).click();
        } else {
            throw new RuntimeException("No budget items to remove");
        }
    }

    public int numOfAddedCategories() {
        List<WebElement> freshLabels = driver.findElements(By.cssSelector("span.category-label"));
        return freshLabels.size();
    }

    public String getLeftToSpendAmount() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement amountElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".left-label b"))
        );
        return amountElement.getText();
    }

    public void setBudget(int budgetItemIdx, int amount) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfAllElements(amountInputs)).get(budgetItemIdx).clear();
        amountInputs.get(budgetItemIdx).sendKeys(String.valueOf(amount));
    }

    public boolean validationErrorExists(int idx) {
        WebElement input = driver.findElement(By.cssSelector("input[formcontrolname='amount']"));
        return "true".equals(amountInputs.get(idx).getAttribute("aria-invalid"));
    }
}
