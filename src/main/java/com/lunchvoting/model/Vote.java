package com.lunchvoting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity {

    public enum Status {
        NEW,
        CREATED,
        UPDATED
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    @Column(name = "date_time", nullable = false, columnDefinition = "DATE DEFAULT current_date")
    private LocalDateTime dateEntered = LocalDateTime.now();

    @Transient
    private Status status = Status.NEW;

    public Vote() {}

    public Vote(Integer id, Menu menu, User user) {
        this(id, menu, user, LocalDateTime.now());
    }

    public Vote(Integer id, Menu menu, User user, LocalDateTime dateEntered) {
        super(id);
        this.menu = menu;
        this.user = user;
        this.dateEntered = dateEntered;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", dateEntered=" + dateEntered +
                '}';
    }
}
