package com.analia.purchase.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.PurchaseDetail;
import com.analia.common.model.resultset.view.PurchaseDetailView;
import com.analia.purchase.service.PurchaseVendorServiceLocal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * @author rlopez
 */
@ApplicationScoped
public class PurchaseVendorService implements PurchaseVendorServiceLocal {


    public PurchaseDetail getPurchaseDetailStatus(String voucherCode, Integer vendorLocationId) throws AnaliaException {
        // TODO Auto-generated method stub
        return null;
    }


    public boolean isVoucherRedeemableAtLocation(String voucherCode, Integer vendorLocationId) throws AnaliaException {
        // TODO Auto-generated method stub
        return false;
    }


    public boolean redeemVoucher(String voucherCode, Integer purchaseDetailId) throws AnaliaException {
        // TODO Auto-generated method stub
        return false;
    }


    public int getTotalRedeemCountForVoucherId(int voucherId) throws AnaliaException {
        // TODO Auto-generated method stub
        return 0;
    }


    public List<PurchaseDetailView> getListPurchaseDetailForVoucherId(Date startDate, Date endDate) throws AnaliaException {
        // TODO Auto-generated method stub
        return null;
    }


    public boolean setVoucherLocation(int purchaseDetailId, int vendorLocationId) throws AnaliaException {
        // TODO Auto-generated method stub
        return false;
    }

}
