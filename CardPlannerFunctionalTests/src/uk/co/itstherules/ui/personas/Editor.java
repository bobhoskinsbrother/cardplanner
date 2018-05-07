package uk.co.itstherules.ui.personas;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;
import uk.co.itstherules.ui.pages.change.ChangePersonPage;
import uk.co.itstherules.ui.pages.change.ChangeStatusPage;
import uk.co.itstherules.ui.pages.list.CardManipulationPage;
import uk.co.itstherules.ui.pages.list.PeoplePage;
import uk.co.itstherules.ui.pages.list.StatusesPage;


public interface Editor {
	
	ChangePersonPage selectEdit(PeoplePage peoplePage, int selectableIndex);
	ChangeCardPage selectEdit(CardManipulationPage<?> selectable, int selectableIndex);
    ChangeStatusPage selectEdit(StatusesPage selectable, int selectableIndex);
    
	Page<?> edit(ChangePersonPage changePerson);
	StatusesPage edit(ChangeStatusPage selected);
	Page<?> edit(ChangeCardPage changeable);

}