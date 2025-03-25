package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.utils.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling curvepoint-related operations.
 */
@Service
public class CurvePointService extends EntityService<CurvePoint, CurvePointDTO, Integer> {
    @Autowired
    private CurvePointRepository curvePointRepository;

    @Override
    protected JpaRepository<CurvePoint, Integer> getRepository() {
        return this.curvePointRepository;
    }

    @Override
    protected void setEntityId(CurvePoint curvePoint, Integer integer) {
        curvePoint.setId(integer);
    }

    @Override
    protected String getEntityName() {
        return CurvePoint.class.getName();
    }

    @Override
    protected Integer getEntityID(CurvePoint curvePoint) {
        return curvePoint.getId();
    }

    @Override
    protected CurvePointDTO mapEntity(CurvePoint curvePoint) {
        CurvePointDTO dto = new CurvePointDTO();

        dto.setId(curvePoint.getId());
        dto.setCurveId(curvePoint.getCurveId());
        dto.setTerm(curvePoint.getTerm());
        dto.setValue(curvePoint.getValue());

        return dto;
    }

    @Override
    protected CurvePoint mapFromDTO(CurvePointDTO curvePointDTO) {
        CurvePoint curvePoint = new CurvePoint();

        curvePoint.setId(curvePointDTO.getId());
        curvePoint.setCurveId(curvePointDTO.getCurveId());
        curvePoint.setTerm(curvePointDTO.getTerm());
        curvePoint.setValue(curvePointDTO.getValue());

        return curvePoint;
    }
}
