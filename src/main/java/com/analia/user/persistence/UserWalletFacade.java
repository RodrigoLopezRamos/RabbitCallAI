package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.UserWallet;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.math.BigInteger;


/**
 * Session Bean implementation class WalletFacade
 */
@ApplicationScoped
public class UserWalletFacade extends JPAPersistenceFacade<UserWallet>  {

    public static final String PARAM_USER_ID = "userId";
    public static final String QUERY_GET_USER_WALLET_FOR_USER_ID = "getUserWalletForUserId";

    @Inject
    private EntityManager entityManager;

    public UserWalletFacade() {
        super(UserWallet.class);
    }





    public UserWallet getUserWalletForUserId(BigInteger userId) throws AnaliaException {
        JpqlParameter jpqlParameterUserId = new JpqlParameter(PARAM_USER_ID, userId);
        return getPersistForNamedQuery(QUERY_GET_USER_WALLET_FOR_USER_ID, jpqlParameterUserId);
    }

}
