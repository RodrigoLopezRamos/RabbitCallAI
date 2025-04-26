package com.analia.filters.service;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Filter;



public interface FilterServiceLocal {
    Filter getFilterById(int filterId) throws AnaliaException;
}
