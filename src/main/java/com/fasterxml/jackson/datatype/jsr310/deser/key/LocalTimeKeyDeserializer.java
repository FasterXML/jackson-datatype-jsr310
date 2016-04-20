package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class LocalTimeKeyDeserializer extends Jsr310KeyDeserializer {

    public static final LocalTimeKeyDeserializer INSTANCE = new LocalTimeKeyDeserializer();

    private LocalTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected LocalTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return LocalTime.parse(key, DateTimeFormatter.ISO_LOCAL_TIME);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(LocalTime.class, key, e.getMessage());
        }
    }

}
