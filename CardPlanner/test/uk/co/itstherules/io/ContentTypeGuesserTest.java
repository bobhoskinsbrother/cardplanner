package uk.co.itstherules.io;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ContentTypeGuesserTest {
	
	@Test
    public void canGuessType() throws Exception {
		assertEquals("image/jpeg", ContentTypeGuesser.guessFrom(".jpg"));
		assertEquals("image/jpeg", ContentTypeGuesser.guessFrom(".jpeg"));
		assertEquals("image/gif", ContentTypeGuesser.guessFrom(".gif"));
		assertEquals("image/png", ContentTypeGuesser.guessFrom(".png"));
		assertEquals("text/html", ContentTypeGuesser.guessFrom(".html"));
		assertEquals("text/plain", ContentTypeGuesser.guessFrom(".txt"));
    }
	
	@Test
	public void canGiveBogStandardAnswerForUnknownExtensions() {
		assertEquals("application/octet-stream", ContentTypeGuesser.guessFrom(".xhtml"));
		assertEquals("application/octet-stream", ContentTypeGuesser.guessFrom(".js"));
		assertEquals("application/octet-stream", ContentTypeGuesser.guessFrom(".json"));

	}
}
