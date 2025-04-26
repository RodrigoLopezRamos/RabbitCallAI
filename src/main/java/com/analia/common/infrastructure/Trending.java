package com.analia.common.infrastructure;

import java.math.BigInteger;

/**
 * @author Rodrigo Lopez
 */
public interface Trending {
    /**
     * @return
     */
 BigInteger getTrendingId();

    /**
     * @param liked
     */
    void setLiked(boolean liked);

    /**
     * @param favourited
     */
    void setFavourited(boolean favourited);
}
