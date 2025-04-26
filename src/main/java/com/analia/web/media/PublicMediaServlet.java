package com.analia.web.media;

import com.analia.common.exception.AnaliaException;
import com.analia.common.util.SettingsUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;


public class PublicMediaServlet extends FileMediaServlet {
    /**
     * Serialization Support.
     */
    private static final long serialVersionUID = 1L;

    private static final Log LOG = LogFactory.getLog(PublicMediaServlet.class);

    private static final String PUBLIC_PREFIX = "/public";

    public PublicMediaServlet() {
        super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        LOG.info("Public Media is Enabled");
    }


    public void destroy() {
    }

    /**
     * @throws IOException
     * @throws ServletException
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            execute(request, response, true, PUBLIC_PREFIX + request.getPathInfo(), SettingsUtil.getMediaFolder());
        } catch (AnaliaException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

}