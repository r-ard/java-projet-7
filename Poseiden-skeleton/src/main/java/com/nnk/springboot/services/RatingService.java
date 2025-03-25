package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.utils.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for handling rating-related operations.
 */
@Service
public class RatingService extends EntityService<Rating, RatingDTO, Integer> {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    protected JpaRepository<Rating, Integer> getRepository() {
        return this.ratingRepository;
    }

    @Override
    protected RatingDTO mapEntity(Rating rating) {
        RatingDTO dto = new RatingDTO();

        dto.setId(rating.getId());
        dto.setFitchRating(rating.getFitchRating());
        dto.setMoodysRating(rating.getMoodysRating());
        dto.setOrder(rating.getOrderNumber());
        dto.setSandPRating(rating.getSandPRating());

        return dto;
    }

    @Override
    protected Rating mapFromDTO(RatingDTO ratingDTO) {
        Rating rating = new Rating();

        rating.setId(ratingDTO.getId());
        rating.setFitchRating(ratingDTO.getFitchRating());
        rating.setMoodysRating(ratingDTO.getMoodysRating());
        rating.setOrderNumber(ratingDTO.getOrder());
        rating.setSandPRating(ratingDTO.getSandPRating());

        return rating;
    }

    @Override
    protected Integer getEntityID(Rating rating) {
        return rating.getId();
    }

    @Override
    protected void setEntityId(Rating rating, Integer integer) {
        rating.setId(integer);
    }

    @Override
    protected String getEntityName() {
        return Rating.class.getName();
    }
}
