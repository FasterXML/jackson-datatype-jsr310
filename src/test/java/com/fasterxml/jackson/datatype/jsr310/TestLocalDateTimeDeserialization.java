package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestLocalDateTimeDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(LocalDateTime.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0), "'2000-01-01T12:00'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notalocaldatetime'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(LocalDateTime.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final LocalDateTime value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private LocalDateTime read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
