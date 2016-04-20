package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestOffsetTimeDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(OffsetTime.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC), "'12:00Z'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notanoffsettime'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(OffsetTime.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final OffsetTime value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private OffsetTime read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
