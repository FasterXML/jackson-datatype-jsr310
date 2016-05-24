package com.fasterxml.jackson.datatype.jsr310.failing;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import org.junit.Test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.ModuleTestBase;

public class FailingInstantRoundTrip76Test extends ModuleTestBase
{
    // [datatype-jsr310#79]
    @Test
    public void should_deserialize_instant_from_serialized_date_in_iso_8601_format() throws Exception
    {
        ObjectMapper mapper = newMapper();
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        Instant givenInstant = LocalDate.of(2016, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

//System.err.println("DEBUG: instant/ser == "+mapper.writeValueAsString(givenInstant));
//System.err.println("DEBUG: instant/jdk == "+givenInstant);
       
        String json = mapper.writeValueAsString(java.util.Date.from(givenInstant));

//System.err.println("DEBUG: json from java.util.Date: "+json);
        
        Instant actual = mapper.readValue(json, Instant.class); // this fails

        assertEquals(givenInstant, actual);
    }

}
