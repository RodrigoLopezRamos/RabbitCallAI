package com.analia.social.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.StoryType;
import com.analia.common.persistence.PersistenceFacade;

import java.util.List;

public interface StoryTypeFacadeLocal extends PersistenceFacade<StoryType> {

    List<StoryType> getStoryTypes() throws AnaliaException;

}
