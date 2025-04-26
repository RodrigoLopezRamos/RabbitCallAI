package com.analia.common.cache;

import java.io.Serializable;

public interface AnaliaCacheableEntity<T> extends Cloneable, Serializable {
    T clone() throws CloneNotSupportedException;
}
