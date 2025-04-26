package com.analia.voucher.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.VoucherVendor;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;

public interface VoucherVendorFacadeLocal extends PersistenceFacade<VoucherVendor> {
    /**
     * @param vendorId
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    VoucherVendor getVoucherVendor(BigInteger vendorId, BigInteger voucherId) throws AnaliaException;

}
