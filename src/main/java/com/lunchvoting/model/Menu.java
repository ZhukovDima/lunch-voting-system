package com.lunchvoting.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name="date_entered", columnDefinition = "DATE DEFAULT current_date")
    @NotNull
    private LocalDate dateEntered = LocalDate.now();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "menu_id")
    @OrderBy
    private Set<MenuItem> items;

    public Menu() {
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate dateEntered, MenuItem... items) {
        this(id, restaurant, dateEntered, new LinkedHashSet<>(Arrays.asList(items)));
    }

    public Menu(Integer id, Restaurant restaurant, LocalDate dateEntered, Set<MenuItem> items) {
        super(id);
        this.restaurant = restaurant;
        this.dateEntered = dateEntered;
        this.items = items;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getDateEntered() {
        return dateEntered;
    }

    public void setDateEntered(LocalDate dateEntered) {
        this.dateEntered = dateEntered;
    }

    public Set<MenuItem> getItems() {
        return items;
    }

    public void setItems(Set<MenuItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "restaurant=" + restaurant +
                ", dateEntered=" + dateEntered +
                ", items=" + items +
                ", id=" + id +
                '}';
    }
}
