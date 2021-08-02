package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;


@ThreadSafe
public class UserStorage {

    @GuardedBy("this")
    private final Map<Integer, Integer> userStorage = new HashMap<>();

    public synchronized boolean add(User user) {
        Integer value = userStorage.get(user.getId());
        if (value == null) {
            userStorage.put(user.getId(), user.getAmount());
            return true;
        }
            return false;
    }

    public synchronized boolean update(User user) {
        if (userStorage.containsKey(user.getId())) {
            userStorage.put(user.getId(), user.getAmount());
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        if (userStorage.containsKey(user.getId())) {
            userStorage.remove(user.getId());
            return true;
        }
        return false;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (userStorage.containsKey(fromId) && userStorage.containsKey(toId)) {
            int manyFrom = userStorage.get(fromId);
            int manyTo = userStorage.get(toId);
            if (manyFrom >= amount) {
                userStorage.replace(toId, manyTo + amount);
                userStorage.replace(fromId, manyFrom - amount);
                return true;
            }
        }
        return false;
    }

}

