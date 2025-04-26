package com.analia.voucher.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.*;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface VoucherServiceLocal {
    /**
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, int page, int sortType, BigInteger categoryId, Integer pageSize, List<Filter> filters) throws AnaliaException;

    /**
     * @param categoryTypeId
     * @param categoryId
     * @return
     */
    int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Voucher getVoucherByVoucherVendorLocationId(BigInteger voucherVendorLocationId) throws AnaliaException;

    /**
     * @param voucherVendorLocationId
     * @return
     * @throws AnaliaException
     */
    VoucherDetailView getVoucherDetailViewByVoucherVendorLocationId(BigInteger voucherVendorLocationId) throws AnaliaException;

    /**
     *
     */
    VoucherVendor getVoucherVendorByVendorId(BigInteger voucherVendorId) throws AnaliaException;

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
     * @param vendorLocationId
     * @param voucherTypeId
     * @param categoryId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId, int page, Integer pageSize, Integer sortType) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<VoucherType> getVoucherTypes() throws AnaliaException;


    /**
     * @return
     * @throws AnaliaException
     */
    List<VoucherDetailView> getVouchersByVendor() throws AnaliaException;

    /**
     * @param voucherTypeId
     * @param zoneId
     * @param voucherProvider
     * @param title
     * @param shortDescription
     * @param description
     * @param website
     * @param retailValue
     * @param notBefore
     * @param notAfter
     * @param maxInventory
     * @param maximunPerUser
     * @param statusId
     * @param termsUrl
     * @param finePrint
     * @param disabled
     * @return
     */
    BigInteger saveVoucher(BigInteger vendorLocationVoucherId, BigInteger categoryId, BigDecimal price, BigInteger voucherTypeId, BigInteger zoneId, String voucherProvider, String title, String shortDescription, String description, String website,
                           BigDecimal retailValue, Date notBefore, Date notAfter, Date startDate, Date endDate, BigInteger maxInventory, BigInteger maximunPerUser, BigInteger statusId, String termsUrl, String finePrint, boolean disabled, String instructions, BigInteger taxGroupId)
            throws AnaliaException;


     BigInteger saveVoucher(BigInteger vendorLocationVoucherId, BigInteger categoryId, BigDecimal price, BigInteger voucherTypeId, BigInteger zoneId, String voucherProvider, String title, String shortDescription, String description, String website, BigDecimal retailValue, Date notBefore, Date notAfter, Date startDate, Date endDate, BigInteger maxInventory, BigInteger maximunPerUser, BigInteger statusId, String termsUrl, String finePrint,
                            boolean disabled, String instructions, BigInteger taxGroupId, BigInteger vendorId , BigInteger vendorLocationId) throws AnaliaException ;

        /**
         * @return
         * @throws AnaliaException
         */
    List<Tax> getTaxNames() throws AnaliaException;

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
    List<VoucherResultSet> getVouchers(String querySearchParam, int page, int pageSize) throws AnaliaException;

    /**
     *
     * @param externalId
     * @param vendorId
     * @return
     * @throws AnaliaException
     */
    Voucher  getVoucherByExternalId(BigInteger externalId , BigInteger vendorId)throws AnaliaException;
}
