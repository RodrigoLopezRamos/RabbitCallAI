package com.analia.setttings.core;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Settings;


import java.util.List;

public interface SettingsCoreLocal {
    /**
     * @return
     * @throws AnaliaException
     */
    List<Settings> getAllSettingsFromDatabase() throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    List<Settings> getAllSettingsFromCache() throws AnaliaException;

    /**
     * @param key
     * @return
     * @throws AnaliaException
     */
    Settings getSettingByKey(String key) throws AnaliaException;

    /**
     * @param key
     * @return
     * @throws AnaliaException
     */
    String getStringValueForSettingKey(String key) throws AnaliaException;

    /**
     * @param key
     * @return
     * @throws AnaliaException
     */
    int getIntValueForSettingKey(String key) throws AnaliaException;

    /**
     * @return
     * @throws AnaliaException
     */
    Double getDoubleValueForSettingKey(String key) throws AnaliaException;


}
