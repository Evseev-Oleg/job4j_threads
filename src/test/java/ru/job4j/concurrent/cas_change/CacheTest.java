package ru.job4j.concurrent.cas_change;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class CacheTest {

    @Test
    public void changeTest() {
        Cache cache = new Cache();
        Base changed = new Base(1, 0);
        cache.add(changed);
        Base changed1 = new Base(1, 0);
        changed1.setName("Иван");
        cache.update(changed1);
        Base changed2 = new Base(1, 1);
        changed2.setName("Коля");
        cache.update(changed2);
        assertThat(cache.get(1).getVersion(), is(2));
        assertThat(cache.get(1).getName(), is("Коля"));
    }

    @Test
    public void deleteTest() {
        Cache cache = new Cache();
        Base changed = new Base(1, 0);
        cache.add(changed);
        assertThat(cache.get(1).getId(), is(1));
        cache.delete(changed);
        assertNull(cache.get(1));
    }
}