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
        return userStorage.putIfAbsent(user.getId(), user.getAmount()) == null;
    }

    public synchronized boolean update(User user) {
        return userStorage.replace(user.getId(), user.getAmount()) != null;
    }

    public synchronized boolean delete(User user) {
        return userStorage.remove(user.getId()) != null;
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

