package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.utils.EntityService;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BidListServiceTests extends AbstractServiceTests<BidList, BidListDTO, Integer> {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListRepository bidListRepository;

    @Autowired
    private BidService bidService;

    @BeforeEach
    public void setUp() {
        getRepository().save( generateTestEntity() );
    }

    @AfterEach
    public void cleanUp() {
        getRepository().deleteAll();
    }

    @Override
    protected BidList generateTestEntity() {
        BidList bidList = new BidList();
        bidList.setAccount("TestAccount");
        bidList.setType("Type");
        bidList.setBidQuantity(10d);

        return bidList;
    }

    protected void updateTestDTO(BidListDTO dto) {
        dto.setBidQuantity( dto.getBidQuantity() + 1 );
    }

    protected Integer getEntityId(BidList entity) {
        return entity.getId();
    }

    protected Integer getDTOId(BidListDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(BidListDTO aDto, BidListDTO bDto) {
        return !aDto.getBidQuantity().equals(bDto.getBidQuantity());
    }

    @Override
    protected EntityService<BidList, BidListDTO, Integer> getService() {
        return bidService;
    }

    @Override
    protected JpaRepository<BidList, Integer> getRepository() {
        return bidListRepository;
    }

    @Test
    public void testFindAllSuccess() throws Exception {
        super.testFindAll();
    }

    @Test
    public void testFindByIdSuccess() throws Exception {
        super.testFindById();
    }

    @Test
    public void testUpdateSuccess() throws Exception {
        super.testUpdate();
    }

    @Test
    public void testUpdateFail() throws Exception {
        super.testUpdateOnNonExistantEntity();
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        super.testDelete();
    }
}
