package uk.co.itstherules.yawf.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.co.itstherules.string.url.QueryStringParser;


public final class UriModel {
	
	public enum Scheme {
		query, string;
		public String toString() {
			return name().toLowerCase();
		};
	}

	private Scheme scheme;
	private URI uri;
	private List<String> pathList;
	private Map<String, Object> map;
	private String fragment;
	private String username;
	private String password;
	private Integer port;

	public UriModel(String uriString) {
		try {
	        this.uri = new URI(uriString);
        } catch (URISyntaxException e1) {
	        throw new RuntimeException(e1);
        }
		try {
			this.scheme = Scheme.valueOf(uri.getScheme());
		} catch (Exception e) {
			this.scheme = Scheme.query;
		}
		String path = this.uri.getPath();
		String[] splitUpPath = path.split("/");
		this.pathList = Arrays.asList(Arrays.copyOfRange(splitUpPath, 1, splitUpPath.length));
		String queryString = this.uri.getRawQuery();
		queryString = (queryString==null) ? "" : queryString;
		this.map = new HashMap<String, Object>();
		this.map.putAll(new QueryStringParser().parse(queryString));
		this.username = "";
		this.password = "";

		String userInformation = this.uri.getUserInfo();
		if(userInformation != null) {
			String[] usernamePassword = userInformation.split(":");
			if(usernamePassword.length==1) {
				this.username = usernamePassword[0];
			} else if (usernamePassword.length==2) {
				this.username = usernamePassword[0];
				this.password = usernamePassword[1];
			}
		}
		this.port = uri.getPort();
		this.fragment = this.uri.getFragment();
		this.fragment = (fragment==null) ? "" : fragment;
	}

	public Scheme getScheme() {
		return scheme;
	}

	public String getHost() {
		return this.uri.getHost();
	}

	public String getPath(int index) {
		try {
			return this.pathList.get(index);
		} catch (Exception e) {
			return "";
		}
	}

	public Map<String, Object> getQueryString() {
		return this.map;
	}

	public String getFragment() {
		return fragment;
	}
	
	public String getUsername() {
	    return username;
    }
	
	public String getPassword() {
	    return password;
    }

	public Integer getPort() {
	    return port;
    }
}
