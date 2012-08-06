package uk.co.itstherules.ui.personas;

import java.text.MessageFormat;
import java.util.Properties;

public class PersonaFixture {

	private final Properties properties;

	public PersonaFixture(Properties properties) {
		this.properties = properties;
    }

	public String getCardTitle() {
		return get("card.title");
    }

	public String getCardBody() {
		return get("card.body");
    }

	public String getEffortAmount() {
		return get("card.effort.amount");
    }

	public String getValueAmount() {
		return get("card.value.amount");
    }

	public String[] getTags() {
		return get("card.tags").split(",");
    }

	public String getStatusTitle() {
		return get("status.title");
    }

	public String getStatusToDrag() {
		return get("status.to.drag");
    }

	public String getCardParent()  {
		return get("card.parent");
    }

	public String getPersonTitle()  {
		return get("person.title");
    }

	public String getPersonFirstName() {
		return get("person.firstName");
    }

	public String getPersonLastName() {
		return get("person.lastName");
    }

	public String getPersonInitials()  {
		return get("person.initials");
    }

	public String getPersonEmail()  {
		return get("person.email");
    }

	public String getPersonPassword()  {
		return get("person.password");
    }

	public String getPersonImageIdentity() {
		return get("person.image.identity");
    }

	public String getPersonImageName() {
		return get("person.image.name");
    }

	public String getFirstCard() {
		return get("cardplanner.1.card.identity");
    }

	public String getFirstCardPlannerColumnIdentity(int rowNumber) {
		String firstColumn = get("cardplanner.1.column.identity");
		return MessageFormat.format(firstColumn, rowNumber);
    }

	public String getSecondCardPlannerColumnIdentity(int rowNumber) {
		String firstColumn = get("cardplanner.2.column.identity");
		return MessageFormat.format(firstColumn, rowNumber);
    }

	public String getThirdCardPlannerColumnIdentity(int rowNumber) {
		String firstColumn = get("cardplanner.3.column.identity");
		return MessageFormat.format(firstColumn, rowNumber);
    }
	
	public String getCardPlannerBacklogIdentity() {
		return get("cardplanner.backlog.column.identity");
    }
	
	public Integer getInteger(PersonaKey key) {
		String value = get(key.toString());
		return Integer.parseInt(value);
    }

	public String get(String key) {
		return properties.getProperty(key, "");
    }

	public String getFilePathForUpload() {
		return get("file.path.for.uploading");
    }

	public String getAttachmentTitle() {
		return get("attachment.title");
    }

	public String getAttachmentIdentity() {
		return get("attachment.identity");
    }

	public String getPostItTitle() {
		return get("postit.title");
    }

	public String getPostItBody() {
		return get("postit.body");
    }

	public String getPostItColour() {
		return get("postit.colour");
    }

	public String getValueTypeTitle() {
	    return get("valuetype.title");
    }

	public String getEffortTypeTitle() {
	    return get("efforttype.title");
    }

	public String getRate() {
	    return get("rate");
    }

	public String getCardTypeTitle() {
	    return get("cardtype.title");
    }

	public String getIdentifier(String identifier) {
	    return get(identifier+".identity");
    }

	public String getAuthenticationTokenOne() {
	    return get("authenticationToken.one");
    }

	public String getAuthenticationTokenTwo() {
	    return get("authenticationToken.two");
    }

	public String getUsername() {
	    return get("username");
    }

	public String getPassword() {
	    return get("password");
    }

}
