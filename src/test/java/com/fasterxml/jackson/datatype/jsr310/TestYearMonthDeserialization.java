package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.Month;
import java.time.YearMonth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestYearMonthDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(YearMonth.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(YearMonth.of(2000, Month.JANUARY), "'2000-01'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notayearmonth'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(YearMonth.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final YearMonth value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private YearMonth read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
