package com.analia.setttings.persistence.impl;

import com.analia.common.model.Settings;
import com.analia.common.model.Story;
import com.analia.common.persistence.JPAPersistenceFacade;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;

import java.util.List;

@ApplicationScoped
public class SettingsFacade extends JPAPersistenceFacade<Settings> {
    @Inject
    private EntityManager entityManager;

    public SettingsFacade() {
        super(Settings.class);
    }


    public Settings getSettingByKey(String settingKey) {
        Query query = entityManager.createQuery("select s from Settings s where s.settingKey=:settingKey");
        query.setParameter("settingKey", settingKey);
        List result = query.getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return (Settings) result.get(0);
    }

    public List<Settings> getSettingByKeys(List<String> settingKeys) {
        TypedQuery<Settings> query = entityManager.createQuery("select s from Settings s where s.settingKey IN (:settingKeys)", Settings.class);
        query.setParameter("settingKeys", settingKeys);
        List result = query.getResultList();
        return result;
    }
}
