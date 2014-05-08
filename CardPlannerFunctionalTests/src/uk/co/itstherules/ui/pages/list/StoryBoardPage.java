package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import uk.co.itstherules.ui.TestConstants;
import uk.co.itstherules.ui.functions.Wait;

import java.util.List;

public class StoryBoardPage extends CardManipulationPage<StoryBoardPage> {

    public StoryBoardPage(String appRoot, WebDriver driver) {
        super(appRoot, driver);
    }

    public StoryBoardPage dragOnto(WebElement toDrag, WebElement toDragOnto) {
        Actions builder = new Actions(driver);
        Action dragAndDrop = builder.clickAndHold(toDrag).moveToElement(toDragOnto).release(toDragOnto).build();
        dragAndDrop.perform();
        try {
            //TODO: a less horrible way to handle AJAX events
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public StoryBoardPage dragOnto(String toDragId, String toDragOntoId) {
        WebElement toDrag = driver.findElement(By.id(toDragId));
        WebElement toDragOnto = driver.findElement(By.id(toDragOntoId));
        return dragOnto(toDrag, toDragOnto);
    }

    public StoryBoardPage selectSwimLaneAmount(int swimLaneAmount) {
        String stringLaneAmount = String.valueOf(swimLaneAmount);
        WebElement swimLaneAmountSelect = driver.findElement(By.id("selectSwimLaneAmount"));
        List<WebElement> options = swimLaneAmountSelect.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if (stringLaneAmount.equals(option.getText())) {
                option.click();
                break;
            }
        }
        Wait.forText(driver, "<!--SwimLaneAmount=" + swimLaneAmount + "-->", 5000);
        return this;
    }

    @Override
    public String getAction() {
        return "Show";
    }

    @Override
    public String getController() {
        return "StoryBoard";
    }

    public StoryBoardPage toggleBacklog() {
        driver.findElement(By.id("backlog_tab")).click();
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public void deleteCard(String cardId) {
        toggleBacklog();
        expandCard(cardId);
        confirmAs(true);
        By delete = By.id("delete_card_button" + cardId);
        Wait.untilVisible(driver, delete, 5000);
        driver.findElement(delete).click();
    }

    public StoryBoardPage collapseCard(String cardId) {
        WebElement expandElement = driver.findElement(By.id("_collapse"+cardId));
        expandElement.click();
        return this;
    }

    public StoryBoardPage expandCard(String cardId) {
        WebElement expandElement = driver.findElement(By.id("_expand"+cardId));
        expandElement.click();
        return this;
    }

    public StoryBoardPage clickCardShow(String cardId) {
        final By show = By.id("image_show_card_button" + cardId);
        Wait.untilVisible(driver, show, 5000);
        WebElement editCard = driver.findElement(show);
        editCard.click();
        return this;
    }

    public StoryBoardPage clickCardEdit(String cardId) {
        expandCard(cardId);
        final By edit = By.id("image_edit_card_button" + cardId);
        Wait.untilVisible(driver, edit, 5000);
        WebElement editCard = driver.findElement(edit);
        editCard.click();
        return this;
    }

    public StoryBoardPage dragCardFromBacklogToInProgress(String cardId) {
        Wait.forElement(driver, By.id(cardId), 1000);
        String targetPanelId = TestConstants.IN_PROGRESS_HOTSPOT_ID;
        dragOnto(cardId, targetPanelId);
        return this;
    }

    public void closePopOver() {
        driver.findElement(By.id("lightwindow_title_bar_close_link")).click();
    }

    public StoryBoardPage addTag(String tagString) {
        WebElement tag = driver.findElement(By.id("tag"));
        tag.clear();
        tag.sendKeys(tagString);
        driver.findElement(By.id("addTagButton")).click();
        return this;
    }

    public void clickCardAdd() {
        driver.findElement(By.id("add_card_button")).click();
    }

    public StoryBoardPage setTitle(String title) {
        fillField("title", title);
        return this;
    }

    public StoryBoardPage setBody(String body) {
        fillField("body", body);
        return this;
    }

    public StoryBoardPage setEffort(String type, double amount) {
        selectByValue("effort.type.identity", type);
        fillField("effort.amount", String.valueOf(amount));
        return this;
    }

    public StoryBoardPage setValue(String type, double amount) {
        selectByValue("value.type.identity", type);
        fillField("value.amount", String.valueOf(amount));
        return this;
    }

    public StoryBoardPage setCardType(String value) {
        selectByValue("type.identity", value);
        return this;
    }

    public StoryBoardPage setStatus(String value) {
        selectByValue("status.identity", value);
        return this;
    }


    public StoryBoardPage completeAction() {
        driver.findElement(By.id("completeAction")).click();
        return this;
    }


    private void selectByValue(String selectId, String value) {
        final WebElement select = driver.findElement(By.name(selectId));
        List<WebElement> options = select.findElements(By.tagName("option"));
        for (WebElement option : options) {
            if(value.equals(option.getText())){
                option.click();
                return;
            }
        }
        throw new IllegalArgumentException("<select id=\""+selectId+"\"> does not contain "+value);
    }

    private void fillField(String id, String title) {clearAndPopulate(driver.findElement(By.name(id)), title);}

}