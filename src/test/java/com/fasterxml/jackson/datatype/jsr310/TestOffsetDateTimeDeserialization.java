package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.Test;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


public class TestOffsetDateTimeDeserialization extends ModuleTestBase
{
   private final ObjectReader READER = newMapper().readerFor(OffsetDateTime.class);

    @Test
    public void testDeserializationAsString01() throws Exception
    {
        expectSuccess(OffsetDateTime.of(2000, 1, 1, 12, 0, 0, 0, ZoneOffset.UTC), "'2000-01-01T12:00Z'");
    }

    @Test
    public void testDeserializationAsString02() throws Exception
    {
        expectSuccess(OffsetDateTime.of(2000, 1, 1, 7, 0, 0, 0, ZoneOffset.UTC), "'2000-01-01T12:00+05:00'");
    }

    @Test
    public void testDeserializationAsString03() throws Exception
    {
        //
        // Verify that the offset in the json is preserved when we disable ADJUST_DATES_TO_CONTEXT_TIME_ZONE
        //
        ObjectReader reader2 = newMapper().disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE).readerFor(OffsetDateTime.class);
        OffsetDateTime parsed = reader2.readValue(aposToQuotes("'2000-01-01T12:00+05:00'"));
        notNull(parsed);
        expect(OffsetDateTime.of(2000, 1, 1, 12, 0, 0, 0, ZoneOffset.ofHours(5)), parsed) ;
    }

    @Test
    public void testBadDeserializationAsString01() throws Throwable
    {
        expectFailure("'notanoffsetdatetime'");
    }

    private void expectFailure(String json) throws Throwable {
        try {
            read(json);
            fail("expected DateTimeParseException");
        } catch (JsonProcessingException e) {
            if (e.getCause() == null) {
                throw e;
            }
            if (!(e.getCause() instanceof DateTimeParseException)) {
                throw e.getCause();
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private void expectSuccess(Object exp, String json) throws IOException {
        final OffsetDateTime value = read(json);
        notNull(value);
        expect(exp, value);
    }

    private OffsetDateTime read(final String json) throws IOException {
        return READER.readValue(aposToQuotes(json));
    }

    private static void notNull(Object value) {
        assertNotNull("The value should not be null.", value);
    }

    private static void expect(Object exp, Object value) {
        assertEquals("The value is not correct.", exp,  value);
    }
}
