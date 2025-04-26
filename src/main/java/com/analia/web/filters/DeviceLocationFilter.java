package com.analia.web.filters;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.user.service.UserService;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.LogRecord;

/**
 * @author Rodrigo Filters to determine the location for a device
 */
public class DeviceLocationFilter extends AnaliaFilter {
    /**
     *
     */
    private static final Log LOG = LogFactory.getLog(DeviceLocationFilter.class);

    /**
     *
     */
    private static final String LATITUDE = "LATITUDE";

    /**
     *
     */
    private static final String LONGITUDE = "LONGITUDE";

    /**
     *
     */
    @Inject
    private UserService userServiceLocal;


    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("Device Location filter is up !");

    }


    public void destroy() {
    }


    public void execute(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try {
            String sblatitude = httpServletRequest.getHeader(LATITUDE);
            String sblonString = httpServletRequest.getHeader(LONGITUDE);
            if (sblatitude == null || sblonString == null) {
                this.respondWithErrorCode(httpServletResponse, ExceptionCode.BAD_REQUEST, "Geo coordinates are not present on the request!");
                return;
            }
            userServiceLocal.resolveDeviceLocationAndTimeZone(Double.parseDouble(sblatitude), Double.parseDouble(sblonString));
            chain.doFilter(request, response);

        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
            setResponseErrorCode(httpServletResponse, e);
        }

    }

}
