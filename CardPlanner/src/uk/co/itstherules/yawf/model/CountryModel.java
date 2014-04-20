package uk.co.itstherules.yawf.model;

import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.NotBlank;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

@Entity 
public final class CountryModel extends IdentifiableDeleteableModel<CountryModel> {
	
	@Column(length=2) @QueryKey("countryCode") @MaxLength(value=2) @NotBlank private String countryCode;
	@Column(length=2) @QueryKey("languageCode") @MaxLength(value=2) @NotBlank private String languageCode;
	
    @Override public CountryModel defaultSetup(ObjectCache objectCache) {
		this.languageCode = "en";
	    return this;
	}
	
	public Locale getLocale() { return new Locale(languageCode, countryCode); }
	public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
	public String getCountryCode() { return countryCode; }
	public String getLanguageCode() { return languageCode; }
	
}