package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T>{

    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList() {
        this.list = new ArrayList<>();
    }

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        Objects.checkIndex(index, list.size());
        return list.get(index);
    }

    public synchronized List<T> copy(List<T> list) {
        return (List<T>) new ArrayList<>(list).clone();
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }
}
