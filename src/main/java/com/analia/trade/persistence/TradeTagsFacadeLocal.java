package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Tag;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface TradeTagsFacadeLocal extends PersistenceFacade<Tag> {

    List<Tag> getTradeTagsByTradeId(BigInteger tradeId) throws AnaliaException;
}
