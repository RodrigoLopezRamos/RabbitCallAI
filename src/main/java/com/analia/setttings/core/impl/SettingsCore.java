package com.analia.setttings.core.impl;

import com.analia.cache.core.CacheCore;
import com.analia.cache.util.CacheConfigEnum;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.common.model.Settings;
import com.analia.setttings.persistence.impl.SettingsFacade;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.List;

@ApplicationScoped
public class SettingsCore  {

    public static final String SYSTEM_MEDIA_SERVER_URL = "system.analia.media.server.url";
    public static final String SYSTEM_STRIPE_KEY = "system.analia.stripe.key";
    public static final String SYSTEM_SUBJECT_TEMPLATE = "system.analia.subject.email";
    public static final String SYSTEM_EMAIL_TEMPLATE = "system.analia.template.email";
    public static final String SYSTEM_CONFIRMATION_EMAIL_FROM = "system.analia.confirmation.email.from";
    public static final String SYSTEM_FORGOT_PASSWORD_EMAIL_BODY = "system.analia.forgot.password.email.body";
    public static final String SYSTEM_FORGOT_PASSWORD_EMAIL_SUBJECT = "system.analia.forgot.password.email.subject";
    public static final String SYSTEM_USER_TERMS_URL = "system.analia.user.terms.url";
    public static final String SYSTEM_USER_EMAILCONFIRMATION_REDIRECT = "system.analia.user.email.confirmation.redirect";
    public static final String SYSTEM_PASSWORD_RESET_LINK = "system.analia.user.password.reset.link";
    public static final String SYSTEM_SERVER_URL = "system.analia.server.url";
    public static final String SYSTEM_CHECKIN_INTERVAL = "system.analia.cheking.interval";
    public static final String SYSTEM_CHECKIN_DISTANCE = "system.analia.cheking.distance.allowed";
    public static final String SYSTEM_SHARING_WEB_URL = "system.analia.share.web.url";
    public static final String SYSTEM_SHARING_WEB_TRADE_URL = "system.analia.share.web.trade.url";
    public static final String SYSTEM_SHARING_WEB_VOUCHER_URL = "system.analia.share.web.chatbot.url";
    public static final String SYSTEM_SHARING_WEB_STORY_URL = "system.analia.share.web.story.url";
    public static final String SYSTEM_RECEIPT_TEMPLATE = "system.analia.receipt.template";
    public static final String SYSTEM_PURCHASE_MAX_ALLOWED = "system.analia.purchase.max.allowed";
    public static final String SYSTEM_VOUCHER_TEMPLATE = "system.analia.voucher.template";
    public static final String SYSTEM_USER_REFERRAL_CODE_PREFIX = "system.analia.user.referral.code";
    public static final String SYSTEM_PROCESSING_FEE_PERCENTAGE = "system.analia.processing.fee.percentage";
    public static final String SYSTEM_VOUCHER_FEE_PERCENTAGE = "system.analia.voucher.fee.percentage";

    /**
     *
     */
    @Inject
    private CacheCore cacheCoreLocal;

    /**
     *
     */
    @Inject
    private SettingsFacade settingsFacadeLocal;


    public List<Settings> getAllSettingsFromDatabase() throws AnaliaException {
        return settingsFacadeLocal.findAllJPa();
    }

    public List<Settings> getAllSettingsByKeys(List<String> keys) throws AnaliaException {
        return settingsFacadeLocal.getSettingByKeys(keys);
    }


    public List<Settings> getAllSettingsFromCache() throws AnaliaException {
        CacheKey cacheKey = new CacheKey();
        cacheKey.put("getAllSettingsFromCache", "getAllSettingsFromCache");
        return cacheCoreLocal.getData(CacheConfigEnum.SETTINGS, cacheKey);
    }


    public Settings getSettingByKey(String key) throws AnaliaException {
        HashSet<Settings> hashSet = new HashSet<>(getAllSettingsFromCache());
        for (Settings settings : hashSet) {
            if (settings.getSettingKey().equals(key)) {
                return settings;
            }
        }
        throw new AnaliaException(ExceptionCode.SERVER_ERROR, "Key is not found . Configuration issue!");
    }


    public String getStringValueForSettingKey(String key) throws AnaliaException {
        Settings settings = getSettingByKey(key);
        return settings.getSettingValue();
    }


    public int getIntValueForSettingKey(String key) throws AnaliaException {
        Settings settings = getSettingByKey(key);
        return Integer.parseInt(settings.getSettingValue());
    }


    public Double getDoubleValueForSettingKey(String key) throws AnaliaException {
        Settings settings = getSettingByKey(key);
        Double value = Double.parseDouble(settings.getSettingValue());
        return value;
    }

}
