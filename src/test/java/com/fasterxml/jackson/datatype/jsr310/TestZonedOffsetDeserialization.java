package com.fasterxml.jackson.datatype.jsr310;

import java.io.IOException;
import java.time.ZoneOffset;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class TestZonedOffsetDeserialization extends ModuleTestBase
{
    private final ObjectReader READER = newMapper().readerFor(ZoneOffset.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(ZoneOffset.of("+0300"), "'+0300'");
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notazonedoffset'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected InvalidFormatException");
        } catch (InvalidFormatException e) {
            assertEquals(ZoneOffset.class, e.getTargetType());
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final ZoneOffset value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private ZoneOffset read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
