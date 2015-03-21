package com.fasterxml.jackson.datatype.jsr310;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class TestDurationKeySerialization {

    private ObjectMapper om;
    private Map<Duration, String> map;

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
        map.put(Duration.ofMinutes(13).plusSeconds(37).plusNanos(123), "test");

        String value = om.writeValueAsString(map);

        assertNotNull("Value should not be null", value);
        assertEquals("Value is not correct", map("PT13M37.000000123S", "test"), value);
    }

    @Test
    public void testDeserialization() throws Exception {
        Map<Duration, String> value = om.readValue(
                map("PT13M37.000000123S", "test"),
                new TypeReference<Map<Duration, String>>() {
                });

        assertNotNull("Value should not be null", value);
        map.put(Duration.ofMinutes(13).plusSeconds(37).plusNanos(123), "test");
        assertEquals("Value is not correct", map, value);
    }

    private String map(String key, String value) {
        return String.format("{\"%s\":\"%s\"}", key, value);
    }

}
