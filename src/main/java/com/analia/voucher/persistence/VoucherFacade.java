package com.analia.voucher.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Voucher;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import com.analia.voucher.persistence.VoucherFacadeLocal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class VoucherFacade extends JPAPersistenceFacade<Voucher> implements VoucherFacadeLocal {

    public static final String QUERY_GET_VOUCHERS = "getVouchers";
    public static final String QUERY_GET_VOUCHERS_ORDER_BY_DATE = "getVouchersOrderByDate";

    public static final String QUERY_GET_VOUCHERS_BY_LOCATION_ID = "getVouchersByVendorLocationId";
    public static final String QUERY_GET_VOUCHERS_BOUGHT_IN_PURCHASE_FOR_VOUCHER_ID = "getVouchersBoughtInPurchaseForVoucherId";
    public static final String QUERY_GET_TOTAL_VOUCHERS = "totalVoucherCountByZoneId";
    public static final String QUERY_GET_VOUCHER_BY_SEARCH_PARAM = "getVoucherBySearchParam";


    public static final String PARAM_VENDOR_LOCATION_ID = "vendorLocationId";
    public static final String PARAM_VOUCHER_ID = "voucherId";
    public static final String PARAM_VOUCHER_TYPE_ID = "voucherTypeId";
    public static final String PARAM_CATEGORY_ID = "categoryId";
    public static final String PARAM_CITY_ID = "cityId";
    public static final String PARAM_SEARCH = "search";
    @Inject
    private EntityManager entityManager;

    public VoucherFacade() {
        super(Voucher.class);
    }


    @SuppressWarnings("unchecked")

    public List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, BigInteger categoryId, BigInteger cityId) throws AnaliaException {

        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHERS);
        query.setParameter(PARAM_VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(PARAM_CATEGORY_ID, categoryId);
        query.setParameter(PARAM_CITY_ID, cityId);

        List<Object[]> voucherResultSet = (List<Object[]>) query.getResultList();
        List<VoucherResultSet> result = new ArrayList<>();
        for (Object[] objects : voucherResultSet) {
            result.add(getVoucherResultSet(objects));
        }
        return result;
    }


    public List<VoucherResultSet> getVouchers(BigInteger voucherTypeId, BigInteger cityId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHERS_ORDER_BY_DATE);
        query.setParameter(PARAM_VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(PARAM_CITY_ID, cityId);

        List<Object[]> voucherResultSet = (List<Object[]>) query.getResultList();
        List<VoucherResultSet> result = new ArrayList<>();
        for (Object[] objects : voucherResultSet) {
            result.add(getVoucherResultSet(objects));
        }
        return result;
    }


    @SuppressWarnings("unchecked")

    public VoucherResultSet getVoucherByResultSetByVoucherTypeId(BigInteger voucherTypeId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHERS);
        query.setParameter(PARAM_VOUCHER_TYPE_ID, voucherTypeId);
        List<Object[]> voucherResultSet = (List<Object[]>) query.getResultList();
        return getVoucherResultSet(voucherResultSet.get(0));
    }

    /**
     * @param objects
     * @return
     */
    private VoucherResultSet getVoucherResultSet(Object[] objects) {
        VoucherResultSet voucher = new VoucherResultSet();
        voucher.setVoucherVendorLocationId((BigInteger) objects[0]);
        voucher.setZoneId((BigInteger) objects[1]);
        voucher.setDirectoryId((BigInteger) objects[2]);
        voucher.setTitle((String) objects[3]);
        voucher.setNotBefore((Date) objects[4]);
        voucher.setNotAfter((Date) objects[5]);
        voucher.setVoucherTypeId((BigInteger) objects[6]);
        voucher.setLatitude((Double) objects[7]);
        voucher.setLongitude((Double) objects[8]);
        voucher.setVoucherId((BigInteger) objects[9]);
        voucher.setCategoryName((String) objects[10]);
        voucher.setCategoryId((BigInteger) objects[11]);
        return voucher;
    }


    @SuppressWarnings("unchecked")
    public VoucherResultSet getVoucherByResultSetByVoucherId(BigInteger voucherId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHERS);
        query.setParameter(PARAM_VOUCHER_ID, voucherId);
        List<Object[]> voucherResultSet = (List<Object[]>) query.getResultList();
        return getVoucherResultSet(voucherResultSet.get(0));
    }


    @SuppressWarnings("unchecked")
    public List<VoucherResultSet> getVouchersByVendorLocationId(BigInteger vendorLocationId, BigInteger voucherTypeId, BigInteger categoryId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHERS_BY_LOCATION_ID);
        query.setParameter(PARAM_VOUCHER_TYPE_ID, voucherTypeId);
        query.setParameter(PARAM_CATEGORY_ID, categoryId);
        query.setParameter(PARAM_VENDOR_LOCATION_ID, vendorLocationId);
        List<Object[]> voucherResultSet = (List<Object[]>) query.getResultList();
        List<VoucherResultSet> result = new ArrayList<>();
        for (Object[] objects : voucherResultSet) {
            result.add(getVoucherResultSet(objects));
        }
        return result;
    }

    /**
     *
     */

    public List<Voucher> getVouchersBoughtInPurchaseForVoucherId(BigInteger voucherId) throws AnaliaException {
        return getListForNamedQuery(QUERY_GET_VOUCHERS_BOUGHT_IN_PURCHASE_FOR_VOUCHER_ID, new JpqlParameter(PARAM_VOUCHER_ID, voucherId));
    }


    public int totalVoucherCountByZoneId(BigInteger categoryTypeId, BigInteger categoryId, BigInteger cityId) {
        Query query = entityManager.createNamedQuery(QUERY_GET_TOTAL_VOUCHERS);
        query.setParameter(PARAM_VOUCHER_TYPE_ID, categoryTypeId);
        query.setParameter(PARAM_CATEGORY_ID, categoryId);
        query.setParameter(PARAM_CITY_ID, cityId);
        long aLong = (long) query.getSingleResult();
        return (int) aLong;
    }


    @SuppressWarnings("unchecked")

    public List<VoucherResultSet> getVouchers(String querySearchParam) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_VOUCHER_BY_SEARCH_PARAM);
        query.setParameter(PARAM_SEARCH, "%" + querySearchParam + "%");
        List<Object[]> tradeResultSet = (List<Object[]>) query.getResultList();
        List<VoucherResultSet> result = new ArrayList<>();
        for (Object[] objects : tradeResultSet) {
            result.add(getVoucherResultSet(objects));
        }
        return result;
    }


    public Voucher getVoucherByExternalId(BigInteger externalId, BigInteger vendorId) throws AnaliaException {
        Query query = entityManager.createQuery("select v from Voucher v , " +
                "VoucherCategory vc ," +
                " VoucherVendor vv" +
                " where v.externalId =?1 " +
                "and  v.id =vc.voucherId  and  vc.id = vv.voucherCategoryId and   vv.vendorId =?2     ", Voucher.class);
        query.setParameter(1, externalId);
        query.setParameter(2, vendorId);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return (Voucher) result.get(0);
    }

    @Override
    public Voucher getVoucherById(BigInteger voucherId) throws AnaliaException {
        JpqlParameter jpqlParameterFilterName = new JpqlParameter("id", voucherId);
        return getPersistForNamedQuery("getVoucher", jpqlParameterFilterName);
    }
}

