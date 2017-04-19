package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries({
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meals m WHERE m.id=:id and m.user_id=:user_id"),
        @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meals m LEFT JOIN FETCH m.user WHERE m.id=:id and m.user_id=:user_id"),
        @NamedQuery(name = Meal.GET_ALL, query = "SELECT m FROM Meals m LEFT JOIN FETCH m.user WHERE m.user_id=:user_id ORDER BY m.date_time DESC"),
        @NamedQuery(name = Meal.GET_BETWEEN, query = "SELECT m FROM Meals m LEFT JOIN FETCH m.user WHERE m.user_id=:user_id AND m.date_time BETWEEN  :start_time AND :end_time ORDER BY m.date_time DESC")
})

@Entity
@Table(name = "meals")
public class Meal extends BaseEntity {
    public static final String DELETE = "Meal.delete";
    public static final String GET = "Meal.get";
    public static final String GET_ALL = "Meal.getAll";
    public static final String GET_BETWEEN = "Meal.getBetween";

    @Column(name = "date_time", columnDefinition = "timestamp default now()", nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;
    @Column(name = "calories", columnDefinition = "int", nullable = false)
    private int calories;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
