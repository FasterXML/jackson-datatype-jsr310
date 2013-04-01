package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

import static org.junit.Assert.*;

public class TestDurationSerialization
{
    private ObjectMapper mapper;

    @Before
    public void setUp()
    {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testSerializationAsTimestamp01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "60.000000000", value);
    }

    @Test
    public void testSerializationAsTimestamp02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "13498.000008374", value);
    }

    @Test
    public void testSerializationAsString01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + duration.toString() + '"', value);
    }

    @Test
    public void testSerializationAsString02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + duration.toString() + '"', value);
    }

    @Test
    public void testSerializationWithTypeInfo01() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + Duration.class.getName() + "\",\"" + duration.toString() + "\"]", value);
    }

    @Test
    public void testDeserializationAsFloat01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        Duration value = this.mapper.readValue("60.0", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsFloat02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        Duration value = this.mapper.readValue("13498.000008374", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        Duration value = this.mapper.readValue("60", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 0);

        Duration value = this.mapper.readValue("13498", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        Duration value = this.mapper.readValue('"' + duration.toString() + '"', Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsString02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        Duration value = this.mapper.readValue('"' + duration.toString() + '"', Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsString03() throws Exception
    {
        Duration value = this.mapper.readValue("\"   \"", Duration.class);

        assertNull("The value should be null.", value);
    }

    @Test
    public void testDeserializationWithTypeInfo01() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        String prefix = "[\"" + Duration.class.getName() + "\",";

        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        TemporalAmount value1 = this.mapper.readValue(prefix + "13498.000008374]", TemporalAmount.class);
        TemporalAmount value2 = this.mapper.readValue(prefix + "13498]", TemporalAmount.class);
        TemporalAmount value3 = this.mapper.readValue(prefix + '"' + duration.toString() + "\"]", TemporalAmount.class);

        assertNotNull("The first value should not be null.", value1);
        assertTrue("The first value should be a Duration.", value1 instanceof Duration);
        assertEquals("The first value is not correct.", duration, value1);

        assertNotNull("The second value should not be null.", value2);
        assertTrue("The second value should be a Duration.", value2 instanceof Duration);
        assertEquals("The second value is not correct.", Duration.ofSeconds(13498L), value2);

        assertNotNull("The third value should not be null.", value3);
        assertTrue("The third value should be a Duration.", value3 instanceof Duration);
        assertEquals("The third value is not correct.", duration, value3);
    }
}
