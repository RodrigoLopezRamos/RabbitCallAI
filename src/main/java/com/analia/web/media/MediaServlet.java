package com.analia.web.media;


import com.analia.common.context.AnaliaUserContext;
import com.analia.common.model.Directory;
import com.analia.common.model.User;
import com.analia.common.util.SettingsUtil;
import com.analia.media.service.FileSystemServiceLocal;
import jakarta.inject.Inject;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.math.BigInteger;


public class MediaServlet extends FileMediaServlet {
    private static final long serialVersionUID = 1L;
    /**
     *
     */
    private final Log log = LogFactory.getLog(getClass());
    @Inject
    private FileSystemServiceLocal fileServiceLocal;

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
        log.info("Media Servlet in enabled");
    }

    /**
     * @see Servlet#destroy()
     */
    public void destroy() {
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        execute(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            String servletPath = request.getServletPath();
            String contextPath = request.getContextPath();
            String pathOfMediaInUrl = requestUri.replace(contextPath, "").replace(servletPath, "").replaceFirst("/", "");
            String directoryString = pathOfMediaInUrl.split("/")[0];
            Directory directory = this.fileServiceLocal.getDirectory(BigInteger.valueOf(Integer.valueOf(directoryString)));
            BigInteger userId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId();
            if (directory == null || (directory.getOwnerId() != null && directory.getOwnerId().compareTo(userId) != 0)) {
                log.error("DIRECTORY " + directory + " NOT FOUND : BABOSO !" + userId);
                response.setStatus(404);
            } else {
                String path = this.computePath(request, pathOfMediaInUrl);
                this.execute(request, response, true, path, SettingsUtil.getMediaFolder());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setStatus(404);
        }
    }
}
