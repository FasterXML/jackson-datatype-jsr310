package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.Month;
import java.time.MonthDay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestMonthDayDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(MonthDay.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(MonthDay.of(Month.JANUARY, 1), "'--01-01'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notamonthday'");
    }

    private void expectFailure(String aposJson) throws Throwable {
        try {
            read(aposJson);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(MonthDay.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String aposJson) throws IOException {
        final MonthDay value = read(aposJson);
        notNull(value);
        expect(exp, value);
    }

    private MonthDay read(final String aposJson) throws IOException {
        return READER.readValue(aposToQuotes(aposJson));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
