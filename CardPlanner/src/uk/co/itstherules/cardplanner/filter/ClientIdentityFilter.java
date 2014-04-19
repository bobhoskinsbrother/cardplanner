package uk.co.itstherules.cardplanner.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public final class ClientIdentityFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
		Cookie[] cookies = httpRequest.getCookies();
		boolean found = false;
        if(cookies!=null) {
            for (Cookie cookie : cookies) {
                if("clientIdentity".equals(cookie.getName()) && !"".equals(cookie.getValue())) {
                    found = true;
                    break;
                }
            }
        }

		if(!found) { 
			Cookie cookie = new Cookie("clientIdentity", UUID.randomUUID().toString());
			HttpServletResponse.class.cast(response).addCookie(cookie);
		}
	      chain.doFilter(request, response);
	   }

	@Override
    public void destroy() {
    }

	@Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}