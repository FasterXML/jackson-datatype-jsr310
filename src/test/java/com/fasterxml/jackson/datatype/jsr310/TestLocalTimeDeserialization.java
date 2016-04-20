package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestLocalTimeDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(LocalTime.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(LocalTime.of(12, 0), "'12:00'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notalocaltime'");
    }

    private void expectFailure(String aposJson) throws Throwable {
        try {
            read(aposJson);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(LocalTime.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String aposJson) throws IOException {
        final LocalTime value = read(aposJson);
        notNull(value);
        expect(exp, value);
    }

    private LocalTime read(final String aposJson) throws java.io.IOException {
        return READER.readValue(aposToQuotes(aposJson));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
