package com.nnk.springboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RatingDTO {
    private Integer id;

    @NotEmpty(message = "Moodys rating is mandatory")
    private String moodysRating;

    @NotEmpty(message = "SandP rating is mandatory")
    private String sandPRating;

    @NotEmpty(message = "Fitch rating is mandatory")
    private String fitchRating;

    @NotNull(message = "Order is mandatory")
    @Min(value = 1, message = "Order must not be null")
    private Integer order;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}
