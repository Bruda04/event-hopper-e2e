package shared.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.openqa.selenium.support.How.XPATH;

public class BookingDialogPage {
    private WebDriver driver;

    @FindBy(how = XPATH, using = "//h2[contains(text(),'Book a service')]")
    private WebElement dialogTitle;

    @FindBy(how = XPATH, using = "//mat-datepicker-toggle/button")
    private WebElement datepickerToggle;

    public BookingDialogPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isDialogOpened() {
        return new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds())
                .until(ExpectedConditions.visibilityOf(dialogTitle))
                .isDisplayed();
    }

    public void openDatepicker() {
        datepickerToggle.click();
        new WebDriverWait(driver, Duration.ofSeconds(5).getSeconds())
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mat-datepicker-content")));
    }

    public boolean arePastDatesDisabled() {
        // Angular material koristi klase .mat-calendar-body-disabled za disabled datume
        List<WebElement> disabledDates = driver.findElements(By.cssSelector(".mat-calendar-body-disabled"));
        return !disabledDates.isEmpty();  // ako ih ima — filter radi
    }


}
