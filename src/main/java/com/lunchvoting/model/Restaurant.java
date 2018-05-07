package com.lunchvoting.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy
    private Set<Menu> menus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, Menu... menus) {
        this(id, name, new LinkedHashSet<>(Arrays.asList(menus)));
    }

    public Restaurant(Integer id, String name, Set<Menu> menus) {
        super(id, name);
        this.menus = menus;
    }

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }


}
