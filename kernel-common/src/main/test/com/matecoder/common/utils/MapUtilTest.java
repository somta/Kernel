package com.matecoder.common.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MapUtilTest {

    static class TestBean {
        public String name;
        public int age;
    }

    @Test
    void objectToMap() {
        TestBean bean = new TestBean();
        bean.name = "test";
        bean.age = 25;

        Map<String, Object> map = MapUtil.objectToMap(bean);
        assertNotNull(map);
        assertEquals("test", map.get("name"));
        assertEquals(25, map.get("age"));
    }

    @Test
    void objectToMapNullReturnsNull() {
        assertNull(MapUtil.objectToMap(null));
    }

    @Test
    void mapToObject() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "test");
        map.put("age", 25);

        TestBean bean = (TestBean) MapUtil.mapToObject(map, TestBean.class);
        assertNotNull(bean);
        assertEquals("test", bean.name);
        assertEquals(25, bean.age);
    }

    @Test
    void mapToObjectNullReturnsNull() {
        assertNull(MapUtil.mapToObject(null, TestBean.class));
    }

    @Test
    void removeNullValueRemovesNullEntries() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", null);
        map.put("key3", "");

        MapUtil.removeNullValue(map);

        assertTrue(map.containsKey("key1"));
        assertFalse(map.containsKey("key2"));
        assertFalse(map.containsKey("key3"));
    }

    @Test
    void removeNullValueRemovesEmptyCollections() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", java.util.Collections.emptyList());
        map.put("key3", java.util.Collections.emptyMap());

        MapUtil.removeNullValue(map);

        assertTrue(map.containsKey("key1"));
        assertFalse(map.containsKey("key2"));
        assertFalse(map.containsKey("key3"));
    }

    @Test
    void removeNullValueKeepsNonEmpty() {
        Map<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", java.util.List.of("a", "b"));
        map.put("key3", Map.of("k", "v"));

        MapUtil.removeNullValue(map);

        assertEquals(3, map.size());
    }
}
