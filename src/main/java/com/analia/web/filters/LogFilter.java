package com.analia.web.filters;

import com.analia.web.util.AnaliaHttpServletResponseWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.logging.LogRecord;

/**
 * @author Rodrigo Lopez
 */
public class LogFilter extends AnaliaFilter {
    private static final String ENCONDING = "UTF-8";

    private static final String LOGIN_REQUEST_SUFFIX = "loginUser.n";

    private static final String SIGNUP_REQUEST_SUFFIX = "signUpUser.s";

    private static final String CHANGEPASSWORD_REQUEST_SUFFIX = "changePassword.s";

    private static final String LOG_REQUEST_BODY_SETTING = "system.log.request.body";

    private static final String LOG_REQUEST_SETTING = "system.log.enabled";

    private static final String JSON_BODY = "application/json";

    private static final Log LOG = LogFactory.getLog(LogFilter.class);

    private static final int MAX_SIZE_FOR_LOG = 4096;

    private static final long EXPIRE_TIME = 604800000L; // ..ms = 1 week.


    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void execute(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletRequest.setCharacterEncoding(ENCONDING);
        httpServletResponse.setCharacterEncoding(ENCONDING);


        String requestBody = "Request's body logging not enabled (" + LOG_REQUEST_BODY_SETTING + ")";
        long startTime = 0;
        long endTime = 0;
        boolean logEnabled = "true".equals(System.getProperty(LOG_REQUEST_SETTING));
        try {
            if (logEnabled) {
                startTime = System.currentTimeMillis();
                if ("true".equals(System.getProperty(LOG_REQUEST_BODY_SETTING))) {
                    String uri = httpServletRequest.getRequestURI();
                    if (!uri.endsWith(LOGIN_REQUEST_SUFFIX) && !uri.endsWith(SIGNUP_REQUEST_SUFFIX) && !uri.endsWith(CHANGEPASSWORD_REQUEST_SUFFIX)) {
                        // not the login request... we can log content of the body,
                        // login needs to change FFS!
                        // we are expected to log the body content...
                        requestBody = this.getRequestBody(httpServletRequest);
                        if (requestBody == null) {
                            String contentType = httpServletRequest.getContentType();
                            requestBody = (contentType != null && contentType.startsWith(JSON_BODY) ? "too big, empty, or null." : "Skipping non-jason request body");
                            // no wrapping of request required, calling doFilter with
                            // standard request object (low overhead)
                            chain.doFilter(request, response);
                        } else {
                            chain.doFilter(new LoggedRequest(httpServletRequest, requestBody), response);
                        }
                    } else {
                        requestBody = "***Request body protected due to password exposure***";
                        // no wrapping of request required, calling doFilter with
                        // standard request object (low overhead)
                        chain.doFilter(request, response);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            setResponseErrorCode(httpServletResponse, e);
        } finally {
            if (logEnabled) {
                endTime = System.currentTimeMillis();
                LOG.info(this.getRequestMetaData(httpServletRequest) + this.getRequestHeaders(httpServletRequest) + this.getRequestParameters(httpServletRequest) + "\nBODY:\n" + requestBody + "\nRESPONSE: "
                        + new AnaliaHttpServletResponseWrapper(httpServletResponse).getResponse().getContentType() + ";\nTIME: " + (endTime - startTime) + " ms\n\n");
            }
        }

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        LOG.info("LOG FILTER IS ENABLED (" + LOG_REQUEST_SETTING + "=" + ("true".equals(System.getProperty(LOG_REQUEST_SETTING))) + ")");
    }

    // PRIVATE METHODS
    // ----------------------------------------------------------------------------------------------------------

    /**
     * Details some parameters about the requests.
     *
     * @param httpServletRequest The request received by the server.
     * @return The string with the printout of the request's meta data.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If an error occurs.
     */
    private String getRequestMetaData(HttpServletRequest httpServletRequest) throws IOException, ServletException {
        String metaData = "REQUEST:\n" + "ip: " + httpServletRequest.getRemoteAddr() + "\nuri: " + httpServletRequest.getRequestURI() + "\nmethod: " + httpServletRequest.getMethod() +
                "\nurl: " + httpServletRequest.getRequestURL() + "\nquery-string: " + httpServletRequest.getQueryString() + "\n";
        return metaData;
    }

    /**
     * Retrieves all the HTTP headers and their values and makes a string for
     * logging purposes.
     *
     * @param request the request received by the server.
     * @return The string suitable for log output of all the headers in the
     * request.
     * @throws IOException      if an I/O error occurs.
     * @throws ServletException if an error occurs.
     */
    private String getRequestHeaders(HttpServletRequest request) throws IOException, ServletException {
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder headers = new StringBuilder("HEADERS:\n");
        String headerName;
        while (headerNames.hasMoreElements()) {
            headerName = headerNames.nextElement();
            headers.append(headerName).append(": '").append(request.getHeader(headerName)).append("'");
            if (headerNames.hasMoreElements()) {
                headers.append("\n ");
            }
        }
        headers.append("\n");
        return headers.toString();
    }

    /**
     * Retrieves the parameters sent with the request.
     *
     * @param request The http servlet request received by the server
     * @return The string output of all the parameters present in the request.
     * @throws IOException      If an IO error occurs.
     * @throws ServletException If an error occurs.
     */
    private String getRequestParameters(HttpServletRequest request) throws IOException, ServletException {
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuilder parameters = new StringBuilder("PARAMETERS:\n");
        String paramName;
        while (paramNames.hasMoreElements()) {
            paramName = paramNames.nextElement();
            parameters.append(paramName).append(": '").append(request.getParameter(paramName)).append("'");
            if (paramNames.hasMoreElements()) {
                parameters.append("\n ");
            }
        }
        return parameters.toString();
    }

    /**
     * Retrieves the body of the an 'application/json' request and converts it to
     * string.
     *
     * @param httpServletRequest
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private String getRequestBody(HttpServletRequest httpServletRequest) throws IOException, ServletException {
        StringBuilder requestBody = null;
        String contentType = httpServletRequest.getContentType();
        if (contentType != null && contentType.startsWith(JSON_BODY) && httpServletRequest.getContentLength() < MAX_SIZE_FOR_LOG) {
            if (httpServletRequest.getContentLength() > 0) {
                requestBody = new StringBuilder();
                InputStream in = httpServletRequest.getInputStream();
                byte[] buffer = new byte[1024];
                int size;
                while ((size = in.read(buffer)) > 0) {
                    requestBody.append(new String(buffer, 0, size));
                }
            }
        }
        LOG.debug("REQUEST  _>>>"+requestBody);
        return (requestBody != null ? requestBody.toString() : null);
    }




    // FIXME REMOVE THIS FROM PRODDUCTION
    // PRODUCTION----------------------------------------------------------------------------------------------------------

    class LoggedRequest extends HttpServletRequestWrapper {
        private final HttpServletRequest delegate;
        private final byte[] body;

        LoggedRequest(HttpServletRequest theDelegate, String theBody) {
            super(theDelegate);
            this.delegate = theDelegate;
            this.body = theBody.getBytes();
            this.setRequest(theDelegate);
        }

        public Object getAttribute(String arg0) {
            return delegate.getAttribute(arg0);
        }

        public Enumeration<String> getAttributeNames() {
            return delegate.getAttributeNames();
        }

        public String getAuthType() {
            return delegate.getAuthType();
        }

        public String getCharacterEncoding() {
            return delegate.getCharacterEncoding();
        }

        public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
            delegate.setCharacterEncoding(arg0);
        }

        public int getContentLength() {
            return delegate.getContentLength();
        }

        public String getContentType() {
            return delegate.getContentType();
        }

        public String getContextPath() {
            return delegate.getContextPath();
        }

        public Cookie[] getCookies() {
            return delegate.getCookies();
        }

        // public DispatcherType getDispatcherType()
        // {
        // return delegate.getDispatcherType();
        // }

        public long getDateHeader(String arg0) {
            return delegate.getDateHeader(arg0);
        }

        public String getHeader(String arg0) {
            return delegate.getHeader(arg0);
        }

        public Enumeration<String> getHeaderNames() {
            return delegate.getHeaderNames();
        }

        public Enumeration<String> getHeaders(String arg0) {
            return delegate.getHeaders(arg0);
        }

        public ServletInputStream getInputStream() throws IOException {
            return new ServletInput(new ByteArrayInputStream(this.body));
        }

        public int getIntHeader(String arg0) {
            return delegate.getIntHeader(arg0);
        }

        public String getLocalAddr() {
            return delegate.getLocalAddr();
        }

        public String getLocalName() {
            return delegate.getLocalName();
        }

        public int getLocalPort() {
            return delegate.getLocalPort();
        }

        public Locale getLocale() {
            return delegate.getLocale();
        }

        public Enumeration<Locale> getLocales() {
            return delegate.getLocales();
        }

        public String getMethod() {
            return delegate.getMethod();
        }

        public String getParameter(String arg0) {
            return delegate.getParameter(arg0);
        }

        public Map<String, String[]> getParameterMap() {
            return delegate.getParameterMap();
        }

        public Enumeration<String> getParameterNames() {
            return delegate.getParameterNames();
        }

        public String[] getParameterValues(String arg0) {
            return delegate.getParameterValues(arg0);
        }

        public String getPathInfo() {
            return delegate.getPathInfo();
        }

        public String getPathTranslated() {
            return delegate.getPathTranslated();
        }

        public String getProtocol() {
            return delegate.getProtocol();
        }

        public String getQueryString() {
            return delegate.getQueryString();
        }

        public BufferedReader getReader() throws IOException {
            return delegate.getReader();
        }


        public String getRemoteAddr() {
            return delegate.getRemoteAddr();
        }

        public String getRemoteHost() {
            return delegate.getRemoteHost();
        }

        public int getRemotePort() {
            return delegate.getRemotePort();
        }

        public String getRemoteUser() {
            return delegate.getRemoteUser();
        }

        public RequestDispatcher getRequestDispatcher(String arg0) {
            return delegate.getRequestDispatcher(arg0);
        }

        public String getRequestURI() {
            return delegate.getRequestURI();
        }

        public StringBuffer getRequestURL() {
            return delegate.getRequestURL();
        }

        public String getRequestedSessionId() {
            return delegate.getRequestedSessionId();
        }

        public String getScheme() {
            return delegate.getScheme();
        }

        public String getServerName() {
            return delegate.getServerName();
        }

        public int getServerPort() {
            return delegate.getServerPort();
        }

        public String getServletPath() {
            return delegate.getServletPath();
        }

        public HttpSession getSession() {
            return delegate.getSession();
        }

        public HttpSession getSession(boolean arg0) {
            return delegate.getSession(arg0);
        }

        public Principal getUserPrincipal() {
            return delegate.getUserPrincipal();
        }

        public boolean isRequestedSessionIdFromCookie() {
            return delegate.isRequestedSessionIdFromCookie();
        }

        public boolean isRequestedSessionIdFromURL() {
            return delegate.isRequestedSessionIdFromURL();
        }

        @SuppressWarnings("deprecation")
        public boolean isRequestedSessionIdFromUrl() {
            return delegate.isRequestedSessionIdFromURL();
        }

        public boolean isRequestedSessionIdValid() {
            return delegate.isRequestedSessionIdValid();
        }

        public boolean isSecure() {
            return delegate.isSecure();
        }

        public boolean isUserInRole(String arg0) {
            return delegate.isUserInRole(arg0);
        }

        public void removeAttribute(String arg0) {
            delegate.removeAttribute(arg0);
        }

        public void setAttribute(String arg0, Object arg1) {
            delegate.setAttribute(arg0, arg1);
        }

    }

    class ServletInput extends ServletInputStream {
        private final ByteArrayInputStream body;

        ServletInput(ByteArrayInputStream theBody) {
            this.body = theBody;
        }

    
        public int read() throws IOException {
            return this.body.read();
        }

    
        public boolean isFinished() {
            // TODO Auto-generated method stub
            return false;
        }

    
        public boolean isReady() {
            // TODO Auto-generated method stub
            return false;
        }

    
        public void setReadListener(ReadListener readListener) {
            // TODO Auto-generated method stub

        }
    }
}
