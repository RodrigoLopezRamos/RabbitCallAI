package com.analia.web.rs.user;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Sharing;
import com.analia.common.util.Base26;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.user.service.UserActivityService;
import com.analia.web.util.RequestUtils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.io.IOException;
import java.math.BigInteger;


@Path("/sh")
@ApplicationScoped
public class UserSharingResource {
    private static final Log LOG = LogFactory.getLog(UserSharingResource.class);

    @Inject
    private UserActivityService userActivityServiceLocal;

    @Inject
    private SettingsService settingsServiceLocal;

    /**
     * @return
     */
    @Path("/{code}")
    @GET
    public void processSharing(@PathParam("code") String code, @Context HttpServletResponse httpServletResponse) {
        try {
            RequestUtils.validateNotNull("code", code);
            BigInteger sharingId = Base26.decode(code);
            Sharing sharing = this.userActivityServiceLocal.processSharing(sharingId);
            String sharingWeb = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SHARING_WEB_URL);
            switch (sharing.getEntityType().intValue()) {
                case 1:
                    sharingWeb = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SHARING_WEB_TRADE_URL);
                    break;
                case 2:
                    sharingWeb = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SHARING_WEB_VOUCHER_URL);
                    break;
                case 3:
                    sharingWeb = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_SHARING_WEB_STORY_URL);
                    break;
                default:
                    LOG.error("Sharing type is not supported " + sharing.getEntityType());
                    break;
            }
            sharingWeb = sharingWeb.replace("{code}", Integer.toString(sharing.getEntityId().intValue()));
            httpServletResponse.sendRedirect(sharingWeb);
        } catch (AnaliaException | IOException e) {
            LOG.error(e.getMessage());
            try {
                httpServletResponse.sendRedirect(settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_USER_EMAILCONFIRMATION_REDIRECT));
            } catch (AnaliaException | IOException e1) {
                LOG.error(e1.getMessage());
            }
        }
    }

}
