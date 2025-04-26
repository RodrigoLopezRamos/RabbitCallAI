package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.vendor.persistence.AccountUserViewFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class AccountUserViewFacade extends JPAPersistenceFacade<AccountUserView> implements AccountUserViewFacadeLocal {

    public static final String GET_LIST_ACCOUNT_USERS_VIEW_FOR_VENDOR_ID = "getListAccountUsersViewForVendorId";
    public static final String PARAM_VENDOR_ID = "vendorId";
    @Inject
    private EntityManager entityManager;

    public AccountUserViewFacade() {
        super(AccountUserView.class);
    }


    public List<AccountUserView> getAllAccountUsersForVendorId(BigInteger vendorId) throws AnaliaException {
        return getListForNamedQuery(GET_LIST_ACCOUNT_USERS_VIEW_FOR_VENDOR_ID, new JpqlParameter(PARAM_VENDOR_ID, vendorId));
    }
}
