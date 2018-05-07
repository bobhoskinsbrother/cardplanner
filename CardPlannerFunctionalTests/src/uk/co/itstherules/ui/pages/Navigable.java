package uk.co.itstherules.ui.pages;

public interface Navigable<T>  {
	T navigateTo(String identity, String... optional);
}
