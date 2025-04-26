package com.analia.web.filters;

import com.analia.authorization.service.AuthorizationService;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Device;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.LogRecord;

@WebFilter
public class DeviceFilter extends AnaliaFilter {

    public static final String NONCE = "nonce";
    public static final String CODE = "code";
    private static final Log LOG = LogFactory.getLog(DeviceFilter.class);
    @Inject
    private AuthorizationService authorizationServiceLocal;


    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.debug("Device Filter is up!");
    }


    public void destroy() {

    }


    public void execute(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Device device = null;
        String nonce = null;
        try {
            String uuid = httpServletRequest.getHeader(DEVICE_IDENTIFIER);
            String ip = httpServletRequest.getRemoteAddr();
            nonce = httpServletRequest.getHeader(NONCE);
            if (nonce == null || nonce.trim().isEmpty()) {
                throw new AnaliaException(ExceptionCode.INVALID_NONCE, "nonce is not valid !");
            }
            if (uuid == null || uuid.trim().isEmpty()) {
                throw new AnaliaException(ExceptionCode.PARAMETER_IS_MANDATORY, "uuid is not valid !");
            }
            device = authorizationServiceLocal.validateDevice(uuid, nonce, ip);
            LOG.debug("Device is authorized");
        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
            this.setResponseErrorCode((HttpServletResponse) response, e);
        } finally {
            if (device != null) {
                nonce = device.generateNonceString();
                AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.DEVICE, device);
                httpServletResponse.setHeader(NONCE, nonce);
                chain.doFilter(request, response);
            } else {
//                try {
//                    throw new AnaliaException(ExceptionCode.INVALID_NONCE, "nonce is not valid !");
//                } catch (AnaliaException e) {
//                    LOG.error(e.getMessage(), e);
//                }
            }
        }
    }

}
