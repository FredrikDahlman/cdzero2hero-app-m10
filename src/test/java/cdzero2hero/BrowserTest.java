package cdzero2hero;

import cdzero2hero.test.api.FriendsPage;
import cdzero2hero.test.api.LoginPage;
import cdzero2hero.test.categories.LargeTest;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Category(LargeTest.class)
public class BrowserTest {
    @BeforeClass
    public static void setUpClass() {
        Main.start();
    }

    @AfterClass
    public static void tearDownClass() {
        Main.stop();
    }

    private WebClient webClient;

    @Before
    public void setUp() {
        webClient = new WebClient();
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
    }

    @After
    public void tearDown() {
        webClient.closeAllWindows();
    }

    @Test
    public void bartsFriends() throws Exception {
        LoginPage loginPage = new LoginPage((HtmlPage) webClient.getPage("http://localhost:" + Main.HTTP_SERVER_PORT));
        assertTrue(loginPage.isLoaded());

        FriendsPage friendsPage = loginPage.login("Bart");
        assertTrue(friendsPage.isLoaded());
        assertEquals("Bart", friendsPage.getName());
        assertEquals(3, friendsPage.getFriendsCount());

        loginPage = friendsPage.logout();
        assertTrue(loginPage.isLoaded());
    }

    @Test
    public void lisasFriend() throws Exception {
        LoginPage loginPage = new LoginPage((HtmlPage) webClient.getPage("http://localhost:" + Main.HTTP_SERVER_PORT));
        assertTrue(loginPage.isLoaded());

        FriendsPage friendsPage = loginPage.login("Lisa");
        assertTrue(friendsPage.isLoaded());
        assertEquals("Lisa", friendsPage.getName());
        assertEquals(0, friendsPage.getFriendsCount());

        friendsPage = friendsPage.addFriend("Jimbo", "Jones");
        assertEquals(1, friendsPage.getFriendsCount());

        loginPage = friendsPage.logout();
        assertTrue(loginPage.isLoaded());

        friendsPage = loginPage.login("Lisa");
        assertTrue(friendsPage.isLoaded());
        assertEquals("Lisa", friendsPage.getName());
        assertEquals(1, friendsPage.getFriendsCount());
    }
}
