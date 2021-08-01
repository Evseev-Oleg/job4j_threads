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
        if (chekId(user.getId())) {
            return false;
        } else {
            userStorage.put(user.getId(), user.getAmount());
            return true;
        }
    }

    public synchronized boolean update(User user) {
        if (chekId(user.getId())) {
            int amount = userStorage.get(user.getId());
            userStorage.replace(user.getId(), amount + user.getAmount());
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        if (chekId(user.getId())) {
            userStorage.remove(user.getId());
            return true;
        }
        return false;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        if (chekId(fromId) && (chekId(toId))) {
            int manyFrom = userStorage.get(fromId);
            int manyTo = userStorage.get(toId);
            userStorage.replace(toId , manyTo + amount);
            userStorage.replace(fromId, manyFrom - amount);
            return true;
        }
        return false;
    }

    private synchronized boolean chekId(int id) {
        return userStorage.containsKey(id);
    }

}

