package com.analia.purchase.service;

import com.analia.common.context.AnaliaUserContext;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Source;
import com.analia.common.model.SourceType;
import com.analia.common.model.User;
import com.analia.common.model.UserProfile;
import com.analia.common.util.DataTypeUtil.DataType;
import com.analia.purchase.core.PurchaseCoreLocal;
import com.analia.purchase.gateway.StripeGateway;
import com.analia.setttings.core.impl.SettingsCore;
import com.analia.setttings.service.impl.SettingsService;
import com.analia.user.core.UserCore;
import com.stripe.model.Customer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigInteger;
import java.util.List;

@ApplicationScoped
public class SourceService implements SourceServiceLocal {
    @Inject
    private PurchaseCoreLocal purchaseCoreLocal;
    @Inject
    private UserCore userLogicLocal;
    @Inject
    private SettingsService settingsServiceLocal;
    @Inject
    private StripeGateway stripeGateway;

    /**
     *
     */

    public Source addSource(String sourceHolderName, String sourceNickName, String sourceProcessorToken, String phoneNumber) throws AnaliaException {
        com.stripe.model.Card defaultCard = null;
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        boolean defaultSource = false;
        try {
            user.getPersona().setPhoneNumber(phoneNumber);
            userLogicLocal.save(user);
            String stripeKey = settingsServiceLocal.getStringValueForSettingKey(SettingsCore.SYSTEM_STRIPE_KEY);
            UserProfile stripeCustomerAccount = userLogicLocal.getValueOnUserProfileByKeyAndUserId(UserCore.STRIPE_CLIENT_ID_PROFILE_KEY, user.getId());
            String customerStripeId = stripeCustomerAccount != null ? stripeCustomerAccount.getValue() : null;
            if (customerStripeId != null) {
                defaultCard = stripeGateway.createCard(stripeKey, customerStripeId, sourceProcessorToken);
            } else {
                //If customer does not have an account we  created a customer on Stripe Account.
                Customer customer = stripeGateway.createCustomer(stripeKey, sourceNickName, sourceProcessorToken);
                // Save Stripe CustomeId for future purchases.
                userLogicLocal.saveUserProfile(UserCore.STRIPE_CLIENT_ID_PROFILE_KEY, DataType.DATA_TYPE_STRING, customer.getId(), user.getId());
                defaultCard = stripeGateway.getDefaultCard(stripeKey, customer);
            }
        } catch (Exception e) {
            throw new AnaliaException(ExceptionCode.INVALID_SOURCE, e.getMessage(), e);
        }
        if (defaultCard == null) {
            throw new AnaliaException(ExceptionCode.INVALID_DEFAULT_SOURCE, "Stripe API does not provide a default source");
        }
        if (purchaseCoreLocal.getDefaultSourceByUserId(user.getId()) == null) {
            defaultSource = true;
        }
        SourceType cardType = purchaseCoreLocal.getSourceTypeByName(defaultCard.getBrand());
        Source source = purchaseCoreLocal.saveSource(BigInteger.valueOf(0), cardType.getId(), sourceHolderName, sourceNickName, defaultCard.getId(), defaultCard.getLast4(), defaultCard.getExpYear(), defaultCard.getExpMonth(), defaultSource, false, user.getId());
        return source;

    }

    /**
     *
     */

    public List<Source> getSources() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return purchaseCoreLocal.getSourceList(user.getId());
    }

    /**
     *
     */

    public boolean setDefaultSource(BigInteger sourceId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        return purchaseCoreLocal.setDefaultSource(user.getId(), sourceId);
    }

    /**
     *
     */

    public boolean deleteSource(BigInteger sourceId) throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        Source source = purchaseCoreLocal.getSource(user.getId(), sourceId);
        boolean isDefaultSource = source.isDefaultCard();
        purchaseCoreLocal.saveSource(sourceId, source.getSourcetypeId(), source.getHolderName(), source.getNickname(), source.getToken(), source.getMaskedCardNumber(), source.getExpiryYear(), source.getExpiryMonth(), false, true, user.getId());

        if (isDefaultSource) {
            List<Source> sources = purchaseCoreLocal.getSourceList(user.getId());
            if (!sources.isEmpty()) {
                Source defaultSource = sources.get(0);
                purchaseCoreLocal.saveSource(sourceId, defaultSource.getSourcetypeId(), defaultSource.getHolderName(), defaultSource.getNickname(), defaultSource.getToken(), defaultSource.getMaskedCardNumber(), defaultSource.getExpiryYear(), defaultSource.getExpiryMonth(), false, true, user.getId());
            }
        }
        return true;
    }

    /**
     *
     */

    public SourceType getSourceTypeById(BigInteger sourceTypeId) throws AnaliaException {
        return purchaseCoreLocal.getSourceTypeById(sourceTypeId);
    }

    /**
     * @return
     */
    public void disableUserSources() throws AnaliaException {
        User user = AnaliaUserContext.getContext().getValueForKey(AnaliaUserContext.USER_ATTRIBUTE, User.class);
        List<Source> sources = this.purchaseCoreLocal.getSourceList(user.getId());
        for (Source source : sources) {
            source.setDisabled(true);
            this.purchaseCoreLocal.saveSource(source);
        }
    }
}
