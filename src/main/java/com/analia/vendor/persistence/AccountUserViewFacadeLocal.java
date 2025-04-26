package com.analia.vendor.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.AccountUserView;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface AccountUserViewFacadeLocal extends PersistenceFacade<AccountUserView> {
    /**
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    List<AccountUserView> getAllAccountUsersForVendorId(BigInteger vendorId) throws AnaliaException;

}
