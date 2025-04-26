package com.analia.web.filters;

import com.analia.authorization.service.AuthorizationService;
import com.analia.common.exception.AnaliaException;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.logging.LogRecord;

public class RequestAuthorizationFilter extends AnaliaFilter {

    private static final Log LOG = LogFactory.getLog(RequestAuthorizationFilter.class);
    /**
     *
     */
    @Inject
    private AuthorizationService authorizationServiceLocal;

    /**
     * Default constructor.
     */
    public RequestAuthorizationFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }


    public void execute(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {
            String method = httpServletRequest.getMethod();
            String endpoint = httpServletRequest.getRequestURI();
            authorizationServiceLocal.authorizeResource(method, endpoint);
            chain.doFilter(servletRequest, servletResponse);

        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
            setResponseErrorCode((HttpServletResponse) servletResponse, e);
        }

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("RequestAuthorizationFilter is up!");
    }

}
