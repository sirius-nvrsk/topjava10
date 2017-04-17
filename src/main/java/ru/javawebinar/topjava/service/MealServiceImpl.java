package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    public Meal save( Integer userId, Meal meal) {
        if ( meal.getUserId() == userId )
            return repository.save( meal );
        throw new NotFoundException("Not save entity");
    }

    public void delete(Integer userId, int id) throws NotFoundException {
        if ( (repository.get(id) != null) && repository.get(id).getUserId() == userId )
            ValidationUtil.checkNotFoundWithId( repository.delete(id), id );
        else throw new NotFoundException("Not delete entity");
    }

    public Meal get(Integer userId, int id) throws NotFoundException
    {
        if ( (repository.get(id) != null) && repository.get(id).getUserId() == userId )
            return checkNotFoundWithId(repository.get(id), id);
        else throw new NotFoundException("Not find entity for this user");
    }


    public List<Meal> getAll(Integer userId )
    {
        Comparator<Meal> byTime = (u1, u2)-> u1.getDateTime().compareTo(u2.getDateTime());
        List<Meal> result = repository.getAll().stream().filter( u-> u.getUserId() == userId ).sorted( byTime ).collect(Collectors.toList());
        return result;
    }

    public void update(Integer userId, Meal meal) {
        if ( meal.getUserId() == userId )
            repository.save( meal );
        throw new NotFoundException("Not save entity");
    }


    public List<MealWithExceed> getWithExceeded(Integer userId, int caloriesPerDay) {
        return this.getFilteredWithExceeded(userId, LocalTime.MIN, LocalTime.MAX, caloriesPerDay);
    }

    public List<MealWithExceed> getFilteredWithExceeded(Integer userId, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<Meal> meals = this.getAll( userId );
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> MealsUtil.createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

}