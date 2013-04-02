/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.Temporal;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class TestOffsetDateTimeSerialization
{
    private static final ZoneId Z1 = ZoneId.of("America/Chicago");

    private static final ZoneId Z2 = ZoneId.of("America/Anchorage");

    private static final ZoneId Z3 = ZoneId.of("America/Denver");

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
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "0.000000000", value);
    }

    @Test
    public void testSerializationAsTimestamp02() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "123456789.183917322", value);
    }

    @Test
    public void testSerializationAsTimestamp03() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", DecimalUtils.toDecimal(date.toEpochSecond(), date.getNano()), value);
    }

    @Test
    public void testSerializationAsString01() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + date.toString() + '"', value);
    }

    @Test
    public void testSerializationAsString02() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + date.toString() + '"', value);
    }

    @Test
    public void testSerializationAsString03() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", '"' + date.toString() + '"', value);
    }

    @Test
    @Ignore("Possible bug in mapper? Comma omitted from written value when writeRaw used.")
    //TODO: Investigate, file bug if necessary
    public void testSerializationWithTypeInfo01() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + OffsetDateTime.class.getName() + "\",123456789.183917322]", value);
    }

    @Test
    public void testSerializationWithTypeInfo02() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(date);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.",
                "[\"" + OffsetDateTime.class.getName() + "\",\"" + date.toString() + "\"]", value);
    }

    @Test
    public void testDeserializationAsTimestamp01WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        OffsetDateTime value = this.mapper.readValue("0.000000000", OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsTimestamp01WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue("0.000000000", OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationAsTimestamp02WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        OffsetDateTime value = this.mapper.readValue("123456789.183917322", OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsTimestamp02WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue("123456789.183917322", OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationAsTimestamp03WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        OffsetDateTime value = this.mapper.readValue(
                DecimalUtils.toDecimal(date.toEpochSecond(), date.getNano()), OffsetDateTime.class
        );

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsTimestamp03WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue(
                DecimalUtils.toDecimal(date.toEpochSecond(), date.getNano()), OffsetDateTime.class
        );

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationAsString01WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsString01WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(0L), Z1);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationAsString02WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsString02WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationAsString03WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, value.getOffset());
    }

    @Test
    public void testDeserializationAsString03WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.setTimeZone(TimeZone.getDefault());
        OffsetDateTime value = this.mapper.readValue('"' + date.toString() + '"', OffsetDateTime.class);

        assertNotNull("The value should not be null.", value);
        assertIsEqual(date, value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), value.getOffset());
    }

    @Test
    public void testDeserializationWithTypeInfo01WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetDateTime.class.getName() + "\",123456789.183917322]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be an OffsetDateTime.", value instanceof OffsetDateTime);
        assertIsEqual(date, (OffsetDateTime) value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, ((OffsetDateTime) value).getOffset());
    }

    @Test
    public void testDeserializationWithTypeInfo01WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.ofInstant(Instant.ofEpochSecond(123456789L, 183917322), Z2);

        this.mapper.setTimeZone(TimeZone.getDefault());
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetDateTime.class.getName() + "\",123456789.183917322]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be an OffsetDateTime.", value instanceof OffsetDateTime);
        assertIsEqual(date, (OffsetDateTime) value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), ((OffsetDateTime) value).getOffset());
    }

    @Test
    public void testDeserializationWithTypeInfo02WithoutTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetDateTime.class.getName() + "\",\"" + date.toString() + "\"]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be an OffsetDateTime.", value instanceof OffsetDateTime);
        assertIsEqual(date, (OffsetDateTime) value);
        assertEquals("The time zone is not correct.", ZoneOffset.UTC, ((OffsetDateTime) value).getOffset());
    }

    @Test
    public void testDeserializationWithTypeInfo02WithTimeZone() throws Exception
    {
        OffsetDateTime date = OffsetDateTime.now(Z3);

        this.mapper.setTimeZone(TimeZone.getDefault());
        this.mapper.addMixInAnnotations(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue(
                "[\"" + OffsetDateTime.class.getName() + "\",\"" + date.toString() + "\"]", Temporal.class
        );

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be an OffsetDateTime.", value instanceof OffsetDateTime);
        assertIsEqual(date, (OffsetDateTime) value);
        assertEquals("The time zone is not correct.", getDefaultOffset(date), ((OffsetDateTime) value).getOffset());
    }

    private static void assertIsEqual(OffsetDateTime expected, OffsetDateTime actual)
    {
        assertTrue("The value is not correct. Expected timezone-adjusted <" + expected + ">, actual <" + actual + ">.",
                expected.isEqual(actual));
    }

    private static ZoneOffset getDefaultOffset(OffsetDateTime date)
    {
        return ZoneId.systemDefault().getRules().getOffset(date.toInstant());
    }
}
