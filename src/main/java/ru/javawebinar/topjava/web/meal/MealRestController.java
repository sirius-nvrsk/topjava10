package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.List;
import java.time.LocalTime;
import java.time.LocalDate;

public class MealRestController {
    private MealService service;

    public List<MealWithExceed> getMeal() {
        service.getWithExceeded(AuthorizedUser.id(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    ;

    public List<MealWithExceed> getMeal(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        service.getFilteredWithExceeded(AuthorizedUser.id(), LocalTime startTime, LocalTime endTime, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    ;

    public Meal getMeal(int id) throws NotFoundException {
        service.get(AuthorizedUser.id(), id);
    }

    ;

    public void deleteMeal(int id) throws NotFoundException {
        service.delete(AuthorizedUser.id(), id);
    }

    ;


    public void saveMeal(Meal meal) throws NotFoundException {
        service.save(AuthorizedUser.id(), meal);
    }

    ;

    public void updateMeal(Meal meal) throws NotFoundException {
        service.update(AuthorizedUser.id(), meal)
    }

    ;
}

