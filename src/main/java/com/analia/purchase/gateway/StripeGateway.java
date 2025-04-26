package com.analia.purchase.gateway;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.*;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class StripeGateway {
    /***
     * TESTING CODE TO CREATE TOKEN , THE TOKEN MUST BE CREATED ON  DEVICE.
     */

    public static final String STRIPE_KEY = "pk_test_XkEqeYkXhH2GnE8M1qpPMoFJ";

    public static void main(String[] args) throws StripeException {
        String apiKey = STRIPE_KEY;

        Stripe.apiKey = apiKey;
        Map<String, Object> tokenParams = new HashMap<String, Object>();
        Map<String, Object> cardParams = new HashMap<String, Object>();
        cardParams.put("number", "4242424242424242");
        cardParams.put("exp_month", 1);
        cardParams.put("exp_year", 2018);
        cardParams.put("cvc", "314");
        tokenParams.put("card", cardParams);

        Token token = Token.create(tokenParams);
        System.out.println(token);
    }

    /**
     * @param description
     * @return
     * @throws AnaliaException
     */
    public Customer createCustomer(String stripeKey, String description, String source) throws AnaliaException {
        try {
            Stripe.apiKey = stripeKey;
            Map<String, Object> customerParams = new HashMap<String, Object>();
            customerParams.put("description", description);
            customerParams.put("source", source);
            Customer customer = Customer.create(customerParams);
            return customer;
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.STRIPE_ERROR, e.getMessage(), e);
        }
    }

    /**
     * @param customerId
     * @return
     * @throws AnaliaException
     */
    public Card createCard(String stripeKey, String customerId, String source) throws AnaliaException {
        Customer cu = null;
        try {
            Stripe.apiKey = stripeKey;
            cu = Customer.retrieve(customerId);
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("source", source);
            return (Card) cu.getSources().create(params);
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.STRIPE_ERROR, e.getMessage(), e);
        }

    }

    /**
     * @param customer
     * @return
     * @throws AnaliaException
     */

    public Card getDefaultCard(String stripeKey, Customer customer) throws AnaliaException {
        Stripe.apiKey = stripeKey;
//        List<ExternalAccount> cards = customer.getSources().getData();
//        for (ExternalAccount externalAccount : cards) {
//            if (externalAccount.getId().equals(customer.getDefaultSource())) {
//                return (Card) externalAccount;
//            }
//        }
        throw new AnaliaException(ExceptionCode.PAYMENT_NOT_AUTHORIZED, "Card is not on Stripe!");
    }

    /**
     * @param customerId
     * @param amount
     * @param currencyType
     * @return
     * @throws AnaliaException
     */

    public String createStripeCharge(String stripeKey, String customerId, BigDecimal amount, String currencyType)
            throws AnaliaException {
        try {
            Stripe.apiKey = stripeKey;
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("amount", amount.multiply(new BigDecimal(100)).intValue());
            parameters.put("currency", currencyType);
            parameters.put("customer", customerId);
            parameters.put("capture", "true");

            Charge charge = Charge.create(parameters);
            if (charge.getFailureCode() != null && charge.getFailureMessage() != null) {
                throw new AnaliaException(ExceptionCode.PAYMENT_NOT_AUTHORIZED,
                        charge.getFailureCode() + " :" + charge.getFailureMessage());
            }
            return charge.getId();
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.PAYMENT_NOT_AUTHORIZED, e.getMessage(), e);
        }
    }

    /**
     * @param card
     */
    public void setStripeDefaultSource(String stripeKey, com.analia.common.model.Source card, String customerId)
            throws AnaliaException {
        try {
            Stripe.apiKey = stripeKey;
            Customer customer = Customer.retrieve(customerId);
            HashMap<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("default_source", card.getToken());
            customer = customer.update(parameters);
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.PAYMENT_NOT_AUTHORIZED, e.getMessage(), e);
        }
    }

}
