package com.analia.web.filters;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;


/**
 * @author Rodrigo Lopez Ramos
 */
public abstract class AnaliaFilter implements Filter {
    /**
     *
     */
    public static final String PAYLOAD_SIZE_SETTING = "system.payload.max.size";
    /**
     *
     */
    public static final String DEVICE_IDENTIFIER = "uuid";
    /**
     *
     */
    private final Log log = LogFactory.getLog(getClass());

    /**
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public abstract void execute(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    /**
     *
     */
    public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        boolean isContextCreated = false;
        if (!AnaliaUserContext.isContextAlive()) {
            // TODO validate size of the request !
            AnaliaUserContext.createUserContext();
            isContextCreated = true;
        }
        try {
            this.execute(request, response, chain);
        } finally {
            if (isContextCreated) {
                AnaliaUserContext.getContext().invalidate();
            }
        }
    }

    /**
     * @param httpServletResponse
     * @param analiaException
     */
    protected void setResponseErrorCode(HttpServletResponse httpServletResponse, AnaliaException analiaException) {
        try {
            log.error("ERROR :  AT  FILTER " + analiaException.getMessage(), analiaException);
            httpServletResponse.setStatus(analiaException.getExceptionCode().getErrorCode().getStatusCode());
            String sb = "{\"errorCode\": " + analiaException.getExceptionCode().getErrorCode().getStatusCode() + ", \"exceptionCode\":" + analiaException.getExceptionCode().getStatusCode() + ",\"message\": \"" + analiaException.getMessage() + "\"" + "}";
            httpServletResponse.getOutputStream().print(sb);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * @param httpServletResponse
     * @param analiaException
     */
    protected void setResponseErrorCode(HttpServletResponse httpServletResponse, Exception analiaException) {
        try {
            log.error("ERROR :  AT  FILTER " + analiaException.getMessage(), analiaException);
            httpServletResponse.setStatus(500);
            String sb = "{\"errorCode\": 500, \"exceptionCode\":500,\"message\": \"" + analiaException.getMessage() + "\"" + "}";
            httpServletResponse.getOutputStream().print(sb);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param httpServletResponse
     * @param message
     */
    protected void respondWithErrorCode(HttpServletResponse httpServletResponse, ExceptionCode exceptionCode, String message) {
        try {
            if (message == null) {
                message = exceptionCode.getMessage();
            }
            log.error("ERROR FOUND: " + message);
            httpServletResponse.setStatus(exceptionCode.getErrorCode().getStatusCode());
            String sb = "{\"errorCode\": " + exceptionCode.getErrorCode().getStatusCode() + ", \"exceptionCode\":" + exceptionCode.getStatusCode() + ",\"message\": \"" + message + "\"" + "}";
            httpServletResponse.getOutputStream().print(sb);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    protected void removeUserContext() {
        try {
            AnaliaUserContext.getContext().invalidate();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
