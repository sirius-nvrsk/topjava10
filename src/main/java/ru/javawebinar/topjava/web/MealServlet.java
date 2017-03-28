package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import ru.javawebinar.topjava.util.MealsUtil;
import static org.slf4j.LoggerFactory.getLogger;


/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        List<MealWithExceed> exceedMeals = MealsUtil.getFilteredWithExceededByCycle(MealsUtil.initializeMeals(), LocalTime.MIN, LocalTime.MAX, 2000);

        request.setAttribute("meals", exceedMeals);
        RequestDispatcher rd = request.getRequestDispatcher("meals.jsp");
        rd.forward(request,response);
    }
}
