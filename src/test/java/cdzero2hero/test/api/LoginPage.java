package cdzero2hero.test.api;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

public class LoginPage {
    private final HtmlPage htmlPage;

    public LoginPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public boolean isLoaded() {
        return "login".equals(htmlPage.getBody().getId());
    }

    public FriendsPage login(String user) throws IOException {
        HtmlTextInput userInput = htmlPage.getElementByName("user");
        userInput.setValueAttribute(user);

        HtmlSubmitInput startButton = htmlPage.getElementByName("start");
        HtmlPage newPage = startButton.click();
        return new FriendsPage(newPage);
    }
}
