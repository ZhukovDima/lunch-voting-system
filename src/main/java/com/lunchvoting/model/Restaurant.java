package com.lunchvoting.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    public Restaurant() {
    }

    public Restaurant(Restaurant r) {
        this(r.getId(), r.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                " name='" + name + '\'' +
                '}';
    }
}
