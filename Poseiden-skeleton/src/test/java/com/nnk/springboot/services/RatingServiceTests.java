package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class RatingServiceTests extends AbstractServiceTests<Rating, RatingDTO, Integer> {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingService ratingService;

    @Override
    protected Rating generateTestEntity() {
        Rating rating = new Rating();
        rating.setFitchRating("1");
        rating.setSandPRating("1");
        rating.setMoodysRating("1");
        rating.setOrderNumber(1);

        return rating;
    }

    protected void updateTestDTO(RatingDTO dto) {
        dto.setFitchRating( dto.getFitchRating() + "1" );
    }

    protected Integer getEntityId(Rating entity) {
        return entity.getId();
    }

    protected Integer getDTOId(RatingDTO dto) {
        return dto.getId();
    }

    protected boolean isDTODifferent(RatingDTO aDto, RatingDTO bDto) {
        return !aDto.getFitchRating().equals(bDto.getFitchRating());
    }

    @Override
    protected RatingService getService() {
        return ratingService;
    }

    @Override
    protected RatingRepository getRepository() { return ratingRepository; }
}
