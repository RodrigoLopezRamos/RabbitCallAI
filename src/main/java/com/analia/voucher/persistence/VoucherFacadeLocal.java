package com.analia.voucher.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Voucher;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface VoucherFacadeLocal extends PersistenceFacade<Voucher> {
    /**
     * @param categoryId
     * @param categoryTypeId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(BigInteger categoryTypeId, BigInteger categoryId, BigInteger zoneId) throws AnaliaException;

    /**
     * @param voucherTypeId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, BigInteger zoneId) throws AnaliaException;

    /**
     * @param vendorLocationId
     * @return
     * @throws AnaliaException
     */
    List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    VoucherResultSet getVoucherByResultSetByVoucherTypeId(BigInteger voucherTypeId) throws AnaliaException;

    /**
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    VoucherResultSet getVoucherByResultSetByVoucherId(BigInteger voucherId) throws AnaliaException;

    /**
     * @param voucherId
     * @return
     * @throws AnaliaException
     */
    List<Voucher> getVouchersBoughtInPurchaseForVoucherId(BigInteger voucherId) throws AnaliaException;

    /**
     *
     */
    int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId, BigInteger cityId);

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
    Voucher getVoucherByExternalId(BigInteger externalId, BigInteger vendorId) throws AnaliaException;


    Voucher getVoucherById(BigInteger voucherId)throws AnaliaException;
}
