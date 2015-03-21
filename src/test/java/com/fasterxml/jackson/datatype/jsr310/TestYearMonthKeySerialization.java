package com.fasterxml.jackson.datatype.jsr310;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestYearMonthKeySerialization {

    private ObjectMapper om;
    private Map<YearMonth, String> map;

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
    public void testSerialization() {
        // TODO test
        Assert.fail("Not done yet");
    }

    @Test
    public void testDeserialization() {
        // TODO test
        Assert.fail("Not done yet");
    }

    private String map(String key, String value) {
        return String.format("{\"%s\":\"%s\"}", key, value);
    }

}
