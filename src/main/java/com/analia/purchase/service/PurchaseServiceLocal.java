package com.analia.purchase.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.resultset.ShoppingCartResultSet;
import com.analia.common.model.resultset.view.PurchaseDetailView;


import java.math.BigInteger;
import java.util.List;


public interface PurchaseServiceLocal {

    /**
     * @return
     * @throws AnaliaException
     */
    List<ShoppingCartResultSet> getPurchaseSummary(boolean creditUsed) throws AnaliaException;

    /**
     * @param creditUsed
     * @return
     * @throws AnaliaException
     */
    String checkout(BigInteger sourceId, boolean creditUsed) throws AnaliaException;


    /**
     *
     * @return
     * @throws AnaliaException
     */
   List<PurchaseDetailView> getPurchaseListByUser()throws AnaliaException;
}
