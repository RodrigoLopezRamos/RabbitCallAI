package com.analia.setttings.service.impl;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Settings;
import com.analia.setttings.core.impl.SettingsCore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;


@ApplicationScoped
public class SettingsService {
    @Inject
    private SettingsCore settingsCoreLocal;


    public String getStringValueForSettingKey(String key) throws AnaliaException {
        return settingsCoreLocal.getStringValueForSettingKey(key);
    }

    public List<Settings> getAllSettingsFromDatabase() throws AnaliaException {
        return settingsCoreLocal.getAllSettingsFromDatabase();
    }


    public List<Settings> getAllSettingsFromCache() throws AnaliaException {
        return settingsCoreLocal.getAllSettingsFromCache();
    }


    public Settings getSettingByKey(String key) throws AnaliaException {
        return settingsCoreLocal.getSettingByKey(key);
    }


    public int getIntValueForSettingKey(String key) throws AnaliaException {
        return settingsCoreLocal.getIntValueForSettingKey(key);
    }


    public Double getDoubleValueForSettingKey(String key) throws AnaliaException {
        return settingsCoreLocal.getDoubleValueForSettingKey(key);
    }

}
