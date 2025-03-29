package com.nnk.springboot;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTests extends AbstractControllerTests<RuleName, Integer> {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RuleNameRepository ruleNameRepository;

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

	@Override
	protected JpaRepository<RuleName, Integer> getRepository() {
		return this.ruleNameRepository;
	}

	@Test
	public void testGetHomePage() throws Exception {
		mockMvc.perform(get("/ruleName/list").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/list"));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/ruleName/add").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/add"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		RuleName entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/ruleName/update/" + id).with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"))
				.andExpect(model().attribute("rating", Matchers.hasProperty("name", Matchers.equalTo(entity.getName()))));
	}

	@Test
	public void testPostAddSuccess() throws Exception {
		long beforeSize = ruleNameRepository.count();

		mockMvc.perform(post("/ruleName/validate").with(csrf())
						.with(getTestUser())
						.param("name", "Name2")
						.param("description", "Description2")
						.param("template", "Template2")
						.param("json", "Json2")
						.param("sql", "SQL2")
						.param("sqlPart", "SQLPart2"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/ruleName/list"));

		assertNotEquals(beforeSize, ruleNameRepository.count());
	}

	@Test
	public void testPostAddFails() throws Exception {
		mockMvc.perform(post("/ruleName/validate").with(csrf())
						.with(getTestUser())
						.param("name", "Name2")
						.param("description", "Description2")
						.param("template", "Template2")
						.param("json", "Json2")
						.param("sql", "SQL2")
						.param("sqlPart", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/add"))
				.andExpect(model().attributeHasFieldErrors("rating", "order"));
	}

	@Test
	public void testPostUpdateSuccess() throws Exception {
		RuleName entity = getEntity();
		Integer id = entity.getId();

		String newName = entity.getName() + "1";

		mockMvc.perform(post("/ruleName/update/" + id).with(csrf())
						.with(getTestUser())
						.param("name", newName)
						.param("description", "Description2")
						.param("template", "Template2")
						.param("json", "Json2")
						.param("sql", "SQL2")
						.param("sqlPart", "SQLPart2"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/ruleName/list"));

		RuleName updatedRuleName = getEntity();
		assertThat(updatedRuleName.getName()).isEqualTo(newName);
	}

	@Test
	public void testPostUpdateFails() throws Exception {
		RuleName entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(post("/ruleName/update/" + id).with(csrf())
						.with(getTestUser())
						.param("name", "Name2")
						.param("description", "Description2")
						.param("template", "Template2")
						.param("json", "Json2")
						.param("sql", "SQL2")
						.param("sqlPart", ""))
				.andExpect(status().isOk())
				.andExpect(view().name("ruleName/update"))
				.andExpect(model().attributeHasFieldErrors("rating", "order"));
	}

	@Test
	public void testGetDelete() throws Exception {
		long beforeSize = ruleNameRepository.count();

		assertNotEquals(beforeSize, 0);

		RuleName entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/ruleName/delete/" + id)
						.with(getTestUser()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/ruleName/list"));

		assertNotEquals(beforeSize, ruleNameRepository.count());
	}
}
