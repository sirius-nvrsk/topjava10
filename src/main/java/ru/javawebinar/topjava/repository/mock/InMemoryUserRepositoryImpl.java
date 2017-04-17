package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        if ( repository.containsKey(id) ) {
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if ( user.isNew() ) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        if ( repository.containsKey(id) )
            return repository.get(id);
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        Comparator<User> byName = (u1, u2)->u1.getName().compareTo(u2.getName());
        Comparator<User> byEmail = (u1,u2)->u1.getEmail().compareTo(u2.getEmail());
        List<User> result = repository.values().stream().sorted(byName.thenComparing(byEmail)).
                collect(Collectors.toList());
        return result;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        List<User> users = repository.values().stream().filter(u -> u.getEmail().equalsIgnoreCase(email))
                .collect(Collectors.toList());
        if ( users.size() != 0 )
            return users.get( 0 );
        return null;
    }


    public static final List<User> USERS = Arrays.asList(
            new User(1, "User1", "user1@gmail.com", "1", Role.ROLE_USER),
            new User(2, "User2", "user2@gmail.com", "2", Role.ROLE_USER),
            new User(3, "Admin", "admin@gmail.com", "3", Role.ROLE_ADMIN)
    );

    {
       USERS.forEach(this::save);
    }

}
