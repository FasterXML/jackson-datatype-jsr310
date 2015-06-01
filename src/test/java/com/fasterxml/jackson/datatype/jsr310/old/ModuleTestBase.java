package com.fasterxml.jackson.datatype.jsr310.old;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ModuleTestBase
{
    @SuppressWarnings("deprecation")
    protected ObjectMapper newMapper() {
        return new ObjectMapper()
                .registerModule(new com.fasterxml.jackson.datatype.jsr310.JSR310Module());
    }
}
