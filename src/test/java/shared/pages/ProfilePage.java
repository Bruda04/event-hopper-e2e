package shared.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.How.*;

public class ProfilePage {
    private WebDriver driver;

    @FindBy(how = TAG_NAME, using = "h3")
    private WebElement heading;

    @FindBy(how = XPATH, using = "//button[.//span[contains(text(), 'VIEW MY EVENTS')]]")
    private WebElement myEventsButton;

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnMyEventsButton() {
        myEventsButton.click();
    }

    public boolean isPageOpened(){
        return (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(heading)).isDisplayed();
    }
}
