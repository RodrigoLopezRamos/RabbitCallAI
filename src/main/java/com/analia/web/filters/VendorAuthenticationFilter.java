package com.analia.web.filters;

import com.analia.authorization.service.AuthorizationService;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.AccountUser;
import com.analia.common.model.ValidLocation;
import com.analia.common.util.Base26;
import com.analia.vendor.service.VendorServiceLocal;
import com.analia.web.util.HeadersUtil;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.LogRecord;


public class VendorAuthenticationFilter extends AnaliaFilter {
    @Inject
    private AuthorizationService authorizationServiceLocal;
    @Inject
    private VendorServiceLocal vendorServiceLocal;


    private final Log log = LogFactory.getLog(getClass());

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        log.info("VENDOR AUTHENTICATION  FILTER IS ENABLED");
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }


    public void execute(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        try {
            String vendorCode = httpServletRequest.getHeader(HeadersUtil.HEADER_VENDOR_CODE);
            List<ValidLocation> listValidLocations = null;
            AccountUser accountUser = vendorServiceLocal.getCurrentAccountUser(Base26.decode(vendorCode).compareTo(BigInteger.ZERO) == 0 ? null : Base26.decode(vendorCode));
            listValidLocations = vendorServiceLocal.getValidLocationsForAccountUserWithDefaultLocation(accountUser.getId());

            if (listValidLocations.size() > 1 || listValidLocations.size() == 0) {
                throw new AnaliaException(ExceptionCode.UNKNOWN_VENDOR_LOCATION, " Not valid location found or  multiple valid locations found");
            }
            ValidLocation validLocation = listValidLocations.get(0);

            AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.VENDOR_LOCATION, vendorServiceLocal.getVendorLocation(validLocation.getVendorLocationId()));
            AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.VALID_LOCATION, validLocation);
            chain.doFilter(servletRequest, servletResponse);
        } catch (AnaliaException e) {
            log.error(e.getMessage(), e);
            this.setResponseErrorCode((HttpServletResponse) servletResponse, e);
        }
    }
}
