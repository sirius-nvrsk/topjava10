package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal save(Integer userId, Meal meal);

    void delete(Integer userId, int id) throws NotFoundException;

    Meal get(Integer userId,int id) throws NotFoundException;

    List<Meal> getAll( Integer userId );

    void update(Integer userId, Meal meal);

    public List<MealWithExceed> getWithExceeded(Integer userId, int caloriesPerDay);

    public List<MealWithExceed> getFilteredWithExceeded(Integer userId, LocalTime startTime, LocalTime endTime, int caloriesPerDay);
}