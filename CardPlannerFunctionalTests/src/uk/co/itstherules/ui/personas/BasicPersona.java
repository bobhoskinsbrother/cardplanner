package uk.co.itstherules.ui.personas;

import org.openqa.selenium.WebElement;
import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.TemplatePage;
import uk.co.itstherules.ui.pages.add.AddSignUpPage;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;
import uk.co.itstherules.ui.pages.change.ChangePersonPage;
import uk.co.itstherules.ui.pages.change.ChangePostItPage;
import uk.co.itstherules.ui.pages.change.ChangeStatusPage;
import uk.co.itstherules.ui.pages.list.*;
import uk.co.itstherules.ui.pages.show.LoginPage;
import uk.co.itstherules.ui.pages.show.ShowCardPage;
import uk.co.itstherules.ui.pages.show.ShowPostItPage;

public class BasicPersona extends BaseBasicPersona<Page<?>> implements Editor, Viewer, Adder, Mover {

	public BasicPersona(String name) {
		super(name);
	}
	
	public Page<?> add(ChangeCardPage changeable) {
		return change(changeable);
	}
	
    public StatusesPage add(StatusesPage statuses) {
		ChangeStatusPage toAdd = statuses.selectAdd();
		return change(toAdd);
	}

    public Page<?> add(ChangePersonPage changeable) {
		return edit(changeable);
	}

	private Page<?> change(ChangeCardPage changeCardPage) {
		String title = personaData.getCardTitle();
		String body = personaData.getCardBody();
		String effortAmount = personaData.getEffortAmount();
		String valueAmount = personaData.getValueAmount();
		String[] tags = personaData.getTags();
		if (!"".equals(title)) {
			changeCardPage = changeCardPage.setTitle(title);
		}
		if (!"".equals(body)) {
			changeCardPage = changeCardPage.setBody(body);
		}
		if (!"".equals(effortAmount)) {
			changeCardPage = changeCardPage.setEffortAmount(effortAmount);
		}
		if (!"".equals(valueAmount)) {
			changeCardPage = changeCardPage.setValueAmount(valueAmount);
		}
		if (tags.length>0) {
			for (String tag : tags) {
				changeCardPage = changeCardPage.addTag(tag);
			}
		}
		return changeCardPage.clickComplete();
	}

	private StatusesPage change(ChangeStatusPage addStatusPage) {
		String title = personaData.getStatusTitle();
		if (!"".equals(title)) {
			addStatusPage = addStatusPage.setTitle(title);
		}
		return addStatusPage.complete();
	}
	
    public StatusesPage change(StatusesPage listStatusPage) {
		listStatusPage.dragStatusRight(personaData.getStatusToDrag());
		try {
	        Thread.sleep(650);
        } catch (InterruptedException e) {
	        throw new RuntimeException(e);
        }
		return listStatusPage;
    }

	public CardsPage dragCardIntoCard(CardsPage cards, int toDragIndex, int toBeDraggedOntoIndex) {
		return cards.dragCardOntoCard(toDragIndex, toBeDraggedOntoIndex);
    }
	
    public Page<?> edit(ChangeCardPage changeable) {
		return change(changeable);
	}

	public StatusesPage edit(ChangeStatusPage selected) {
		return change(selected);
    }
	
	@SuppressWarnings("unchecked")
    public <T extends Page<?>> T signUp(AddSignUpPage signUpPage) {
		String firstName = personaData.getPersonFirstName();
		String lastName = personaData.getPersonLastName();
		String initials = personaData.getPersonInitials();
		String email = personaData.getPersonEmail();
		String password = personaData.getPersonPassword();
		
		
		if (!"".equals(firstName)) {
			signUpPage = signUpPage.setFirstName(firstName);
		}
		if (!"".equals(lastName)) {
			signUpPage = signUpPage.setLastName(lastName);
		}
		if (!"".equals(initials)) {
			signUpPage = signUpPage.setInitials(initials);
		}
		if (!"".equals(email)) {
			signUpPage = signUpPage.setEmail(email);
		}
		if (!"".equals(password)) {
			signUpPage = signUpPage.setPassword(password);
			signUpPage = signUpPage.setConfirmPassword(password);
		}
		return (T) signUpPage.clickComplete();    
	}
	
	public Page<?> edit(ChangePersonPage changePersonPage) {
		String firstName = personaData.getPersonFirstName();
		String lastName = personaData.getPersonLastName();
		String initials = personaData.getPersonInitials();
		String email = personaData.getPersonEmail();
		String password = personaData.getPersonPassword();
		
		
		String imageIdentity = personaData.getPersonImageIdentity();
		if (!"".equals(firstName)) {
			changePersonPage = changePersonPage.setFirstName(firstName);
		}
		if (!"".equals(lastName)) {
			changePersonPage = changePersonPage.setLastName(lastName);
		}
		if (!"".equals(initials)) {
			changePersonPage = changePersonPage.setInitials(initials);
		}
		if (!"".equals(email)) {
			changePersonPage = changePersonPage.setEmail(email);
		}
		if (!"".equals(password)) {
			changePersonPage = changePersonPage.setPassword(password);
			changePersonPage = changePersonPage.setConfirmPassword(password);
		}
		if (!"".equals(imageIdentity)) {
			changePersonPage = changePersonPage.setImage(imageIdentity);
		}
		return changePersonPage.clickComplete();    
	}

	public ChangeCardPage selectEdit(CardManipulationPage<?> selectable, int selectableIndex) {
		return selectable.showTools(selectableIndex).selectEditCard(selectableIndex);
	}

	public <T extends TypesPage<T>> T selectEdit(T page, int selectableIndex) {
		page.getEditCard(selectableIndex).click();
		return page;
    }

	public ChangeStatusPage selectEdit(StatusesPage selectable, int selectableIndex) {
		return selectable.selectEditCard(selectableIndex);
    }
	
	@Override
	public ChangePersonPage selectEdit(PeoplePage peoplePage, int selectableIndex) {
		return peoplePage.selectEditCard(selectableIndex);
	}

	public CardManipulationPage<?> delete(CardManipulationPage<?> selectable, int selectableIndex) {
		return selectable.showTools(selectableIndex).selectDeleteCard(0);
	}

	public CardManipulationPage<?> selectArchive(CardManipulationPage<?> selectable, int selectableIndex) {
		return selectable.showTools(selectableIndex).selectArchiveCard(0);
    }

	public ShowCardPage selectShow(CardManipulationPage<?> selectable, int selectableIndex) {
		return selectable.showTools(0).selectShowCard(0);
	}

	public ShowPostItPage selectShow(PostItPage postItPage, int selectableIndex) {
	    return postItPage.selectShowCard(selectableIndex);
    }

	public String getFirstCard() {
		return personaData.getFirstCard();
    }

	public String getFirstColumn(int rowNumber) {
		return personaData.getFirstCardPlannerColumnIdentity(rowNumber);
    }

	public String getSecondColumn(int rowNumber) {
		return personaData.getSecondCardPlannerColumnIdentity(rowNumber);
	}

	public String getThirdColumn(int rowNumber) {
		return personaData.getThirdCardPlannerColumnIdentity(rowNumber);
	}

	public String getBacklog() {
		return personaData.getCardPlannerBacklogIdentity();
    }

	public StoryBoardPage dragCardOntoCardPlannerBacklog(StoryBoardPage cardPlanner) {
		cardPlanner.dragOnto(personaData.getCardPlannerBacklogIdentity(), personaData.getFirstCard());
		return cardPlanner;
    }

	public StoryBoardPage dragCardOntoFirstCardPlannerColumn(StoryBoardPage cardPlanner, int rowNumber) {
		cardPlanner.dragOnto(personaData.getFirstCardPlannerColumnIdentity(rowNumber), personaData.getFirstCard());
		return cardPlanner;
    }
	
	public StoryBoardPage dragCardOntoSecondCardPlannerColumn(StoryBoardPage cardPlanner, int rowNumber) {
		cardPlanner.dragOnto(personaData.getSecondCardPlannerColumnIdentity(rowNumber), personaData.getFirstCard());
		return cardPlanner;
    }
	
	public StoryBoardPage dragCardOntoThirdCardPlannerColumn(StoryBoardPage cardPlanner, int rowNumber) {
		cardPlanner.dragOnto(personaData.getThirdCardPlannerColumnIdentity(rowNumber), personaData.getFirstCard());
		return cardPlanner;
    }

	public StoryBoardPage selectSwimLaneAmount(StoryBoardPage storyBoardPage, int swimLaneAmount) {
		storyBoardPage.selectSwimLaneAmount(swimLaneAmount);
		return storyBoardPage;
    }

	public CardAttachmentsPage uploadWith(CardAttachmentsPage page, String filePath) {
		page.getToggleUploadFormButton().click();
		page.getTitleField().sendKeys(personaData.getAttachmentTitle());
		page.getAttachmentField().sendKeys(filePath);
		page.getUploadButton().click();
		return page;
    }

	public CardAttachmentsPage browseToAttachmentsForCard(CardAttachmentsPage page) {
		return page.navigateTo(personaData.getFirstCard());
    }

	public Page<?> addPostIt(ChangePostItPage page) {
		page.setTitle(personaData.getPostItTitle());
		page.setBody(personaData.getPostItBody());
		page.setColour(personaData.getPostItColour());
	    return page.clickComplete();
    }

	public EffortTypesPage add(EffortTypesPage page) {
		return change(page);
    }

	public EffortTypesPage edit(EffortTypesPage page) {
		return change(page);
	}

	private EffortTypesPage change(EffortTypesPage page) {
	    page.getRevealFormButton().click();
		page.setTitle(personaData.getEffortTypeTitle());
		page.setRate(personaData.getRate());
		page.getCompleteButton().click();
	    return page;
    }

	public EffortTypesPage delete(EffortTypesPage page, int selectableIndex) {
		WebElement deleteButton = page.getDeleteButton(selectableIndex);
		page.confirmAs(true);
		deleteButton.click();
	    return page;
    }

	public ValueTypesPage add(ValueTypesPage page) {
		return change(page);
    }

	public ValueTypesPage edit(ValueTypesPage page) {
		return change(page);
	}

	private ValueTypesPage change(ValueTypesPage page) {
	    page.getRevealFormButton().click();
		page.setTitle(personaData.getValueTypeTitle());
		page.setRate(personaData.getRate());
		page.getCompleteButton().click();
	    return page;
    }

	public ValueTypesPage delete(ValueTypesPage page, int selectableIndex) {
		WebElement deleteButton = page.getDeleteButton(selectableIndex);
		page.confirmAs(true);
		deleteButton.click();
	    return page;
    }

	public CardTypesPage add(CardTypesPage page) {
		return change(page);
    }

	public CardTypesPage edit(CardTypesPage page) {
		return change(page);
	}

	private CardTypesPage change(CardTypesPage page) {
	    page.getRevealFormButton().click();
		page.setTitle(personaData.getCardTypeTitle());
		page.getCompleteButton().click();
	    return page;
    }

	public CardTypesPage delete(CardTypesPage page, int selectableIndex) {
		WebElement deleteButton = page.getDeleteButton(selectableIndex);
		page.confirmAs(true);
		deleteButton.click();
	    return page;
    }

	public <T> T login(LoginPage page) {
		page.setUsername(personaData.getUsername());
		page.setPassword(personaData.getPassword());
		return page.<T>submit();
    }

	public TemplatePage<?> openLinks(TemplatePage<?> page) {
		page.openLinksPanel();
	    return page;
    }

    public void clickOnUploadedFileOn(CardAttachmentsPage page) {
        page.uploadedFileLink().click();
    }
}
