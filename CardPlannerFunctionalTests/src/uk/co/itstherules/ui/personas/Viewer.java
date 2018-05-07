package uk.co.itstherules.ui.personas;

import uk.co.itstherules.ui.pages.list.CardManipulationPage;
import uk.co.itstherules.ui.pages.show.ShowCardPage;

public interface Viewer {
	ShowCardPage selectShow(CardManipulationPage<?> selectable, int selectableIndex);
}