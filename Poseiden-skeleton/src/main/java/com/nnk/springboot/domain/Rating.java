package com.nnk.springboot.domain;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
public class Rating {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Integer id;

    String moodysRating;
    String sandPRating;
    String fitchRating;
    Integer orderNumber;

    public Rating() {

    }

    public Rating(
            String moodysRating,
            String sandPRating,
            String fitchRating,
            Integer orderNumber
    ) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

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

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
