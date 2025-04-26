package com.analia.purchase.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.SourceType;
import com.analia.common.persistence.PersistenceFacade;


import java.util.List;

public interface SourceTypeFacadeLocal extends PersistenceFacade<SourceType> {

    List<SourceType> getAllCardTypes() throws AnaliaException;

    SourceType getCardTypeByName(String name) throws AnaliaException;
}
