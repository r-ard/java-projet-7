package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@AutoConfigureMockMvc
class CurvePointControllerTests extends AbstractControllerTests<CurvePoint, Integer> {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Override
	protected CurvePoint generateTestEntity() {
		CurvePoint curvePoint = new CurvePoint();
		curvePoint.setTerm(10d);
		curvePoint.setValue(10d);


		return curvePoint;
	}

	@Override
	protected JpaRepository<CurvePoint, Integer> getRepository() {
		return this.curvePointRepository;
	}

	@Test
	public void testGetHomePage() throws Exception {
		mockMvc.perform(get("/curvePoint/list").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/list"));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/curvePoint/add").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		CurvePoint entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/curvePoint/update/" + id).with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"))
				.andExpect(model().attribute("curvePoint", Matchers.hasProperty("term", Matchers.equalTo(entity.getTerm()))));
	}

	@Test
	public void testPostAddSuccess() throws Exception {
		long beforeSize = curvePointRepository.count();

		mockMvc.perform(post("/curvePoint/validate").with(csrf())
						.with(getTestUser())
						.param("term", "10")
						.param("value", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/curvePoint/list"));

		assertNotEquals(beforeSize, curvePointRepository.count());
	}

	@Test
	public void testPostAddFails() throws Exception {
		mockMvc.perform(post("/curvePoint/validate").with(csrf())
						.with(getTestUser())
						.param("term", "1")
						.param("value", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/add"))
				.andExpect(model().attributeHasFieldErrors("curvePoint", "value"));
	}

	@Test
	public void testPostUpdateSuccess() throws Exception {
		CurvePoint entity = getEntity();
		Integer id = entity.getId();

		double newValue = entity.getValue() + 1d;

		mockMvc.perform(post("/curvePoint/update/" + id).with(csrf())
						.with(getTestUser())
						.param("term", "10")
						.param("value", String.valueOf(newValue)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/curvePoint/list"));

		CurvePoint updatedCurvePoint = getEntity();
		assertThat(updatedCurvePoint.getValue()).isEqualTo(newValue);
	}

	@Test
	public void testPostUpdateFails() throws Exception {
		CurvePoint entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(post("/curvePoint/update/" + id).with(csrf())
						.with(getTestUser())
						.param("term", "10")
						.param("value", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("curvePoint/update"))
				.andExpect(model().attributeHasFieldErrors("curvePoint", "value"));
	}

	@Test
	public void testGetDelete() throws Exception {
		long beforeSize = curvePointRepository.count();

		assertNotEquals(beforeSize, 0);

		CurvePoint curvePoint = getEntity();
		Integer id = curvePoint.getId();

		mockMvc.perform(get("/curvePoint/delete/" + id)
						.with(getTestUser()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/curvePoint/list"));

		assertNotEquals(beforeSize, curvePointRepository.count());
	}
}
