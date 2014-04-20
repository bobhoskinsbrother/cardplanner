package uk.co.itstherules.yawf.inbound;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class ValuesProviderFactoryTest {
	
	@Test(expected=SingleCalled.class) public void createSingleWithGet() {
		RequestValuesProviderFactory unit = new RequestValuesProviderFactory(){
			@Override SinglePartRequestValuesProvider createSingle(HttpServletRequest request, ValuesProvider delegate) {
				throw new SingleCalled();
			}
			
			@Override MultipartRequestValuesProvider createMulti(HttpServletRequest request, ValuesProvider delegate) {
				throw new MultipleCalled();
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("method", "get");
		unit.create(new FakeRequest(map), new DefaultValuesProvider());
	}
	
	@Test(expected=SingleCalled.class) public void createSingleWithPost() {
		RequestValuesProviderFactory unit = new RequestValuesProviderFactory(){
			@Override SinglePartRequestValuesProvider createSingle(HttpServletRequest request, ValuesProvider delegate) {
				throw new SingleCalled();
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("method", "post");
		map.put("contentType", "text/html");
		unit.create(new FakeRequest(map), new DefaultValuesProvider());
	}
	
	
	@Test(expected=SingleCalled.class) public void createSingleWithGetAndMultipart() {
		RequestValuesProviderFactory unit = new RequestValuesProviderFactory(){
			@Override SinglePartRequestValuesProvider createSingle(HttpServletRequest request, ValuesProvider delegate) {
				throw new SingleCalled();
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("method", "get");
		map.put("contentType", "multipart/badgers");
		unit.create(new FakeRequest(map), new DefaultValuesProvider());
	}
	
	
	@Test(expected=MultipleCalled.class) public void createMultiWithPostAndMultipart() {
		RequestValuesProviderFactory unit = new RequestValuesProviderFactory(){
			@Override MultipartRequestValuesProvider createMulti(HttpServletRequest request, ValuesProvider delegate) {
				throw new MultipleCalled();
			}
		};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("method", "post");
		map.put("contentType", "multipart/badgers");
		unit.create(new FakeRequest(map), new DefaultValuesProvider());
	}
}
