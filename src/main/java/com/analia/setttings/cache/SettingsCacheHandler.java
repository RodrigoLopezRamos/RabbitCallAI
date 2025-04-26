package com.analia.setttings.cache;

import com.analia.cache.handler.CacheHandler;
import com.analia.common.cache.CacheKey;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Settings;
import com.analia.setttings.core.impl.SettingsCore;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Table;

import java.util.List;

@ApplicationScoped
@Named("SettingsCacheHandle")
public class SettingsCacheHandler implements CacheHandler<Settings> {
    public static final String TAG = "SettingsCacheDelegate";

    @Inject
    private SettingsCore settingsCoreLocal;


    public List<Settings> execute(CacheKey cacheKey) throws AnaliaException {
        return settingsCoreLocal.getAllSettingsFromDatabase();
    }

}
