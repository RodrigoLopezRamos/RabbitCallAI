package com.analia.user.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.ShoppingCart;
import com.analia.common.model.resultset.ShoppingCartResultSet;
import com.analia.common.model.resultset.view.VoucherDetailView;
import com.analia.common.model.resultset.view.VoucherVendorTaxGroupView;
import com.analia.common.persistence.JPAPersistenceFacade;
import com.analia.common.persistence.JpqlParameter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ShoppingCartFacade extends JPAPersistenceFacade<ShoppingCart>  {

    private static final String QUERY_GET_SHOPPING_CART = "getShoppingCart";
    private static final String QUERY_GET_SHOPPING_CARTS = "getShoppingCarts";
    private static final String QUERY_DELETE_SHOPPING_CART = "deleteShoppingCart";
    private static final String QUERY_DELETE_ALL_SHOPPING_CART = "deleteAllShoppingCart";


    private static final String PARAM_VOUCHER_ID = "voucherId";
    private static final String PARAM_USER_ID = "userId";
    private static final String PARAM_ID = "id";

    @Inject
    private EntityManager entityManager;

    public ShoppingCartFacade() {
        super(ShoppingCart.class);
    }






    public ShoppingCart getShoppingCart(BigInteger voucherId, BigInteger userId) throws AnaliaException {
        List<ShoppingCart> shoppingCarts = getListForNamedQuery(QUERY_GET_SHOPPING_CART, new JpqlParameter(PARAM_VOUCHER_ID, voucherId), new JpqlParameter(PARAM_USER_ID, userId));
        if (shoppingCarts == null || shoppingCarts.isEmpty()) {
            return null;
        }
        return shoppingCarts.get(0);
    }

    /**
     *
     */

    public void deleteShoppingCart(BigInteger shoppingCartId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_DELETE_SHOPPING_CART);
        query.setParameter(PARAM_ID, shoppingCartId);
        query.executeUpdate();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")

    public List<ShoppingCartResultSet> getShoppingCarts(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_GET_SHOPPING_CARTS);
        query.setParameter(PARAM_USER_ID, userId);
        List<Object[]> shoppingCartResultSet = (List<Object[]>) query.getResultList();
        List<ShoppingCartResultSet> result = new ArrayList<>();
        for (Object[] objects : shoppingCartResultSet) {
            ShoppingCartResultSet cartResultSet = new ShoppingCartResultSet();
            cartResultSet.setQuantity((BigInteger) objects[0]);
            cartResultSet.setVoucherDetailView((VoucherDetailView) objects[1]);
            cartResultSet.setVoucherVendorTaxGroupView((VoucherVendorTaxGroupView) objects[2]);
            result.add(cartResultSet);
        }
        return result;
    }

    /**
     *
     */

    public void deleteAllShoppingCart(BigInteger userId) throws AnaliaException {
        Query query = entityManager.createNamedQuery(QUERY_DELETE_ALL_SHOPPING_CART);
        query.setParameter(PARAM_USER_ID, userId);
        query.executeUpdate();
    }

}
