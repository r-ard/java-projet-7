package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.utils.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class TradeService extends EntityService<Trade, TradeDTO, Integer> {
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    protected JpaRepository<Trade, Integer> getRepository() {
        return this.tradeRepository;
    }

    @Override
    protected Integer getEntityID(Trade trade) {
        return trade.getId();
    }

    @Override
    protected void setEntityId(Trade trade, Integer integer) {
        trade.setId(integer);
    }

    @Override
    protected TradeDTO mapEntity(Trade trade) {
        TradeDTO dto = new TradeDTO();

        dto.setId(trade.getId());
        dto.setAccount(trade.getAccount());
        dto.setType(trade.getType());
        dto.setBuyQuantity(trade.getBuyQuantity());

        return dto;
    }

    @Override
    protected Trade mapFromDTO(TradeDTO tradeDTO) {
        Trade trade = new Trade();

        trade.setId(tradeDTO.getId());
        trade.setAccount(tradeDTO.getAccount());
        trade.setType(tradeDTO.getType());
        trade.setBuyQuantity(tradeDTO.getBuyQuantity());

        return trade;
    }

    @Override
    protected String getEntityName() {
        return Trade.class.getName();
    }
}
