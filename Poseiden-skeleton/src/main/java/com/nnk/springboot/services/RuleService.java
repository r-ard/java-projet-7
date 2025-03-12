package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.utils.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RuleService extends EntityService<RuleName, RuleNameDTO, Integer> {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    protected JpaRepository<RuleName, Integer> getRepository() {
        return this.ruleNameRepository;
    }

    @Override
    protected RuleNameDTO mapEntity(RuleName ruleName) {
        RuleNameDTO dto = new RuleNameDTO();

        dto.setId(ruleName.getId());
        dto.setName(ruleName.getName());
        dto.setDescription(ruleName.getDescription());
        dto.setJson(ruleName.getJson());
        dto.setTemplate(ruleName.getTemplate());
        dto.setSqlPart(ruleName.getSqlPart());
        dto.setSql(ruleName.getSqlStr());

        return dto;
    }

    @Override
    protected RuleName mapFromDTO(RuleNameDTO ruleNameDTO) {
        RuleName ruleName = new RuleName();

        ruleName.setId(ruleNameDTO.getId());
        ruleName.setName(ruleNameDTO.getName());
        ruleName.setDescription(ruleNameDTO.getDescription());
        ruleName.setJson(ruleNameDTO.getJson());
        ruleName.setTemplate(ruleNameDTO.getTemplate());
        ruleName.setSqlPart(ruleNameDTO.getSqlPart());
        ruleName.setSqlStr(ruleNameDTO.getSql());

        return ruleName;
    }

    @Override
    protected void setEntityId(RuleName ruleName, Integer integer) {
        ruleName.setId(integer);
    }

    @Override
    protected Integer getEntityID(RuleName ruleName) {
        return ruleName.getId();
    }

    @Override
    protected String getEntityName() {
        return RuleName.class.getName();
    }
}
