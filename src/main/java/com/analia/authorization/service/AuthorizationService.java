package com.analia.authorization.service;

import com.analia.authorization.core.AuthorizationCore;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Client;
import com.analia.common.model.Device;
import com.analia.common.model.Permission;
import com.analia.common.model.User;
import com.analia.common.util.Base26;
import com.analia.user.core.UserCore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.math.BigInteger;
import java.sql.Timestamp;

@ApplicationScoped
public class AuthorizationService {

    @Inject
    private AuthorizationCore authorizationCoreLocal;

    @Inject
    private UserCore userCoreLocal;

    /**
     *
     */

    public Permission authorizeResource(String method, String endpoint) throws AnaliaException {

        System.out.println("AUTHORIZATION "+ method + "ENPOINT "+endpoint);
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        BigInteger vendorId = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        Permission permission = authorizationCoreLocal.getPermission(method, endpoint, user.getId(), vendorId);
        if (permission == null) {
            throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Authorization Failed");
        }
        return permission;
    }

    /**
     *
     */

    public Device validateDevice(String uuid, String nonce, String externalIp) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        Device device = userCoreLocal.getDeviceByUUID(uuid, vendorId);
        System.out.println("UUID "+uuid);
        if (device == null) {
            throw new AnaliaException(ExceptionCode.AUTHORIZATION_FAILED, "Device is invalid !");
        }
        if ((device.getExternalIp() != null && device.getExternalIp().equals(externalIp)) || device.isValidNonce(nonce)) {
            int newNonce = (device.getNonce() + 1);
            device.setNonce(newNonce);
            userCoreLocal.saveDevice(device);
        } else {
            ///   throw new AnaliaException(ExceptionCode.INVALID_NONCE, "Nonce is invalid !");
        }
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.DEVICE, device);
        return device;
    }

    /**
     *
     */

    public Device registerDevice(String deviceUuid, String userAgent) throws AnaliaException {
        BigInteger vendorId = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.VENDOR_ID, BigInteger.class);
        Device device = userCoreLocal.getDeviceByUUID(deviceUuid, vendorId);
        if (device == null) {
            device = new Device();
        }
        device.setNonce(device.getNonce() + 1);
        device.setUuid(deviceUuid);
        device.setVendorId(vendorId);
        device.setUserAgent(userAgent);
        device.setTimestamp(new Timestamp(System.currentTimeMillis()));
        userCoreLocal.saveDevice(device);
        return device;
    }

    /**
     *
     */

    public Client validateClient(String platform, String appVersion, String deviceName, String deviceVersion) throws AnaliaException {
        return authorizationCoreLocal.validateDevice(platform, appVersion, deviceName, deviceVersion);
    }

    /**
     * @param code
     * @return
     * @throws AnaliaException
     */

    public Client validateClient(String code) throws AnaliaException {
        if (code == null || code.trim().length() == 0) {
            throw new AnaliaException(ExceptionCode.INVALID_UA);
        }

        System.out.println("CODEIS"+ code);
        Client client = authorizationCoreLocal.getClientByCode(Base26.decode(code).toString());
        if (client == null) {
            throw new AnaliaException(ExceptionCode.INVALID_UA);
        }
        AnaliaUserContext.getContext().setAttribute(AnaliaUserContext.VENDOR_ID, client.getVendorId());
        return client;
    }
}
