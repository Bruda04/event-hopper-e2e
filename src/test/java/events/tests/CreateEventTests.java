package events.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import shared.TestBase;
import shared.pages.*;
import utils.Helper;

import static org.testng.Assert.assertTrue;

public class CreateEventTests extends TestBase {

    @BeforeMethod
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
        String eventTitle = "Selenium Conference";
        String eventDescription = "A fully automated test event";
        String city = "Belgrade";
        String address = "Bulevar kralja Aleksandra";
        String date = "11/15/2025";
        String maxParticipants = "100";
        String imagePath = "src/test/resources/event_cover.jpg";

        CreateEventPage page = new CreateEventPage(driver);
        assertTrue(page.isPageOpened(), "Create Event page failed.");

        page.fillEventInfo(
                eventTitle,
                eventDescription,
                city,
                address,
                date,
                maxParticipants,
                imagePath
        );

        page.clickNext();

        page.fillAgenda("Opening Keynote", "Intro to the event", "Main Hall", "8:15", "11:00");
        page.fillAgenda("Workshop", "Hands-on labs", "Lab Room", "11:15", "15:00");
        page.fillAgenda("Networking", "Networking with industry leaders", "Coworking Space", "17:00", "18:00");

        page.clickFinish();

        Helper.takeScreenshoot(driver, "full_event_with_agenda");

        MyEventsPage myEventsPage = new MyEventsPage(driver);
        assertTrue(myEventsPage.isPageOpened(), "My Events page did not open.");
        assertTrue(myEventsPage.containsEventWithTitle(eventTitle), "Created event not found on My Events page.");
        //myEventsPage.openEventCardByTitle(eventTitle);

        Helper.takeScreenshoot(driver, "my events page");
    }

    @Test
    public void testMissingRequiredFieldsShowsErrors() {
        navigateToCreateEvent();
        CreateEventPage page = new CreateEventPage(driver);
        assertTrue(page.isPageOpened(), "Create Event page failed.");

        page.clickNext(); // Try skipping form without inputs

        Helper.takeScreenshoot(driver, "missing_fields_error");
        Assert.assertTrue(page.allErrorMessagesDisplayed(), "Error messages for required fields are not displayed as expected.");
    }


    @Test
    public void testOverlappingAgendaFails() {
        CreateEventPage page = new CreateEventPage(driver);

        page.fillEventInfo("Overlap Test", "Agenda check", "Novi Sad", "Main Street", "08/20/2025", "50", "src/test/resources/profile_picture.jpg");
        page.clickNext();

        page.fillAgenda("Talk 1", "Session 1", "Room A", "10:00", "11:00");
        page.fillAgenda("Talk 2", "Session 2", "Room B", "10:30", "11:30"); // Overlaps

        Helper.takeScreenshoot(driver, "overlap_agenda_test");
        Assert.assertTrue(page.isOverlappingTimeErrorDisplayed(), "Overlapping time error not displayed as expected.");
    }
}
