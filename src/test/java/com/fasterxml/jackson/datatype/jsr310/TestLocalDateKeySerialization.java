package com.fasterxml.jackson.datatype.jsr310;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class TestLocalDateKeySerialization {

    private ObjectMapper om;
    private Map<LocalDate, String> map;

    @Before
    public void setUp() {
        this.om = new ObjectMapper();
        om.registerModule(new JSR310Module());
        map = new HashMap<>();
    }

    /*
     * ObjectMapper configuration is not respected at deserialization and serialization at the moment.
     */

    @Test
    public void testSerialization() throws Exception {
        map.put(LocalDate.of(2015, 3, 14), "test");

        String value = om.writeValueAsString(map);

        assertEquals("Incorrect value", map("2015-03-14", "test"), value);
    }

    @Test
    public void testDeserialization() throws Exception {
        Map<LocalDate, String> value = om.readValue(map("2015-03-14", "test"), new TypeReference<Map<LocalDate, String>>() {
        });

        map.put(LocalDate.of(2015, 3, 14), "test");
        assertEquals("Incorrect value", map, value);
    }

    private String map(String key, String value) {
        return String.format("{\"%s\":\"%s\"}", key, value);
    }
}
