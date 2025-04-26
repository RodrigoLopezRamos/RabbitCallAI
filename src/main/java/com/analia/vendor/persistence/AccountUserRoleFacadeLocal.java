package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.AccountUser;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;

public interface AccountUserRoleFacadeLocal extends PersistenceFacade<AccountUser> {
    /**
     * @param userId
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    AccountUser getAccountUserRoleForUserAndVendorId(BigInteger userId, BigInteger vendorId) throws AnaliaException;

    /**
     * @param currentUserId
     * @param createdBy
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    AccountUser getAccountUserByVendorIdAndCreatedByUserId(BigInteger currentUserId, BigInteger createdBy, BigInteger vendorId) throws AnaliaException;

}
