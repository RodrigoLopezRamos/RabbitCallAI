package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Device;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;

@ApplicationScoped
public class DeviceFacade extends JPAPersistenceFacade<Device> {

    public static final String PARAM_UUID = "uuid";
    public static final String PARAM_VENDOR_ID = "vendorId";

    public static final String QUERY_GET_DEVICE_UUID = "getDeviceByUuid";


    public DeviceFacade() {
        super(Device.class);
    }


    public Device getDeviceByUuid(String uuid, BigInteger vendorId) throws AnaliaException {
        return getPersistForNamedQuery(QUERY_GET_DEVICE_UUID, new JpqlParameter(PARAM_UUID, uuid), new JpqlParameter(PARAM_VENDOR_ID,vendorId));
    }
}
