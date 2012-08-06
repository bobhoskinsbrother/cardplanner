package uk.co.itstherules.yawf.model.captcha;

import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaFactory;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import uk.co.itstherules.yawf.inbound.ValuesProvider;

public class Captcha {

	private Captcha() { }
	
	public static String getHtml(ValuesProvider provider) {
		ReCaptcha captcha = ReCaptchaFactory.newReCaptcha(provider.getString("captchaPublicKey"), provider.getString("captchaPrivateKey"), false);
	    return captcha.createRecaptchaHtml(null, null);
	}

	public static boolean isValid(ValuesProvider provider) {
		String remoteAddress = provider.getString("RemoteAddress");
        String challenge = provider.getString("recaptcha_challenge_field");
        String response = provider.getString("recaptcha_response_field");
        ReCaptchaImpl captcha = new ReCaptchaImpl();
        captcha.setPrivateKey(provider.getString("captchaPrivateKey"));
        ReCaptchaResponse captchaResponse = captcha.checkAnswer(remoteAddress, challenge, response);
        return captchaResponse.isValid();
    }
}
