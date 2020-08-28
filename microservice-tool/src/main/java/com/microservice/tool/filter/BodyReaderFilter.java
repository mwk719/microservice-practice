package com.microservice.tool.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 创建filer，在filter中对request对象用包装后的request替换
 */
@WebFilter(filterName = "bodyReaderFilter", urlPatterns = "/**")
public class BodyReaderFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest) {
			requestWrapper = new KoalaHttpRequestWrapper((HttpServletRequest) request);
		}
		if (requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}

	}

	@Override
	public void destroy() {
		// do nothing

	}

}