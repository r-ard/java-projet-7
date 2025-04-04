package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RuleNameServiceTests extends AbstractServiceTests<RuleName, RuleNameDTO, Integer> {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Autowired
    private RuleService ruleService;

    @Override
    protected RuleName generateTestEntity() {
        RuleName ruleName = new RuleName();
        ruleName.setName("Name");
        ruleName.setDescription("Description");
        ruleName.setTemplate("Template");
        ruleName.setJson("Json");
        ruleName.setSqlStr("SQLStr");
        ruleName.setSqlPart("SQLPart");

        return ruleName;
    }

    protected void updateTestDTO(RuleNameDTO dto) {
        dto.setName( dto.getName() + "1" );
    }

    protected Integer getEntityId(RuleName entity) {
        return entity.getId();
    }

    protected Integer getDTOId(RuleNameDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(RuleNameDTO aDto, RuleNameDTO bDto) {
        return !aDto.getName().equals(bDto.getName());
    }

    @Override
    protected RuleService getService() {
        return ruleService;
    }

    @Override
    protected RuleNameRepository getRepository() { return ruleNameRepository; }
}
