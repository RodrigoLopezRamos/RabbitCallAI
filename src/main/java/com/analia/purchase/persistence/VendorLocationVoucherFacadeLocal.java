package com.analia.purchase.persistence;

import com.analia.common.model.VendorLocationVoucher;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;

public interface VendorLocationVoucherFacadeLocal extends PersistenceFacade<VendorLocationVoucher> {

    VendorLocationVoucher getVendorLocationVoucher(BigInteger voucherVendorId, BigInteger vendorLocationId);

}
