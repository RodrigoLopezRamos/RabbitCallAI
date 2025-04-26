package com.analia.voucher.core;

import com.analia.cache.core.CacheCore;
import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.*;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.voucher.persistence.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class VoucherCore implements VoucherCoreLocal {
    @Inject
    private CacheCore cacheCore;
    @Inject
    private VoucherFacadeLocal voucherFacadeLocal;
    @Inject
    private VoucherVendorFacadeLocal voucherVendorFacadeLocal;
    @Inject
    private VoucherVendorLocationFacadeLocal voucherVendorLocationFacadeLocal;
    @Inject
    private VoucherTypeFacadeLocal voucherTypeFacadeLocal;
    @Inject
    private VoucherCategoryFacadeLocal voucherCategoryFacadeLocal;
    @Inject
    private VoucherDetailViewFacadeLocal voucherDetailViewFacadeLocal;
    @Inject
    private TaxFacadeLocal taxFacadeLocal;


    /*
     *
     */

    public List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, BigInteger categoryId, BigInteger cityId) throws AnaliaException {
        List<VoucherResultSet> vouchers = voucherFacadeLocal.getVouchers(voucherTypeId, categoryId, cityId);
        vouchers = filterVouchersByCurrentUserDateTime(vouchers);
        return vouchers;
    }


    public List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, BigInteger zoneId) throws AnaliaException {
        List<VoucherResultSet> vouchers = voucherFacadeLocal.getVouchers(voucherTypeId, zoneId);
        vouchers = filterVouchersByCurrentUserDateTime(vouchers);
        return vouchers;
    }


    public int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId, BigInteger cityId) {
        return voucherFacadeLocal.totalVoucherCountByZoneId(categoryTypeId, categoryId, cityId);
    }

    /**
     * @param data
     * @return
     * @throws AnaliaException
     */
    private List<VoucherResultSet> filterVouchersByCurrentUserDateTime(List<VoucherResultSet> data) throws AnaliaException {
        Date currentTime = AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_DATE_TIME, Date.class);
        List<VoucherResultSet> result = new ArrayList<>();
        for (VoucherResultSet voucher : data) {
            if (currentTime.after(voucher.getNotBefore()) && currentTime.before(voucher.getNotAfter())) {
                result.add(voucher);
            }
        }
        return data;
    }


    @Transactional
    public Voucher saveVoucher(Voucher voucher) throws AnaliaException {
        if (voucher == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Voucher object must be not null . Baboso!");
        }
        if (voucher.getId() == null) {
            voucher.setSold(BigInteger.ZERO);
            voucher.setCreatedDatetime(new Date());
            voucher.setCreatedBy(AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
        }

        voucherFacadeLocal.save(voucher);
        voucherFacadeLocal.flush();
        return voucher;
    }


    @Transactional
    public VoucherCategory saveVoucherCategory(VoucherCategory voucherCategory) throws AnaliaException {
        if (voucherCategory == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "VoucherCategory object must be not null . Baboso!");
        }
        voucherCategory.setCreatedDatetime(new Date());
        voucherCategoryFacadeLocal.save(voucherCategory);
        voucherCategoryFacadeLocal.flush();
        return voucherCategory;
    }


    public Voucher getVoucherById(BigInteger voucherId) throws AnaliaException {
        Voucher voucher = voucherFacadeLocal.find(voucherId);
        return voucher;
    }


    public VendorLocationVoucher getVoucherVendorLocation(BigInteger voucherVendorLocationId) throws AnaliaException {
        return voucherVendorLocationFacadeLocal.find(voucherVendorLocationId);
    }


    public VoucherVendor getVoucherVendor(BigInteger voucherVendorId) throws AnaliaException {
        return voucherVendorFacadeLocal.find(voucherVendorId);
    }

    /**
     *
     */

    public List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId) throws AnaliaException {
        return voucherFacadeLocal.getVouchersByVendorLocationId(vendorLocationId, voucherTypeId, categoryId);
    }


    public List<VoucherType> getVoucherTypes() throws AnaliaException {
        return voucherTypeFacadeLocal.findAllJPa();
    }


    public VoucherDetailView getVoucherDetailViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException {
        return voucherDetailViewFacadeLocal.find(vendorLocationVoucherId);
    }


    public VoucherVendor getVoucherVendor(BigInteger vendorId, BigInteger voucherId) throws AnaliaException {
        return voucherVendorFacadeLocal.getVoucherVendor(vendorId, voucherId);
    }


    public VoucherCategory getVoucherCategory(BigInteger voucherCategoryId) throws AnaliaException {
        return voucherCategoryFacadeLocal.find(voucherCategoryId);
    }


    @Transactional
    public VoucherVendor saveVoucherVendor(VoucherVendor voucherVendor) throws AnaliaException {
        if (voucherVendor == null) {
            throw new AnaliaException(ExceptionCode.PERSISTENCE_EXCEPTION, "Voucher object must be not null . Baboso!");
        }
        if (voucherVendor.getId() == null) {
            voucherVendor.setCreatedDatetime(new Date());
            voucherVendor.setCreatedBy(AnaliaUserContext.getContext().getMandatoryValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class).getId());
        }
        voucherVendorFacadeLocal.save(voucherVendor);
        voucherVendorFacadeLocal.flush();
        return voucherVendor;
    }


    @Transactional
    public VendorLocationVoucher saveVendorLocationVoucher(VendorLocationVoucher vendorLocationVoucher) throws AnaliaException {
        voucherVendorLocationFacadeLocal.save(vendorLocationVoucher);
        voucherVendorLocationFacadeLocal.flush();
        return vendorLocationVoucher;
    }

    /**
     *
     */

    public List<Tax> getTaxes() throws AnaliaException {
        return taxFacadeLocal.findAllJPa();
    }

    /**
     *
     */

    public List<Voucher> getVouchersBoughtInPurchaseForVoucherId(BigInteger voucherId) throws AnaliaException {
        return voucherFacadeLocal.getVouchersBoughtInPurchaseForVoucherId(voucherId);
    }


    public List<VoucherResultSet> getVouchers(String querySearchParam) throws AnaliaException {
        return voucherFacadeLocal.getVouchers(querySearchParam);
    }

    public Voucher getVoucherByExternalId(BigInteger externalId, BigInteger vendorId) throws AnaliaException {
        return voucherFacadeLocal.getVoucherByExternalId(externalId, vendorId);
    }

}
