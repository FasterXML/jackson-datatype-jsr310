package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
    public void testSerializationAsTimestampNanoseconds01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "60.000000000", value);
    }

    @Test
    public void testSerializationAsTimestampNanoseconds02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "13498.000008374", value);
    }

    @Test
    public void testSerializationAsTimestampMilliseconds01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "60000", value);
    }

    @Test
    public void testSerializationAsTimestampMilliseconds02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "13498000", value);
    }

    @Test
    public void testSerializationAsTimestampMilliseconds03() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 837481723);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "13498837", value);
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
    @Ignore("Possible bug in mapper? Comma omitted from written value when writeRaw used.")
    //TODO: Investigate, file bug if necessary
    public void testSerializationWithTypeInfo01() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + Duration.class.getName() + "\",13498.000008374]", value);
    }

    @Test
    public void testSerializationWithTypeInfo02() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 837481723);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + Duration.class.getName() + "\",13498837]", value);
    }

    @Test
    public void testSerializationWithTypeInfo03() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(duration);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + Duration.class.getName() + "\",\"" + duration.toString() + "\"]", value);
    }

    @Test
    public void testDeserializationAsFloat01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        Duration value = this.mapper.readValue("60.0", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsFloat02() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        Duration value = this.mapper.readValue("60.0", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsFloat03() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        Duration value = this.mapper.readValue("13498.000008374", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsFloat04() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        Duration value = this.mapper.readValue("13498.000008374", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt01() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        Duration value = this.mapper.readValue("60", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt02() throws Exception
    {
        Duration duration = Duration.ofSeconds(60L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        Duration value = this.mapper.readValue("60000", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt03() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        Duration value = this.mapper.readValue("13498", Duration.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", duration,  value);
    }

    @Test
    public void testDeserializationAsInt04() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 0);

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        Duration value = this.mapper.readValue("13498000", Duration.class);

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

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        TemporalAmount value = this.mapper.readValue(prefix + "13498.000008374]", TemporalAmount.class);

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a Duration.", value instanceof Duration);
        assertEquals("The value is not correct.", duration, value);
    }

    @Test
    public void testDeserializationWithTypeInfo02() throws Exception
    {
        String prefix = "[\"" + Duration.class.getName() + "\",";

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        TemporalAmount value = this.mapper.readValue(prefix + "13498]", TemporalAmount.class);

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a Duration.", value instanceof Duration);
        assertEquals("The value is not correct.", Duration.ofSeconds(13498L), value);
    }

    @Test
    public void testDeserializationWithTypeInfo03() throws Exception
    {
        String prefix = "[\"" + Duration.class.getName() + "\",";

        this.mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        TemporalAmount value = this.mapper.readValue(prefix + "13498837]", TemporalAmount.class);

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a Duration.", value instanceof Duration);
        assertEquals("The value is not correct.", Duration.ofSeconds(13498L, 837000000), value);
    }

    @Test
    public void testDeserializationWithTypeInfo04() throws Exception
    {
        Duration duration = Duration.ofSeconds(13498L, 8374);

        String prefix = "[\"" + Duration.class.getName() + "\",";

        this.mapper.addMixInAnnotations(TemporalAmount.class, MockObjectConfiguration.class);
        TemporalAmount value = this.mapper.readValue(prefix + '"' + duration.toString() + "\"]", TemporalAmount.class);

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a Duration.", value instanceof Duration);
        assertEquals("The value is not correct.", duration, value);
    }
}
