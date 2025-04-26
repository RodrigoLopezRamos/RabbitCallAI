package com.analia.trade.persistence;

import com.analia.common.exception.AnaliaException;
import com.analia.common.model.TradeAdditionalData;
import com.analia.common.persistence.PersistenceFacade;


import java.math.BigInteger;
import java.util.List;

public interface TradeAdditionalDataFacadeLocal extends PersistenceFacade<TradeAdditionalData> {
    /**
     * @param tradeId
     * @return
     * @throws AnaliaException
     */
    List<TradeAdditionalData> geTradeAdditionalDataByTradeId(BigInteger tradeId) throws AnaliaException;

}
