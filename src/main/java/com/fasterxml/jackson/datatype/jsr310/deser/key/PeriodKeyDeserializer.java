package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.Period;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class PeriodKeyDeserializer extends Jsr310KeyDeserializer {

    public static final PeriodKeyDeserializer INSTANCE = new PeriodKeyDeserializer();

    private PeriodKeyDeserializer() {
        // singletin
    }

    @Override
    protected Period deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return Period.parse(key);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(Period.class, key, e.getMessage());
        }
    }
}
