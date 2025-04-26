package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserDevice;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;

@ApplicationScoped
public class UserDeviceFacade extends JPAPersistenceFacade<UserDevice>  {
    private static final String QUERY_GET_USER_DEVICE = "getUserDevice";
    private static final String PARAM_DEVICE_ID = "deviceId";
    private static final String PARAM_USER_ID = "userId";

    @Inject
    private EntityManager entityManager;

    public UserDeviceFacade() {
        super(UserDevice.class);
    }





    public UserDevice getUserDevice(BigInteger deviceId, BigInteger userId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_USER_DEVICE, new JpqlParameter(PARAM_DEVICE_ID, deviceId), new JpqlParameter(PARAM_USER_ID, userId));
    }

}
