package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserCheckin;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class UserCheckinFacade extends JPAPersistenceFacade<UserCheckin>  {

    public static final String QUERY_GET_LAST_USER_CHECKIN = "getUserLastCheckinAtLocation";
    public static final String PARAM_START_DATE = "startDate";
    public static final String PARAM_END_DATE = "endDate";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_VOUCHER_VENDOR_LOCATION_ID = "voucherVendorLocationId";
    public static final String PARAM_TRADE_LOCATION_ID = "tradeLocationId";

    @Inject
    private EntityManager entityManager;


    public UserCheckinFacade() {
        super(UserCheckin.class);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     *
     */

    public UserCheckin getUserCheckinAtLocation(BigInteger userId, BigInteger tradeLocationId, BigInteger voucherVendorLocationId) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        JpqlParameter jpqlParameterVoucherVendorLocationId = new JpqlParameter(PARAM_VOUCHER_VENDOR_LOCATION_ID, voucherVendorLocationId);
        JpqlParameter jpqlParameterTradeLocationId = new JpqlParameter(PARAM_TRADE_LOCATION_ID, tradeLocationId);

        List<UserCheckin> checkins = getListForNamedQuery(QUERY_GET_LAST_USER_CHECKIN, jpqlParameterUserId, jpqlParameterVoucherVendorLocationId, jpqlParameterTradeLocationId);
        if (checkins.isEmpty()) {
            return null;
        }
        return checkins.get(0);
    }


}
