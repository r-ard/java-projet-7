package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class TradeServiceTests extends AbstractServiceTests<Trade, TradeDTO, Integer> {
    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeService tradeService;

    @Override
    protected Trade generateTestEntity() {
        Trade trade = new Trade();
        trade.setType("Type");
        trade.setAccount("Account");
        trade.setBuyQuantity(10d);

        return trade;
    }

    protected void updateTestDTO(TradeDTO dto) {
        dto.setAccount( dto.getAccount() + "1" );
    }

    protected Integer getEntityId(Trade entity) {
        return entity.getId();
    }

    protected Integer getDTOId(TradeDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(TradeDTO aDto, TradeDTO bDto) {
        return !aDto.getAccount().equals(bDto.getAccount());
    }

    @Override
    protected TradeService getService() {
        return tradeService;
    }

    @Override
    protected TradeRepository getRepository() { return tradeRepository; }
}
