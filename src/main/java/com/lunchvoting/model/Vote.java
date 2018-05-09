package com.lunchvoting.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date_time", nullable = false, columnDefinition = "DATE DEFAULT current_date")
    private LocalDateTime dateEntered = LocalDateTime.now();

    public Vote() {}

    public Vote(Integer id, Restaurant restaurant, User user) {
        this(id, restaurant, user, LocalDateTime.now());
    }

    public Vote(Integer id, Restaurant restaurant, User user, LocalDateTime dateEntered) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.dateEntered = dateEntered;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(LocalDateTime dateEntered) {
        this.dateEntered = dateEntered;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", dateEntered=" + dateEntered +
                '}';
    }
}
