package uk.co.itstherules.cardplanner.model;

import uk.co.itstherules.cardplanner.model.type.EffortTypeModel;
import uk.co.itstherules.cardplanner.model.type.ValueTypeModel;
import uk.co.itstherules.yawf.model.*;
import uk.co.itstherules.yawf.model.persistence.ObjectCache;

public final class CachedInstance {

	public enum Identities implements Identity {
		CONFIGURATION_MODEL("1952bba9-bee2-43e5-a202-81637bf6e479", ConfigurationModel.class),

		PLANNED("6ea38f66-9c49-48a4-8e26-03e3c888de35", StatusModel.class),
		IN_PROGRESS("1e5da83b-0a9d-4428-af28-7187b8718d2b", StatusModel.class),
		DONE("f828ca79-7cca-43ef-89bd-10b3e5825958", StatusModel.class),
		
		SIGN_UP_EMAIL_BODY("9bb5359a-5556-4119-8252-7b40e5e75894", TemplateModel.class),
		SIGN_UP_ADD_BODY("b79fcc3b-09a1-4791-a85a-b20c4d670297", TemplateModel.class),
		SIGN_UP_SHOW_BODY("7e6feb71-d276-4375-be0e-76e34149757d", TemplateModel.class),
		SIGN_UP_CONFIRM_SHOW_BODY("0ca28b66-0ace-4334-990e-faa5aa8771ec", TemplateModel.class),
		SIGN_UP_TEAM_TO_ACCEPT_BODY("db937b0a-71ac-48ae-856e-ca3879bec52e", TemplateModel.class),
		LOGIN_SHOW_BODY("89a913c7-baa0-4894-91c5-0c42a2cad7c7", TemplateModel.class),

		EMPTY_BODY("3e898331-0492-4dab-863b-56f65c21d4be", TemplateModel.class),
		
		CARDPLANNER_TEMPLATE("d3fb23f7-e862-4522-974f-c9af0ec4c0ff", TemplateModel.class), 
		CLEAN_TEMPLATE("03e20d90-2957-4524-8723-5931fede5001", TemplateModel.class),
		SIMPLE_TEMPLATE("ccbd639c-22f1-4bdf-b23f-75eb4fdebbe7", TemplateModel.class), 

		SIGN_UP_EMAIL_PAGE("e4110b5f-ddb1-441c-be4c-12e6bd229718", PageModel.class),
		SIGN_UP_ADD_PAGE("9440fca8-f3f8-48dd-b042-dbef2a08bea2", PageModel.class),
		SIGN_UP_SHOW_PAGE("0410ecfe-a346-4f3b-85fd-b16ce044d3cb", PageModel.class),
		SIGN_UP_CONFIRM_SHOW_PAGE("f4847e22-a2d6-4786-8ae6-cbc399e58fbd", PageModel.class), 
		SIGN_UP_TEAM_TO_ACCEPT_PAGE("15cc9ac4-7b00-48a6-8ce3-d5d38a818e24", PageModel.class), 
		LOGIN_SHOW_PAGE("3d9af2d8-a303-4134-bb4e-268109bd57de", PageModel.class),

		INVISIBLE_PAGE("f500a322-59c7-4384-ad5c-9dd6ab01e907", PageModel.class),
		
		CURRENCY_VALUE_TYPE("00a2df98-7293-471d-965e-365a963b5cfa", ValueTypeModel.class),
		IDEAL_DAY_EFFORT_TYPE("bed78a3f-9c04-40a9-b565-0889f9d782e2", EffortTypeModel.class),
		DEFAULT_ATTACHMENT("894301b2-f8d0-4999-af7a-5747a742fefb", SimpleAttachmentModel.class),
		INVISIBLE_CARD("0a312c5f-a8ae-423e-86d9-7f9ddf84d3ff", CardModel.class),
		USER_STORY_ITEM_TYPE("8d423afc-bc42-47e4-a8ba-f9d0726fa7b9", CardTypeModel.class),
		INVISIBLE_PERSON("82f0fd2a-c3c0-48c8-8cbd-df999eb4f913", PersonModel.class),
		THE_BACKLOG("6efda9ea-ef05-4511-b80e-3c5ac2b98f4d", StatusModel.class),
		EMAIL_DETAILS("6e7d28dc-9dc2-422e-84a0-6d787d353147", EmailDetailsModel.class),
		
		DEFAULT_EFFORT("d0ee62e6-7232-4376-ba4f-53e8fbcae34e", EffortModel.class),
		DEFAULT_VALUE("9e54ecdf-9d22-4d22-8186-b73edfc554e8", ValueModel.class),
		
		BASE_PERSON("fba82635-9578-4801-b377-05423ea059dd", PersonModel.class),
		;
		

		/* 
		
		168b676f-d45a-485b-b4e1-41805a3af456
		105e77ab-8917-41d0-8f1b-d54528911cd1
		9cacefdd-880f-4120-abdb-aecd25303039
		 */
		
		
		private final String identity;
		private final Class<? extends IdentityDeleteable<?>> type;

		private Identities(String identity, Class<? extends IdentityDeleteable<?>> type) { 
			this.identity = identity;
			this.type = type; 
		}
		public String getIdentity() { return identity; }
		public Class<? extends IdentityDeleteable<?>> toInstantiate() { return type; }
	}
	
	public void initialise(ObjectCache objectCache) {
		//Order matters - need the fine grained objects to be there first for the collaborators
		
		SpecialInstances instances = new SpecialInstances(objectCache);
		instances.persistIfNotExists(Identities.CONFIGURATION_MODEL);
		ConfigurationModel configurationModel = objectCache.retrieveByIdentity(ConfigurationModel.class, Identities.CONFIGURATION_MODEL.getIdentity());
		if(configurationModel.getFirstRun()) {
			instances.persistIfNotExists(Identities.PLANNED);
			instances.persistIfNotExists(Identities.IN_PROGRESS);
			instances.persistIfNotExists(Identities.DONE);
			instances.persistIfNotExists(Identities.IDEAL_DAY_EFFORT_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_EFFORT);
			instances.persistIfNotExists(Identities.CURRENCY_VALUE_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_VALUE);
			instances.persistIfNotExists(Identities.THE_BACKLOG);
			instances.persistIfNotExists(Identities.USER_STORY_ITEM_TYPE);
			instances.persistIfNotExists(Identities.DEFAULT_ATTACHMENT);
			instances.persistIfNotExists(Identities.INVISIBLE_PERSON);
			instances.persistIfNotExists(Identities.EMAIL_DETAILS);
			instances.persistIfNotExists(Identities.INVISIBLE_CARD);
			instances.persistIfNotExists(Identities.CARDPLANNER_TEMPLATE);
			instances.persistIfNotExists(Identities.CLEAN_TEMPLATE);
			instances.persistIfNotExists(Identities.SIMPLE_TEMPLATE);
			instances.persistIfNotExists(Identities.SIGN_UP_EMAIL_BODY);
			instances.persistIfNotExists(Identities.SIGN_UP_ADD_BODY);
			instances.persistIfNotExists(Identities.SIGN_UP_SHOW_BODY);
			instances.persistIfNotExists(Identities.SIGN_UP_TEAM_TO_ACCEPT_BODY);
			instances.persistIfNotExists(Identities.LOGIN_SHOW_BODY);
			instances.persistIfNotExists(Identities.SIGN_UP_CONFIRM_SHOW_BODY);
			instances.persistIfNotExists(Identities.EMPTY_BODY);
			instances.persistIfNotExists(Identities.INVISIBLE_PAGE);
			instances.persistIfNotExists(Identities.SIGN_UP_EMAIL_PAGE);
			instances.persistIfNotExists(Identities.SIGN_UP_ADD_PAGE);
			instances.persistIfNotExists(Identities.SIGN_UP_SHOW_PAGE);
			instances.persistIfNotExists(Identities.LOGIN_SHOW_PAGE);
			instances.persistIfNotExists(Identities.SIGN_UP_CONFIRM_SHOW_PAGE);
			instances.persistIfNotExists(Identities.SIGN_UP_TEAM_TO_ACCEPT_PAGE);
			
			instances.persistIfNotExists(Identities.BASE_PERSON);
	
			if(countCountries(objectCache)==0) {
				for (Country country : Country.values()) { objectCache.save(country(country)); }
			}
			configurationModel.firstRunComplete();
			objectCache.save(configurationModel);
		}
    }

	private int countCountries(ObjectCache objectCache) { return objectCache.count(CountryModel.class).intValue(); }
	

	//***************************************************************************************************************************************************	
	//													vv			Here be dragons	grrrrr			vv	
	//***************************************************************************************************************************************************	


	private CountryModel country(Country country) {
        CountryModel countryModel = new CountryModel();
        countryModel.setIdentity(country.getIdentity());
        countryModel.setTitle(country.getTitle());
        countryModel.setCountryCode(country.getCountryCode());
        return countryModel.defaultSetup(null);
	}

	private enum Country {

		AFGHANISTAN("fd9dceb6-176a-4b06-b1ae-ddbf58b9db8d", "Afghanistan", "AF"),
		ALAND_ISLANDS("40926abe-3c4f-49e0-9cb2-8b915dadf924", "Aland Islands", "AX"),
		ALBANIA("dd152bbf-cd61-4c7c-95a6-046689f946f3", "Albania", "AL"),
		ALGERIA("eadfcad3-0cc2-4406-bc4d-1fcf7425371e", "Algeria", "DZ"),
		AMERICAN_SAMOA("cd75ebcd-e1fe-467c-ab4a-8967b8ac4108", "American Samoa", "AS"),
		ANDORRA("49a282f8-6c58-4f57-a515-717b576cf1c5", "Andorra", "AD"),
		ANGOLA("754f431d-3297-41ec-82d3-cf2ec9c53e33", "Angola", "AO"),
		ANGUILLA("bfe9a0fc-2336-4ad0-8e0b-7efa43ef8a24", "Anguilla", "AI"),
		ANTARCTICA("51c00d2e-a5f1-4a50-a405-94ecd8fce0cf", "Antarctica", "AQ"),
		ANTIGUA_AND_BARBUDA("f6aeae72-8658-452b-a790-58d79efff575", "Antigua And Barbuda", "AG"),
		ARGENTINA("92aef8db-3f9c-4102-bcbc-950d1f5ccc1f", "Argentina", "AR"),
		ARMENIA("d0bf7f8a-0fd0-4dee-8bf3-e178577fcba8", "Armenia", "AM"),
		ARUBA("f0983579-a13b-46be-b80a-88252ad7fafd", "Aruba", "AW"),
		AUSTRALIA("be077b02-314a-4e09-858b-007fec3a936b", "Australia", "AU"),
		AUSTRIA("d9442d99-429c-48e7-85f4-06a9b368edd4", "Austria", "AT"),
		AZERBAIJAN("b53cf08b-ab39-47c9-8b3f-3be2d2baefaa", "Azerbaijan", "AZ"),
		BAHAMAS("67c6c0d8-612a-47c5-a4ac-f9a1686bbca8", "Bahamas", "BS"),
		BAHRAIN("b3f5ea02-cba8-4d2d-9b58-3b2466b31f33", "Bahrain", "BH"),
		BANGLADESH("bf68020d-2f5d-489e-ab7e-cdc2c4c10afa", "Bangladesh", "BD"),
		BARBADOS("196b4e9f-5681-48f5-b42c-dbf9033ec1f7", "Barbados", "BB"),
		BELARUS("8702083e-4ebe-4ca5-bf86-4b96ca21ad32", "Belarus", "BY"),
		BELGIUM("e9d518ac-663d-4e70-a12a-8393ac3ec627", "Belgium", "BE"),
		BELIZE("04b78f01-4fb8-458c-b057-413ae3cc490b", "Belize", "BZ"),
		BENIN("3c402e26-99ad-4d34-8e8f-f48698ea4d84", "Benin", "BJ"),
		BERMUDA("2dffedfa-30d9-4517-b903-4d039474604a", "Bermuda", "BM"),
		BHUTAN("3ab17b8c-55bc-4553-8ac3-4a47bec77f02", "Bhutan", "BT"),
		BOLIVIA_PLURINATIONAL_STATE_OF("ffaeceb2-9d79-4935-8e0d-f2eed601ab01", "Bolivia, Plurinational State Of", "BO"),
		BOSNIA_AND_HERZEGOVINA("aa158799-b947-4b05-a859-816343e579ec", "Bosnia And Herzegovina", "BA"),
		BOTSWANA("9059d1a7-cef5-4687-b2db-2b0e6f67b3d4", "Botswana", "BW"),
		BOUVET_ISLAND("073c3093-3975-4258-bc23-7d27339d2170", "Bouvet Island", "BV"),
		BRAZIL("47f5d0f5-43f2-4699-a89d-7230854ea631", "Brazil", "BR"),
		BRITISH_INDIAN_OCEAN_TERRITORY("c8e524eb-ba02-4233-8118-0a5152722fc0", "British Indian Ocean Territory", "IO"),
		BRUNEI_DARUSSALAM("11f0241f-11d0-4107-80a1-5b3fc3454c76", "Brunei Darussalam", "BN"),
		BULGARIA("c1660667-3daa-4b96-9c66-e91ea4022328", "Bulgaria", "BG"),
		BURKINA_FASO("64cb501c-5482-4056-b58b-9d1a7156f40f", "Burkina Faso", "BF"),
		BURUNDI("600d090f-0e87-4e76-8b56-bc4526c56425", "Burundi", "BI"),
		CAMBODIA("d0921ebd-3602-4389-a525-8c9099a2703f", "Cambodia", "KH"),
		CAMEROON("94788c03-27f1-4dbd-ba86-746d9f0c132f", "Cameroon", "CM"),
		CANADA("93dcb9d7-51fd-4f24-b8eb-c4df1d93273d", "Canada", "CA"),
		CAPE_VERDE("7a49eb0a-d185-42f3-95c5-43e54dad2c66", "Cape Verde", "CV"),
		CAYMAN_ISLANDS("60bf500f-7968-465d-b918-6748a7988df3", "Cayman Islands", "KY"),
		CENTRAL_AFRICAN_REPUBLIC("8fda6b80-8858-43a8-990b-00b2c47bcd88", "Central African Republic", "CF"),
		CHAD("f32ec67c-4a22-46a2-802c-c194f7e810ad", "Chad", "TD"),
		CHILE("23bd5656-425e-4a4f-8d55-2f67d83bd377", "Chile", "CL"),
		CHINA("2825ce03-e699-44b5-8511-116c50224915", "China", "CN"),
		CHRISTMAS_ISLAND("7e5925fc-dd33-41c2-a8d7-a453887ac84a", "Christmas Island", "CX"),
		COCOS_KEELING_ISLANDS("41abd7f7-b36c-44d9-8356-607e83cc6f3f", "Cocos (keeling) Islands", "CC"),
		COLOMBIA("b2435ebd-4521-45c1-8ed5-5baaaf274132", "Colombia", "CO"),
		COMOROS("25017807-084e-4388-8601-843e74033afd", "Comoros", "KM"),
		CONGO("1da97556-378b-436d-938d-3dbc8c910252", "Congo", "CG"),
		CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE("94aaf792-d6e8-4515-9729-7bc84a7b5d32", "Congo, The Democratic Republic Of The", "CD"),
		COOK_ISLANDS("7c05a24f-3c70-42f9-a4d8-1c0ca5561022", "Cook Islands", "CK"),
		COSTA_RICA("b66cb94e-a5e2-48ae-bdf7-10edaabbccc9", "Costa Rica", "CR"),
		COTE_D_IVOIRE("4370d499-e489-4c49-b204-26a715fb02fa", "Cote D'ivoire", "CI"),
		CROATIA("561b97cd-c27d-4b2a-975b-400a68225065", "Croatia", "HR"),
		CUBA("a089dd8b-5659-44c2-8e15-d6de5237b438", "Cuba", "CU"),
		CYPRUS("a3a67a44-ea96-4751-8c88-ab760051c856", "Cyprus", "CY"),
		CZECH_REPUBLIC("7fa95dc4-b4ac-46fb-9a54-efd72d80e0d0", "Czech Republic", "CZ"),
		DENMARK("da3a7c00-af5e-4d35-9cfb-ee811393115e", "Denmark", "DK"),
		DJIBOUTI("d912940d-9015-4c4f-a967-2db6f9d7c9c4", "Djibouti", "DJ"),
		DOMINICA("1d372994-251d-48c4-805c-05876abb1ab3", "Dominica", "DM"),
		DOMINICAN_REPUBLIC("d3d4c0e7-a4ff-44e5-9c1f-5a664e8eb7e6", "Dominican Republic", "DO"),
		ECUADOR("b63be5a4-560b-46c7-8246-b6bcd3004b2e", "Ecuador", "EC"),
		EGYPT("d28e6ce3-77ef-4f0f-9556-1f2255df425c", "Egypt", "EG"),
		EL_SALVADOR("2cbc51c0-ce36-40b8-9aba-97c67b55ad2d", "El Salvador", "SV"),
		EQUATORIAL_GUINEA("215e175d-2e8c-41c3-a528-818a3a9e2d33", "Equatorial Guinea", "GQ"),
		ERITREA("a4501fe3-ac84-4dc3-905c-e7f28a6c72c2", "Eritrea", "ER"),
		ESTONIA("f6c5f1b9-71b4-47db-9d3f-6854c5c8decf", "Estonia", "EE"),
		ETHIOPIA("42f797be-75cd-493d-b51b-754fa4da9297", "Ethiopia", "ET"),
		FALKLAND_ISLANDS_MALVINAS("2c77ac76-ffd3-448b-a75a-7668dea6cb62", "Falkland Islands (malvinas)", "FK"),
		FAROE_ISLANDS("3ec3ed28-0a46-44b4-8be4-ebbd05c9649e", "Faroe Islands", "FO"),
		FIJI("d0852e98-39d4-427f-bedd-12edd15d5ff5", "Fiji", "FJ"),
		FINLAND("ef953252-ae9d-462a-9c98-a313e5521f7b", "Finland", "FI"),
		FRANCE("2b3605a8-9082-4682-88ef-a01c6798cceb", "France", "FR"),
		FRENCH_GUIANA("e6b04856-f221-4fd3-a947-e6d76d0fe45e", "French Guiana", "GF"),
		FRENCH_POLYNESIA("cf3e2c40-3359-4810-be7d-eb409dccb7e3", "French Polynesia", "PF"),
		FRENCH_SOUTHERN_TERRITORIES("c929377f-7497-473a-86a5-9ffa1adb3ba5", "French Southern Territories", "TF"),
		GABON("9726a960-aab8-4d6f-88a6-1c55c8717c9e", "Gabon", "GA"),
		GAMBIA("82882e7b-d294-4ecb-b593-06fcb5118259", "Gambia", "GM"),
		GEORGIA("34d3cb26-a61a-4d27-b16b-3c42396f2d2e", "Georgia", "GE"),
		GERMANY("15cb3a1e-f899-48ce-9b08-4e2a862d1e28", "Germany", "DE"),
		GHANA("30d14214-b6b5-45ef-b26d-f6e8e0e2aaf3", "Ghana", "GH"),
		GIBRALTAR("fe1ae37a-1cf4-4431-8085-936cce3d5906", "Gibraltar", "GI"),
		GREECE("926a2fb4-b9ea-419e-96e2-0e836d21f95b", "Greece", "GR"),
		GREENLAND("c6285bca-b44c-4cb0-8f27-c1ebd0a95c65", "Greenland", "GL"),
		GRENADA("584c1495-06c2-4d34-bcba-2a66230209fa", "Grenada", "GD"),
		GUADELOUPE("3315f83e-e729-4656-9ab2-e20abc6a834f", "Guadeloupe", "GP"),
		GUAM("8f5e6d2e-e441-430f-9e18-09ceec7498c8", "Guam", "GU"),
		GUATEMALA("b8f8aebb-5a5d-4ac3-b12a-78e166b90b40", "Guatemala", "GT"),
		GUERNSEY("95570174-230f-4b9c-bf54-d7db03c65424", "Guernsey", "GG"),
		GUINEA("c71109d6-f82c-417a-a022-90c3056d517b", "Guinea", "GN"),
		GUINEA_BISSAU("4bf3382f-4c14-4e75-866f-4e2a1c85e4a4", "Guinea-Bissau", "GW"),
		GUYANA("7bdd2d8a-1b35-43b9-b639-274c9d82760f", "Guyana", "GY"),
		HAITI("f810d698-fbeb-4829-8f3d-009f2f30f60a", "Haiti", "HT"),
		HEARD_ISLAND_AND_MCDONALD_ISLANDS("f6297445-4381-4cc5-859c-15e1e7e82380", "Heard Island And Mcdonald Islands", "HM"),
		HONDURAS("d317deec-69f3-4fb5-abc7-7a20408c9f4c", "Honduras", "HN"),
		HONG_KONG("cbdbbb19-b551-4eb8-a830-0aa08d33b357", "Hong Kong", "HK"),
		HUNGARY("dddf8c1e-49fb-4c47-9b05-cc8671337781", "Hungary", "HU"),
		ICELAND("38471f6d-026c-4b75-a1dd-362685c57c0a", "Iceland", "IS"),
		INDIA("adaade36-9010-4c7d-b334-01283390487b", "India", "IN"),
		INDONESIA("ef626581-26ac-480f-8bc9-8ebbf14fff2a", "Indonesia", "ID"),
		IRAN_ISLAMIC_REPUBLIC_OF("3b33d8c5-9ec5-4cb6-8093-8ada4b17ad8f", "Iran, Islamic Republic Of", "IR"),
		IRAQ("6ef8fb42-a24b-41f9-9afa-1d506f851c7c", "Iraq", "IQ"),
		IRELAND("9e929cc0-f448-4a5f-b01c-9884810c62e9", "Ireland", "IE"),
		ISLE_OF_MAN("c873a353-e7d9-446c-9727-12343dfc7c31", "Isle Of Man", "IM"),
		ISRAEL("a6031249-c9fd-4ace-8b4d-eebcfb5f3a29", "Israel", "IL"),
		ITALY("e582a959-50e7-4c04-9ce3-70b3ef2e19d6", "Italy", "IT"),
		JAMAICA("bed55c9b-738f-431f-8733-ba17d885a89d", "Jamaica", "JM"),
		JAPAN("8e10ae6a-b342-4b3f-ad57-dee6de5c9fc7", "Japan", "JP"),
		JERSEY("8d27007c-2d9f-49c2-9644-d0b0912bd0e8", "Jersey", "JE"),
		JORDAN("f0355911-628a-485e-a1c9-b113b5e3a271", "Jordan", "JO"),
		KAZAKHSTAN("c67653e4-5115-4aa0-a38d-3fcc617fd09a", "Kazakhstan", "KZ"),
		KENYA("2b702354-a677-459a-a1f1-fb0599efea98", "Kenya", "KE"),
		KIRIBATI("c6c7083d-cdc3-4490-a8b1-d659d915c5d8", "Kiribati", "KI"),
		KOREA_DEMOCRATIC_PEOPLE_S_REPUBLIC_OF("0447ce1c-ffe8-445f-bce6-59458bf8f385", "Korea, Democratic People's Republic Of", "KP"),
		KOREA_REPUBLIC_OF("9c06a8bb-b10e-4104-96d4-e8a155928584", "Korea, Republic Of", "KR"),
		KUWAIT("b1839f3f-09a1-43c5-b04b-3fb1a870c5fd", "Kuwait", "KW"),
		KYRGYZSTAN("93bfe88f-4ecb-4988-8ffa-dfaf3452b18b", "Kyrgyzstan", "KG"),
		LAO_PEOPLE_S_DEMOCRATIC_REPUBLIC("4ae85dd8-ed3b-4fbb-a3a9-3960a1da0e3b", "Lao People's Democratic Republic", "LA"),
		LATVIA("2c288471-5df4-4ce4-b559-f5f2f3c0ef20", "Latvia", "LV"),
		LEBANON("e15b1d86-21a4-42fc-aa97-f15c3a687cc3", "Lebanon", "LB"),
		LESOTHO("1e70e273-1bf0-4be0-9c6a-b01821c777e6", "Lesotho", "LS"),
		LIBERIA("4a75201f-7048-4361-a54f-6505ac5cbbdc", "Liberia", "LR"),
		LIBYAN_ARAB_JAMAHIRIYA("9765e574-9fc0-42ac-8178-939bd8d7a8d5", "Libyan Arab Jamahiriya", "LY"),
		LIECHTENSTEIN("e219124c-fe8a-478d-9648-f6f5b7546c48", "Liechtenstein", "LI"),
		LITHUANIA("a24dad47-8c93-4529-87d6-ba6a7396f7e1", "Lithuania", "LT"),
		LUXEMBOURG("5c2660eb-f531-4ce9-91c8-f200313af113", "Luxembourg", "LU"),
		MACAO("d5b8fa72-24c8-4098-a6cf-99f08413a6ec", "Macao", "MO"),
		MACEDONIA_THE_FORMER_YUGOSLAV_REPUBLIC_OF("275aed90-13db-4c3b-b436-fa7739e80318", "Macedonia, The Former Yugoslav Republic Of", "MK"),
		MADAGASCAR("a1ec5f07-bca3-461e-a42d-d8547a4a9fb5", "Madagascar", "MG"),
		MALAWI("1f5a9c74-e764-4c0c-98bf-d6d6adcc9830", "Malawi", "MW"),
		MALAYSIA("3419afd9-938b-405b-8fc8-d8903a417d09", "Malaysia", "MY"),
		MALDIVES("3c3c6c2b-c3c4-4bca-8d3b-5f023f308ffd", "Maldives", "MV"),
		MALI("4de3dc26-e43f-470d-897d-5bc3499a8a42", "Mali", "ML"),
		MALTA("831a0d22-3c3e-4932-a04d-ae36ee7ad72a", "Malta", "MT"),
		MARSHALL_ISLANDS("fb3ee713-68e9-42ae-b039-42bbf5c097b2", "Marshall Islands", "MH"),
		MARTINIQUE("d22252ef-c362-4b95-9714-98d8f3f5c53c", "Martinique", "MQ"),
		MAURITANIA("aec6e210-3f63-400c-aea4-673e51ea31c7", "Mauritania", "MR"),
		MAURITIUS("b4979420-7dcb-4456-9c25-31b5eb8858f2", "Mauritius", "MU"),
		MAYOTTE("79bbbed3-fa9d-403f-8269-381c6519e019", "Mayotte", "YT"),
		MEXICO("d429f1f5-07b8-401e-b9b9-c523a2c9ecaa", "Mexico", "MX"),
		MICRONESIA_FEDERATED_STATES_OF("1cbb8cc5-ea46-45a1-8ca2-f4195f332e08", "Micronesia, Federated States Of", "FM"),
		MOLDOVA_REPUBLIC_OF("b9733523-a333-4df9-80ea-82f8874a69c5", "Moldova, Republic Of", "MD"),
		MONACO("f8d9802d-e665-4f03-95ff-2c3708c918ff", "Monaco", "MC"),
		MONGOLIA("3c001a73-6fee-498d-8b8b-721c80bfadc6", "Mongolia", "MN"),
		MONTENEGRO("4b529f82-d22c-49ec-a9c3-1039391fce30", "Montenegro", "ME"),
		MONTSERRAT("fbfd571d-7f32-492a-a181-07844e8a9f1d", "Montserrat", "MS"),
		MOROCCO("8222b990-7daf-41fc-8d11-003e7793f02c", "Morocco", "MA"),
		MOZAMBIQUE("c5daaeca-df29-4255-b884-e25d40638485", "Mozambique", "MZ"),
		MYANMAR("d80f347f-63c5-4228-bc19-018370dd16d9", "Myanmar", "MM"),
		NAMIBIA("74010e6b-e7da-4502-bcb5-86a16a8198b0", "Namibia", "NA"),
		NAURU("7e0109ac-ad59-4cb2-a603-b8a01900da6b", "Nauru", "NR"),
		NEPAL("3bd31706-f8d6-406a-98c4-f5f7631580fb", "Nepal", "NP"),
		NETHERLANDS("f5815aee-eca3-4d1f-8315-be01152e0487", "Netherlands", "NL"),
		NETHERLANDS_ANTILLES("22e67eac-26f0-453a-9aff-0e5590df5b9f", "Netherlands Antilles", "AN"),
		NEW_CALEDONIA("9624494b-c52f-4556-a5bd-cc193a058827", "New Caledonia", "NC"),
		NEW_ZEALAND("85bf7d23-62ee-4d30-80dc-a0a9c0a0608b", "New Zealand", "NZ"),
		NICARAGUA("5ffb9bb4-fabf-4fea-9acf-d941d9c7a9c2", "Nicaragua", "NI"),
		NIGER("de7493b9-cdea-42ed-8aaa-25a222c80212", "Niger", "NE"),
		NIGERIA("38adb25a-361e-4763-b211-3ec93a646f1d", "Nigeria", "NG"),
		NIUE("111da22b-cca8-4edf-9d9c-f7431804e345", "Niue", "NU"),
		NORFOLK_ISLAND("d41387a6-3658-41be-a1f7-a63c9d6747b4", "Norfolk Island", "NF"),
		NORTHERN_MARIANA_ISLANDS("3f11bb0f-0ce2-44b4-be62-662c458a8fb7", "Northern Mariana Islands", "MP"),
		NORWAY("644c52ad-1266-4e58-925a-352d83e9828f", "Norway", "NO"),
		OMAN("3b0b739f-1386-47d3-ae2b-c8216a7c0923", "Oman", "OM"),
		PAKISTAN("d144e8a9-d09f-4f40-9930-558231f6c4ce", "Pakistan", "PK"),
		PALAU("71445004-6914-4806-9ff0-f1ffbe962a26", "Palau", "PW"),
		PALESTINIAN_TERRITORY_OCCUPIED("01014448-ac3c-421e-ab4d-f6167784b089", "Palestinian Territory, Occupied", "PS"),
		PANAMA("59d4cda7-6d35-4a0e-a88f-d26224ba4dfe", "Panama", "PA"),
		PAPUA_NEW_GUINEA("d9e85b1d-1d1b-4b26-9f28-9428c6284d98", "Papua New Guinea", "PG"),
		PARAGUAY("340ca652-1d0d-415f-a2fa-b7c5d7395f78", "Paraguay", "PY"),
		PERU("2194f4cf-b504-4202-8162-b9b01d454e2d", "Peru", "PE"),
		PHILIPPINES("d4299f12-1741-465e-ad1d-7c377f2d5092", "Philippines", "PH"),
		PITCAIRN("0eeb1f23-f8d2-47e5-b037-1ba8f9b85f63", "Pitcairn", "PN"),
		POLAND("722c54b5-ee1c-4a2f-b298-963b4dc6dedd", "Poland", "PL"),
		PORTUGAL("e50e1fd9-2efa-4c6b-a5b9-50746f69f75c", "Portugal", "PT"),
		PUERTO_RICO("c53b6b70-a876-4ee3-8806-af0216e30ca3", "Puerto Rico", "PR"),
		QATAR("be05fbd9-a46f-41ee-83e9-2cd9831f140f", "Qatar", "QA"),
		REUNION("5deaf13f-255c-434f-90a1-a0b38f53783d", "Reunion", "RE"),
		ROMANIA("a83734c5-d438-43e5-b79f-b82715e5ece5", "Romania", "RO"),
		RUSSIAN_FEDERATION("9aa74ee3-b6aa-4cf9-92d6-419a8fa6d720", "Russian Federation", "RU"),
		RWANDA("84a2e0eb-adaa-461c-bc80-eafcdc63e156", "Rwanda", "RW"),
		SAINT_BARTHELEMY("43f218a0-de19-4846-bd96-7e322fea219b", "Saint Barthelemy", "BL"),
		SAINT_HELENA_ASCENSION_AND_TRISTAN_DA_CUNHA("85c758f6-d480-4283-a4f4-73abb1545eb4", "Saint Helena, Ascension And Tristan Da Cunha", "SH"),
		SAINT_KITTS_AND_NEVIS("74f1cede-cd49-4b06-bd18-d3575b3ea4e0", "Saint Kitts And Nevis", "KN"),
		SAINT_LUCIA("7e291f2a-d659-46e3-885a-4f7bc3170647", "Saint Lucia", "LC"),
		SAINT_MARTIN("8c66def5-8c2d-4142-955f-3707c9922305", "Saint Martin", "MF"),
		SAINT_PIERRE_AND_MIQUELON("12b0dab3-c02a-4a5f-9e32-7c3d163d8e52", "Saint Pierre And Miquelon", "PM"),
		SAINT_VINCENT_AND_THE_GRENADINES("01fb62ba-22aa-4884-9118-effe35d9fe17", "Saint Vincent And The Grenadines", "VC"),
		SAMOA("6a9caafb-3195-4f4a-b200-e53145e71a3c", "Samoa", "WS"),
		SAN_MARINO("ceb3505e-220a-4997-abcd-5fadeff63520", "San Marino", "SM"),
		SAO_TOME_AND_PRINCIPE("e4562729-1bc1-4b14-9b68-2fb936d6b8ce", "Sao Tome And Principe", "ST"),
		SAUDI_ARABIA("995cce8a-e521-4c0a-8588-f0132c392ab6", "Saudi Arabia", "SA"),
		SENEGAL("69c3c569-ba29-4123-97f9-b29474f2372f", "Senegal", "SN"),
		SERBIA("f470cb9b-104d-4a10-9e72-1752cb943793", "Serbia", "RS"),
		SEYCHELLES("5eeecc5f-31b8-4bfd-91ca-6fd57077fb3d", "Seychelles", "SC"),
		SIERRA_LEONE("aaa14539-5f6f-4c2c-ba7e-61a865c6376e", "Sierra Leone", "SL"),
		SINGAPORE("e0e5075e-47a4-40c3-b770-8a7e77c9b21a", "Singapore", "SG"),
		SLOVAKIA("b6913bae-cc99-47c1-ac56-1650e13a1e1f", "Slovakia", "SK"),
		SLOVENIA("5c146721-aef4-46a8-b16c-733b93f8cfcc", "Slovenia", "SI"),
		SOLOMON_ISLANDS("f855ef80-4781-4709-951d-4afb1bf500df", "Solomon Islands", "SB"),
		SOMALIA("1be70779-dd6d-495b-91ab-b93a2a31a34d", "Somalia", "SO"),
		SOUTH_AFRICA("d532f11c-b8f6-490c-97b4-54f2eefe87b0", "South Africa", "ZA"),
		SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("b1b9aa0f-31de-48a5-9d4b-112172289170", "South Georgia And The South Sandwich Islands", "GS"),
		SPAIN("31132240-2eaf-4011-9784-3686488eab1f", "Spain", "ES"),
		SRI_LANKA("72ad604d-cfc5-4237-a353-78b55d04be93", "Sri Lanka", "LK"),
		SUDAN("f617740c-52fe-4f9f-b8ac-070a19cc4ee6", "Sudan", "SD"),
		SURINAME("f8934ae9-c198-409e-8d70-0da1de92b3eb", "Suriname", "SR"),
		SVALBARD_AND_JAN_MAYEN("9d762966-a594-459f-8ea4-654e59b982d6", "Svalbard And Jan Mayen", "SJ"),
		SWAZILAND("06796721-9618-42f1-bfb3-e2dfbc1a4999", "Swaziland", "SZ"),
		SWEDEN("57bc3a8c-f283-4efd-bde2-ace45504da9f", "Sweden", "SE"),
		SWITZERLAND("44b23e94-7cd5-478a-9076-3dc875d66a28", "Switzerland", "CH"),
		SYRIAN_ARAB_REPUBLIC("ad11ee09-d28d-400b-bc15-b24a5fc12818", "Syrian Arab Republic", "SY"),
		TAIWAN_PROVINCE_OF_CHINA("3c40f307-8080-4245-b60c-c9cce6651586", "Taiwan, Province Of China", "TW"),
		TAJIKISTAN("47c41f54-a3e3-4b1f-afa2-3e61649d0eb2", "Tajikistan", "TJ"),
		TANZANIA_UNITED_REPUBLIC_OF("87acc359-54da-4abf-8721-af93d52879b0", "Tanzania, United Republic Of", "TZ"),
		THAILAND("3d4e6c8f-2198-407a-8cd2-d8a6f5aafe5f", "Thailand", "TH"),
		TIMOR_LESTE("0aa4e6c9-1826-4eea-87df-cc105a1ef7c4", "Timor-Leste", "TL"),
		TOGO("b5a19bda-2ba2-4297-9ddc-62cf71befcee", "Togo", "TG"),
		TOKELAU("2b111fd2-d358-4a9d-b32e-a281235ae144", "Tokelau", "TK"),
		TONGA("b379376a-b511-4803-9703-66069d73bf0a", "Tonga", "TO"),
		TRINIDAD_AND_TOBAGO("ad6b119a-4460-4e3e-b4dd-01deed210d62", "Trinidad And Tobago", "TT"),
		TUNISIA("a0069a05-fa16-4edc-99f5-48079ada5053", "Tunisia", "TN"),
		TURKEY("4a9ab8f8-e32b-4f94-a52b-a117d7405ff8", "Turkey", "TR"),
		TURKMENISTAN("77a7d961-537b-4bf3-9f4c-0e60ebc1668d", "Turkmenistan", "TM"),
		TURKS_AND_CAICOS_ISLANDS("19371e96-ad93-4276-8f0e-8a0fec982433", "Turks And Caicos Islands", "TC"),
		TUVALU("aa3c5599-03b9-4721-a663-4cdb0d9c51aa", "Tuvalu", "TV"),
		UGANDA("ca6da09e-6b9c-49e9-b087-c20e99c449d1", "Uganda", "UG"),
		UKRAINE("fad00ff8-4bc6-4ec7-a6b2-f16102badf13", "Ukraine", "UA"),
		UNITED_ARAB_EMIRATES("6e076a70-c9a3-4833-a824-21e6053e9bb6", "United Arab Emirates", "AE"),
		UNITED_KINGDOM("7e274de5-1030-4c36-b26c-4cc223969f27", "United Kingdom", "GB"),
		UNITED_STATES("8a541ad5-d238-4b15-b533-e6f528942268", "United States", "US"),
		UNITED_STATES_MINOR_OUTLYING_ISLANDS("6ad6bdea-cb90-4ae2-ab63-52dd5b0dc0e1", "United States Minor Outlying Islands", "UM"),
		URUGUAY("5323dcbf-a515-4a91-a7e5-1b4a8ca78577", "Uruguay", "UY"),
		UZBEKISTAN("46085b6e-aeea-4e32-878d-b94b72d5cdbe", "Uzbekistan", "UZ"),
		VANUATU("10095ea8-af7b-4128-9e0b-3e502571cb8e", "Vanuatu", "VU"),
		VATICAN_CITY_STATE("5866497a-dc58-4fc9-8535-4834b5169eef", "Vatican City State", "VA"),
		VENEZUELA_BOLIVARIAN_REPUBLIC_OF("d872d410-fd89-4474-9474-bb506e4cf26f", "Venezuela, Bolivarian Republic Of", "VE"),
		VIET_NAM("65673902-7f12-4273-b841-24bcf3a6a147", "Viet Nam", "VN"),
		VIRGIN_ISLANDS_BRITISH("b6b3a8e3-cbb8-4a57-88ed-d3ec2ee679d4", "Virgin Islands, British", "VG"),
		VIRGIN_ISLANDS_U_S("6e2b92a2-661c-446a-87da-f1e789d176e4", "Virgin Islands, U.S.", "VI"),
		WALLIS_AND_FUTUNA("3a125e2b-59a8-4aa0-9119-c06eba99b805", "Wallis And Futuna", "WF"),
		WESTERN_SAHARA("55e5dc71-7344-4850-ae72-d922210106b4", "Western Sahara", "EH"),
		YEMEN("d6818d75-0560-4b9c-b33b-78a67c2eb334", "Yemen", "YE"),
		ZAMBIA("2fcee165-3d21-41dd-a32f-1ca4531d75ed", "Zambia", "ZM"),
		ZIMBABWE("dd84778b-37a0-4134-a961-488a2cec8266", "Zimbabwe", "ZW");


		private final String identity;
		private final String title;
		private final String countryCode;
		
		private Country(String identity, String title, String countryCode){
			this.identity = identity;
			this.title = title;
			this.countryCode = countryCode; 
		}
		public String getIdentity() { return identity; }
		public String getTitle() { return title; }
		public String getCountryCode() { return countryCode; }
		@Override public String toString() { return title; }
	}
	
}
