package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.PurchaseDetail;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface PurchaseDetailFacadeLocal extends PersistenceFacade<PurchaseDetail> {

    PurchaseDetail getPurchaseDetailForVoucher(String voucher) throws AnaliaException;

    List<PurchaseDetail> getListPurchaseDetailForVoucherId(BigInteger voucherId) throws AnaliaException;

}
