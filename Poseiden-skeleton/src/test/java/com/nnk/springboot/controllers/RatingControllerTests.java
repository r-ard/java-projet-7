package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Test;
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
@Transactional
public class RatingTests extends AbstractControllerTests<Rating, Integer> {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	protected Rating generateTestEntity() {
		Rating rating = new Rating();
		rating.setFitchRating("1");
		rating.setSandPRating("1");
		rating.setMoodysRating("1");
		rating.setOrderNumber(1);

		return rating;
	}

	@Override
	protected JpaRepository<Rating, Integer> getRepository() {
		return this.ratingRepository;
	}

	@Test
	public void testGetHomePage() throws Exception {
		mockMvc.perform(get("/rating/list").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/list"));
	}

	@Test
	public void testGetAddPage() throws Exception {
		mockMvc.perform(get("/rating/add").with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/add"));
	}

	@Test
	public void testGetUpdatePage() throws Exception {
		Rating entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(get("/rating/update/" + id).with(getTestUser()))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().attribute("rating", Matchers.hasProperty("fitchRating", Matchers.equalTo(entity.getFitchRating()))));
	}

	@Test
	public void testPostAddSuccess() throws Exception {
		long beforeSize = ratingRepository.count();

		mockMvc.perform(post("/rating/validate").with(csrf())
						.with(getTestUser())
						.param("fitchRating", "1")
						.param("sandPRating", "1")
						.param("moodysRating", "1")
						.param("order", "1"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rating/list"));

		assertNotEquals(beforeSize, ratingRepository.count());
	}

	@Test
	public void testPostAddFails() throws Exception {
		mockMvc.perform(post("/rating/validate").with(csrf())
						.with(getTestUser())
						.param("fitchRating", "1")
						.param("sandPRating", "1")
						.param("moodysRating", "1")
						.param("order", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/add"))
				.andExpect(model().attributeHasFieldErrors("rating", "order"));
	}

	@Test
	public void testPostUpdateSuccess() throws Exception {
		Rating entity = getEntity();
		Integer id = entity.getId();

		int newOrder = entity.getOrderNumber() + 1;

		mockMvc.perform(post("/rating/update/" + id).with(csrf())
						.with(getTestUser())
						.param("fitchRating", "1")
						.param("sandPRating", "1")
						.param("moodysRating", "1")
						.param("order", String.valueOf(newOrder)))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rating/list"));

		Rating updatedRating = getEntity();
		assertThat(updatedRating.getOrderNumber()).isEqualTo(newOrder);
	}

	@Test
	public void testPostUpdateFails() throws Exception {
		Rating entity = getEntity();
		Integer id = entity.getId();

		mockMvc.perform(post("/rating/update/" + id).with(csrf())
						.with(getTestUser())
						.param("fitchRating", "1")
						.param("sandPRating", "1")
						.param("moodysRating", "1")
						.param("order", "0"))
				.andExpect(status().isOk())
				.andExpect(view().name("rating/update"))
				.andExpect(model().attributeHasFieldErrors("rating", "order"));
	}

	@Test
	public void testGetDelete() throws Exception {
		long beforeSize = ratingRepository.count();

		assertNotEquals(beforeSize, 0);

		Rating rating = getEntity();
		Integer id = rating.getId();

		mockMvc.perform(get("/rating/delete/" + id)
						.with(getTestUser()))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/rating/list"));

		assertNotEquals(beforeSize, ratingRepository.count());
	}
}
