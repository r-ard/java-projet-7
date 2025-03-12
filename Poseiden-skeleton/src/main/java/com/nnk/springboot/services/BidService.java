package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.utils.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BidService extends EntityService<BidList, BidListDTO, Integer> {
    @Autowired
    private BidListRepository bidListRepository;

    @Override
    protected JpaRepository<BidList, Integer> getRepository() {
        return this.bidListRepository;
    }

    @Override
    protected BidList mapFromDTO(BidListDTO bidListDTO) {
        BidList entity = new BidList();

        entity.setId(bidListDTO.getId());
        entity.setBidQuantity(bidListDTO.getBidQuantity());
        entity.setAccount(bidListDTO.getAccount());
        entity.setType(bidListDTO.getType());

        return entity;
    }

    @Override
    protected BidListDTO mapEntity(BidList bidList) {
        BidListDTO dto = new BidListDTO();

        dto.setId(bidList.getId());
        dto.setBidQuantity(bidList.getBidQuantity());
        dto.setAccount(bidList.getAccount());
        dto.setType(bidList.getType());

        return dto;
    }

    @Override
    protected Integer getEntityID(BidList bidList) {
        return bidList.getId();
    }

    @Override
    protected String getEntityName() {
        return BidList.class.getName();
    }

    @Override
    protected void setEntityId(BidList bidList, Integer integer) {
        bidList.setId(integer);
    }
}
