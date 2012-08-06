package uk.co.itstherules.ui.personas;

import uk.co.itstherules.ui.pages.Page;
import uk.co.itstherules.ui.pages.change.ChangeCardPage;
import uk.co.itstherules.ui.pages.list.StatusesPage;

public interface Adder {
	
	Page<?> add(ChangeCardPage changeable);

	StatusesPage add(StatusesPage statuses);
}