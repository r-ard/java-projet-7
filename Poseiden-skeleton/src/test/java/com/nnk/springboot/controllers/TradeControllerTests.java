package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.repositories.TradeRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TradeControllerTests extends AbstractControllerTests<Trade, Integer> {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TradeRepository tradeRepository;

	@Override
	protected Trade generateTestEntity() {
		Trade trade = new Trade();
		trade.setType("Type");
		trade.setAccount("Account");
		trade.setBuyQuantity(10d);

		return trade;
	}

	@Override
	protected JpaRepository<Trade, Integer> getRepository() {
		return this.tradeRepository;
	}

	@Test
	public void testGetHomePage() throws Exception {
		mockMvc.perform(get("/trade/list").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/list"));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/trade/add").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		Trade entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/trade/update/" + id).with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"))
				.andExpect(model().attribute("rating", Matchers.hasProperty("account", Matchers.equalTo(entity.getAccount()))));
	}

	@Test
	public void testPostAddSuccess() throws Exception {
		long beforeSize = tradeRepository.count();

		mockMvc.perform(post("/trade/validate").with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("buyQuantity", "10"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/trade/list"));

		assertNotEquals(beforeSize, tradeRepository.count());
	}

	@Test
	public void testPostAddFails() throws Exception {
		mockMvc.perform(post("/trade/validate").with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("buyQuantity", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/add"))
				.andExpect(model().attributeHasFieldErrors("trade", "buyQuantity"));
	}

	@Test
	public void testPostUpdateSuccess() throws Exception {
		Trade entity = getEntity();
		Integer id = entity.getId();

		double newBuyQuantity = entity.getBuyQuantity() + 1d;

		mockMvc.perform(post("/trade/update/" + id).with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("buyQuantity", String.valueOf(newBuyQuantity)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/trade/list"));

		Trade updatedTrade = getEntity();
		assertThat(updatedTrade.getBuyQuantity()).isEqualTo(newBuyQuantity);
	}

	@Test
	public void testPostUpdateFails() throws Exception {
		Trade entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(post("/trade/update/" + id).with(csrf())
						.with(getTestUser())
						.param("account", "Account2")
						.param("type", "Type2")
						.param("buyQuantity", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("trade/update"))
				.andExpect(model().attributeHasFieldErrors("trade", "buyQuantity"));
	}

	@Test
	public void testGetDelete() throws Exception {
		long beforeSize = tradeRepository.count();

		assertNotEquals(beforeSize, 0);

		Trade entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/trade/delete/" + id)
						.with(getTestUser()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/trade/list"));

		assertNotEquals(beforeSize, tradeRepository.count());
	}
}
