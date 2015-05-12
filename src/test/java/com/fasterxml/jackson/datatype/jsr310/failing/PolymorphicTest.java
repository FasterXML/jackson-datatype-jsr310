package com.fasterxml.jackson.datatype.jsr310.failing;

import java.time.ZoneId;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.ModuleTestBase;

public class PolymorphicTest extends ModuleTestBase
{
    // for [datatype-jsr310#24]
    @Test
    public void testZoneId() throws Exception
    {
        ObjectMapper mapper = newMapper();
        mapper.enableDefaultTyping();

        ZoneId exp = ZoneId.of("America/Chicago");

        String json = mapper.writerFor(ZoneId.class).writeValueAsString(exp);
System.out.println("JSON -> "+json);
        
        ZoneId act = mapper.readValue(json, ZoneId.class);
        
        assertEquals(exp, act);
    }
}
