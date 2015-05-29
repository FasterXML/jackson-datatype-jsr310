package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ModuleTestBase
{
    protected ObjectMapper newMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }
}
