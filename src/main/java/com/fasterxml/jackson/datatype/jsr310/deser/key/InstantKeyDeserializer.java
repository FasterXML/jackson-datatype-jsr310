package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class InstantKeyDeserializer extends Jsr310KeyDeserializer {

    public static final InstantKeyDeserializer INSTANCE = new InstantKeyDeserializer();

    private InstantKeyDeserializer() {
        // singleton
    }

    @Override
    protected Instant deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return DateTimeFormatter.ISO_INSTANT.parse(key, Instant::from);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(Instant.class, key, e.getMessage());
        }
    }
}
