package com.analia.web.filters;


import com.analia.authorization.service.AuthorizationService;
import com.analia.common.exception.AnaliaException;
import com.analia.web.util.HeadersUtil;
import io.quarkus.vertx.http.runtime.FilterConfig;
import jakarta.inject.Inject;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.logging.LogRecord;


public class ClientFilter extends AnaliaFilter {
    private static final Log LOG = LogFactory.getLog(ClientFilter.class);

    @Inject
    private AuthorizationService authorizationServiceLocal;

    /**
     * Default constructor.
     */
    public ClientFilter() {
    }


    public void destroy() {
    }


    public void execute(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String code = httpServletRequest.getHeader(HeadersUtil.HEADER_CODE);
        try {
            authorizationServiceLocal.validateClient(code);
            chain.doFilter(servletRequest, servletResponse);
        } catch (AnaliaException e) {
            setResponseErrorCode(httpServletResponse, e);
        }
    }


    public void init(FilterConfig fConfig) throws ServletException {
        LOG.info("CLIENT FILTER IS ENABLED");
    }

}
