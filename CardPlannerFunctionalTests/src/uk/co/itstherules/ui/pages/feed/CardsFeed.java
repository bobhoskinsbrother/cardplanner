package uk.co.itstherules.ui.pages.feed;

import java.io.IOException;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class CardsFeed  {

	private final String appRoot;

	public CardsFeed(String appRoot) {
		this.appRoot = appRoot;
	}


	public String atomFeedFor(String identity) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet method = new HttpGet(this.appRoot + "/Cards/Atom/" + identity + "/atom.xml");
	        HttpResponse reply = client.execute(method);
	        HttpEntity entity = reply.getEntity();
	        ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
			entity.writeTo(outputstream);
			EntityUtils.consume(entity);
	        return outputstream.toString("UTF8");
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
    }
}
