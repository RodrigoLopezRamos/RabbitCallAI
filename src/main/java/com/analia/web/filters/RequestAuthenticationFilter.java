package com.analia.web.filters;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.User;
import com.analia.common.model.UserRole;
import com.analia.common.util.Base26;
import com.analia.user.service.UserService;
import com.analia.web.util.HeadersUtil;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.util.logging.LogRecord;

public class RequestAuthenticationFilter extends AnaliaFilter {
    /**
     * Role
     */
    public static final String ROLE_CODE = "role";
    /**
     *
     */
    private static final Log LOG = LogFactory.getLog(RequestAuthenticationFilter.class);
    /**
     *
     */
    private static final String TOKEN = "TOKEN";
    /**
     *
     */


    @Inject
    private UserService userServiceLocal;

    /**
     * Default constructor.
     */
    public RequestAuthenticationFilter() {

    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }


    public void execute(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        User user = null;
        UserRole userRole = null;
        try {
            String token = httpServletRequest.getHeader(TOKEN);
            user = userServiceLocal.authenticateUser(token);
            userRole = userServiceLocal.getUserRole();

            AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.USER_ATTRIBUTE, user);
            httpServletResponse.setHeader(HeadersUtil.HEADER_ROLE_CODE, Base26.encode(userRole.getRoleId()));
            httpServletResponse.setHeader(ROLE_CODE, Base26.encode(userRole.getRoleId()));
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
        LOG.info("Request authentication filter is up!");
    }

}
