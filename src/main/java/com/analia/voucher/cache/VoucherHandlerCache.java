package com.analia.voucher.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.VoucherResultSet;
import com.analia.voucher.persistence.VoucherFacadeLocal;
import com.analia.voucher.persistence.VoucherFacade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
@Named("VoucherHandlerCache")
public class VoucherHandlerCache implements CacheHandler<VoucherResultSet> {

    @Inject
    private VoucherFacadeLocal voucherFacadeLocal;


    public List<VoucherResultSet> execute(CacheKey cacheKey) throws AnaliaException {
        BigInteger voucherTypeId = (BigInteger) cacheKey.get(VoucherFacade.PARAM_VOUCHER_TYPE_ID);
        BigInteger categoryId = (BigInteger) cacheKey.get(VoucherFacade.PARAM_CATEGORY_ID);
        BigInteger cityId = (BigInteger) cacheKey.get(VoucherFacade.PARAM_CITY_ID);
        List<VoucherResultSet> listVouchers = voucherFacadeLocal.getVouchers(voucherTypeId, categoryId, cityId);
        return listVouchers;
    }

}
