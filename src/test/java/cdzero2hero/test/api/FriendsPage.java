package cdzero2hero.test.api;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

public class FriendsPage {
    private final HtmlPage htmlPage;

    public FriendsPage(HtmlPage htmlPage) {
        this.htmlPage = htmlPage;
    }

    public boolean isLoaded() {
        return "friends".equals(htmlPage.getBody().getId());
    }

    public String getName() {
        return htmlPage.getElementById("name").asText();
    }

    public LoginPage logout() throws IOException {
        HtmlAnchor logout = htmlPage.getAnchorByHref("/logout");
        HtmlPage newPage = logout.click();
        return new LoginPage(newPage);
    }

    public int getFriendsCount() {
        return htmlPage.getByXPath("//tr[@class='friend']").size();
    }

    public FriendsPage addFriend(String firstName, String surname) throws IOException {
        HtmlTextInput firstNameInput = htmlPage.getElementByName("firstName");
        firstNameInput.setValueAttribute(firstName);

        HtmlTextInput surnameInput = htmlPage.getElementByName("surname");
        surnameInput.setValueAttribute(surname);

        HtmlSubmitInput submitButton = htmlPage.getElementByName("submit");
        HtmlPage newPage = submitButton.click();
        return new FriendsPage(newPage);
    }
}
