package com.analia.authorization.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Client;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


/**
 * Session Bean implementation class ClientFacade
 */

@ApplicationScoped
public class ClientFacade extends JPAPersistenceFacade<Client> {
    public static final String PARAMETER_PLATFORM = "platform";
    public static final String PARAMETER_PLATFORM_VERSION = "platformVersion";
    public static final String PARAMETER_APP_VERSION = "version";
    public static final String PARAMETER_DEVICE_NAME = "deviceName";
    public static final String PARAMETER_DEVICE_VERSION = "deviceVersion";
    public static final String QUERY_CHECK_IF_CLIENT_IS_ELIGIBLE = "checkIfClientIsEligible";
    private static final String CODE = "code";


    @Inject
    private EntityManager entityManager;

    /**
     * Default constructor.
     */
    public ClientFacade() {
        super(Client.class);
    }

    /**
     *
     */



    /**
     *
     */
    public Client checkIfClientIsEligible(String platform, String appVersion, String deviceName, String deviceVersion) throws AnaliaException {
        JpqlParameter jpqlParameterPlatform = new JpqlParameter(PARAMETER_PLATFORM, platform);
        JpqlParameter jpqlParameterAppVersion = new JpqlParameter(PARAMETER_APP_VERSION, appVersion);
        JpqlParameter jpqlParameterDeviceName = new JpqlParameter(PARAMETER_DEVICE_NAME, deviceName);
        // JpqlParameter jpqlParameterDeviceVersion   = new JpqlParameter(PARAMETER_DEVICE_VERSION, deviceVersion);
        return getPersistForNamedQuery(QUERY_CHECK_IF_CLIENT_IS_ELIGIBLE, jpqlParameterPlatform, jpqlParameterAppVersion, jpqlParameterDeviceName);
    }

    public Client getClientByCode(String code) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_CHECK_IF_CLIENT_IS_ELIGIBLE, new JpqlParameter(CODE,code));
    }

}
