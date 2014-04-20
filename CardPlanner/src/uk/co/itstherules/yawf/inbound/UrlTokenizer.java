package uk.co.itstherules.yawf.inbound;


import java.util.StringTokenizer;

public class UrlTokenizer {

	private final StringTokenizer tokenizer;

	private String next;

	public UrlTokenizer(String path) {
		tokenizer = new StringTokenizer(path, "/");
	}

	public boolean hasNext() {
		return next != null || tokenizer.hasMoreTokens();
	}

	public String nextToken() {
		if (next != null) {
			String result = next;
			next = null;
			return result;
		}
		if (!tokenizer.hasMoreTokens()) {
			throw new IllegalStateException();
		}
		return tokenizer.nextToken();
	}

	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		while (hasNext()) {
			buf.append(nextToken());
			if (hasNext()) {
				buf.append("/");
			}
		}
		return buf.toString();
	}

}
