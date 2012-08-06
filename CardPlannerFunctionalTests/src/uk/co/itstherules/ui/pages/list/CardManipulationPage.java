package uk.co.itstherules.ui.pages.list;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import uk.co.itstherules.ui.functions.BrowserWait;
import uk.co.itstherules.ui.functions.Constants;
import uk.co.itstherules.ui.pages.Editable;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.Showable;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;
import uk.co.itstherules.ui.pages.change.ChangeTypePage;
import uk.co.itstherules.ui.pages.show.ShowCardPage;

public abstract class CardManipulationPage<T> extends Page<T> implements Editable<ChangeCardPage>, Showable<ShowCardPage> {

	protected final String appRoot;

	public CardManipulationPage(String appRoot, WebDriver driver) {
		super(driver, appRoot);
		this.appRoot = appRoot;
		PageFactory.initElements(driver, this);
    }
	
	public CardManipulationPage<?> showTools(int index) {
		WebElement toolsButton = driver.findElements(By.name("toolsButton")).get(index);
		toolsButton.click();
		return this;
	}

	@Override
	public ChangeCardPage selectEditCard(int index) {
		clickCard("editToolButton", index);
		BrowserWait.forElement(driver, By.name("title"), 6000);
		return new ChangeCardPage(appRoot, driver, ChangeTypePage.Edit);
	}

	public ShowCardPage selectShowCard(int index) {
		clickCard("showToolButton", index);
		return new ShowCardPage(appRoot, driver);
    }
	
	private void clickCard(String name, int index) {
		WebElement toolButton = driver.findElements(By.name(name)).get(index);
		toolButton.click();
		BrowserWait.forFrame(driver, Constants.LIGHTWINDOW_IFRAME, 5000);
	}
	
	private CardManipulationPage<?> selectConfirmBasedButton(String buttonName, int index) {
		WebElement button = driver.findElements(By.name(buttonName)).get(index);
		confirmAs(true);
		button.click();
		return this;
    }

	public CardManipulationPage<?> selectArchiveCard(int index) {
		return selectConfirmBasedButton("archiveToolButton", index);
    }

	public CardManipulationPage<?> selectDeleteCard(int index) {
		return selectConfirmBasedButton("deleteToolButton", index);
    }
}
