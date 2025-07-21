package events.tests;

import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.*;
import utils.Helper;

import static org.testng.Assert.assertTrue;

public class CreateEventTests extends TestBase {

    private void navigateToCreateEvent() {
        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isPageOpened(), "Login page failed.");
        loginPage.login(LoginPage.organizer3Username, LoginPage.organizer3Password);

        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isPageOpened(), "Home page failed.");
        homePage.clickOnProfileButton();

        ProfilePage profilePage = new ProfilePage(driver);
        assertTrue(profilePage.isPageOpened(), "Profile page failed.");
        profilePage.clickOnMyEventsButton();

        MyEventsPage myEventsPage = new MyEventsPage(driver);
        assertTrue(myEventsPage.isPageOpened(), "My Events page failed.");
        myEventsPage.clickCreateButton();
    }

    @Test
    public void testCreateFullEventWithAgenda() {
        navigateToCreateEvent();
        CreateEventPage page = new CreateEventPage(driver);
        assertTrue(page.isPageOpened(), "Create Event page failed.");

        page.fillBasicInfo(
                "Selenium Conference",
                "A fully automated test event",
                "Belgrade",
                "Bulevar kralja Aleksandra",
                "08/15/2025", // format depends on locale
                "100",
                "src/test/resources/event_cover.jpg"
        );

        page.clickNext();

        page.fillAgenda("Opening Keynote", "Intro to the event", "Main Hall", "10:00", "11:00");
        page.fillAgenda("Workshop", "Hands-on labs", "Lab Room", "11:15", "13:00");

        page.clickFinish();

        Helper.takeScreenshoot(driver, "full_event_with_agenda");
    }

    @Test
    public void testMissingRequiredFieldsShowsErrors() {
        navigateToCreateEvent();
        CreateEventPage page = new CreateEventPage(driver);
        assertTrue(page.isPageOpened(), "Create Event page failed.");

        page.clickNext(); // Try skipping form without inputs

        Helper.takeScreenshoot(driver, "missing_fields_error");
        // Ideally assert error messages shown
    }

    @Test
    public void testImageUploadOnly() {
        navigateToCreateEvent();
        CreateEventPage page = new CreateEventPage(driver);
        page.uploadImage("src/test/resources/event_cover.jpg");

        Helper.takeScreenshoot(driver, "image_upload_test");
    }

    @Test
    public void testOverlappingAgendaFails() {
        navigateToCreateEvent();
        CreateEventPage page = new CreateEventPage(driver);

        page.fillBasicInfo("Overlap Test", "Agenda check", "Novi Sad", "Main Street", "08/20/2025", "50", "src/test/resources/profile_picture.jpg");
        page.clickNext();

        page.fillAgenda("Talk 1", "Session 1", "Room A", "10:00", "11:00");
        page.fillAgenda("Talk 2", "Session 2", "Room B", "10:30", "11:30"); // Overlaps

        Helper.takeScreenshoot(driver, "overlap_agenda_test");
        // You could add: assert error message element is displayed
    }
}
