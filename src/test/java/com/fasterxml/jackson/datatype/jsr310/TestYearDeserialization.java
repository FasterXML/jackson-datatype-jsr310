package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.Year;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestYearDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(Year.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(Year.of(2000), "'2000'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notayear'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(Year.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final Year value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private Year read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
