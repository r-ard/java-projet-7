package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class BidListControllerTests extends AbstractControllerTests<BidList, Integer> {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BidListRepository bidListRepository;

	@Override
	protected BidList generateTestEntity() {
		BidList bidList = new BidList();
		bidList.setAccount("TestAccount");
		bidList.setType("Type");
		bidList.setBidQuantity(10d);

		return bidList;
	}

	@Override
	protected JpaRepository<BidList, Integer> getRepository() {
		return this.bidListRepository;
	}

	@Test
	public void testGetHomePage() throws Exception {
		mockMvc.perform(get("/bidList/list").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/list"));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/bidList/add").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		BidList bidList = getEntity();
		Integer id = bidList.getId();

		mockMvc.perform(get("/bidList/update/" + id).with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity", Matchers.equalTo(bidList.getBidQuantity()))));
	}

	@Test
	public void testPostAddSuccess() throws Exception {
		long beforeSize = bidListRepository.count();

		mockMvc.perform(post("/bidList/validate").with(csrf())
						.with(getTestUser())
						.param("account", "Account")
						.param("type", "Type2")
						.param("bidQuantity", "20"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/bidList/list"));

		assertNotEquals(beforeSize, bidListRepository.count());
	}

	@Test
	public void testPostAddFails() throws Exception {
		mockMvc.perform(post("/bidList/validate").with(csrf())
						.with(getTestUser())
						.param("account", "Account")
						.param("type", "Type2")
						.param("bidQuantity", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/add"))
				.andExpect(model().attributeHasFieldErrors("bidList", "bidQuantity"));
	}

	@Test
	public void testPostUpdateSuccess() throws Exception {
		BidList bidList = getEntity();
		Integer id = bidList.getId();

		double newBidQuantity = bidList.getBidQuantity() + 1d;

		mockMvc.perform(post("/bidList/update/" + id).with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("bidQuantity", String.valueOf(newBidQuantity)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/bidList/list"));

		BidList updatedBidList = getEntity();
		assertThat(updatedBidList.getBidQuantity()).isEqualTo(newBidQuantity);
	}

	@Test
	public void testPostUpdateFails() throws Exception {
		BidList bidList = getEntity();
		Integer id = bidList.getId();

		mockMvc.perform(post("/bidList/update/" + id).with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("bidQuantity", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("bidList/update"))
				.andExpect(model().attributeHasFieldErrors("bidList", "bidQuantity"));
	}

	@Test
	public void testGetDelete() throws Exception {
		long beforeSize = bidListRepository.count();

		assertNotEquals(beforeSize, 0);

		BidList bidList = getEntity();
		Integer id = bidList.getId();

		mockMvc.perform(get("/bidList/delete/" + id)
						.with(getTestUser()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/bidList/list"));

		assertNotEquals(beforeSize, bidListRepository.count());
	}
}
