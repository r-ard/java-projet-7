package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CurvePointServiceTests extends AbstractServiceTests<CurvePoint, CurvePointDTO, Integer> {
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Autowired
    private CurvePointService curvePointService;

    @Override
    protected CurvePoint generateTestEntity() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10d);
        curvePoint.setValue(10d);

        return curvePoint;
    }

    protected void updateTestDTO(CurvePointDTO dto) {
        dto.setTerm( dto.getTerm() + 1 );
    }

    protected Integer getEntityId(CurvePoint entity) {
        return entity.getId();
    }

    protected Integer getDTOId(CurvePointDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(CurvePointDTO aDto, CurvePointDTO bDto) {
        return !aDto.getValue().equals(bDto.getValue());
    }

    @Override
    protected CurvePointService getService() {
        return curvePointService;
    }

    @Override
    protected CurvePointRepository getRepository() {
        return curvePointRepository;
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
