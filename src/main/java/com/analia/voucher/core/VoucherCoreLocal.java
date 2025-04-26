package com.analia.voucher.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;


import java.math.BigInteger;
import java.util.List;

public interface VoucherCoreLocal {


    /**
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(BigInteger categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException;

    /**
     * @param categoryTypeId
     * @param zoneId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(BigInteger categoryTypeId, BigInteger zoneId) throws AnaliaException;


    /**
     * @param categoryTypeId
     * @param categoryId
     * @param cityId
     * @return
     */
    int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId, BigInteger cityId);


    /**
     * @param vendorLocationId
     * @param voucherTypeId
     * @param categoryId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Voucher getVoucherById(BigInteger voucherId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Voucher saveVoucher(Voucher voucher) throws AnaliaException;

    /**
     * @param voucherCategory
     * @return
     * @throws AnaliaException
     */
    VoucherCategory saveVoucherCategory(VoucherCategory voucherCategory) throws AnaliaException;

    /**
     * @param voucherVendorLocationId
     * @return
     * @throws AnaliaException
     */
    VendorLocationVoucher getVoucherVendorLocation(BigInteger voucherVendorLocationId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    VoucherVendor getVoucherVendor(BigInteger voucherVendorId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<VoucherType> getVoucherTypes() throws AnaliaException;


    /**
     * @param vendorLocationVoucherId
     * @return
     * @throws AnaliaException
     */
    VoucherDetailView getVoucherDetailViewByVendorLocationVoucherId(BigInteger vendorLocationVoucherId) throws AnaliaException;

    /**
     * @param vendorId
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    VoucherVendor getVoucherVendor(BigInteger vendorId, BigInteger voucherId) throws AnaliaException;


    /**
     * @param voucherCategoryId
     * @return
     * @throws AnaliaException
     */
    VoucherCategory getVoucherCategory(BigInteger voucherCategoryId) throws AnaliaException;

    /**
     * @param voucherVendor
     * @return
     * @throws AnaliaException
     */
    VoucherVendor saveVoucherVendor(VoucherVendor voucherVendor) throws AnaliaException;

    /**
     * @param vendorLocationVoucher
     * @return
     * @throws AnaliaException
     */
    VendorLocationVoucher saveVendorLocationVoucher(VendorLocationVoucher vendorLocationVoucher) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<Tax> getTaxes() throws AnaliaException;

    /**
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    List<Voucher> getVouchersBoughtInPurchaseForVoucherId(BigInteger voucherId) throws AnaliaException;


    /**
     * @param querySearchParam
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(String querySearchParam) throws AnaliaException;

    /**
     *
     * @param externalId
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    Voucher getVoucherByExternalId(BigInteger externalId, BigInteger vendorId)throws AnaliaException;

}
