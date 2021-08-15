package ru.job4j.concurrent.cas_change;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public boolean update(Base model) {
        Base stored = memory.get(model.getId());
        Base result = memory.computeIfPresent(model.getId(), (k, v) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            Base change = new Base(model.getId(), v.getVersion() + 1);
            change.setName(model.getName());
            return change;
        });
        return result != null;
    }

    public Base get(int id) {
        return memory.get(id);
    }
}
