package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface PurchaseDetailViewFacadeLocal extends PersistenceFacade<PurchaseDetailView> {
    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListOfPurchaseDetailViewForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

    /**
     * @param vendorId
     * @param startDate
     * @param endDate
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListPurchaseDetailViewForVendorId(BigInteger vendorId, Date startDate, Date endDate) throws AnaliaException;


    /**
     * @param startDate
     * @param endDate
     * @return
     * @throws AnaliaException
     */
    List<PurchaseDetailView> getListPurchaseDetailView(Date startDate, Date endDate) throws AnaliaException;

    /**
     * @param vendorLocationVoucherId
     * @return
     */
    List<PurchaseDetailView> getAllPurchaseDetailsForVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

}
