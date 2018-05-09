package com.lunchvoting.to;

import com.lunchvoting.model.Restaurant;

public class VoteTo {

    private Integer id;

    private Restaurant restaurant;

    public VoteTo() {
    }

    public VoteTo(Integer id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurant=" + restaurant +
                '}';
    }
}
