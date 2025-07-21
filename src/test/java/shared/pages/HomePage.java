package shared.pages;

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
}
