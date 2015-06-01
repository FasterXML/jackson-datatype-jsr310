package com.fasterxml.jackson.datatype.jsr310.old;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

public class ModuleTestBase
{
    protected ObjectMapper newMapper() {
        return new ObjectMapper()
                .registerModule(new JSR310Module());
    }
}
