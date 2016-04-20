package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class OffsetTimeKeyDeserializer extends Jsr310KeyDeserializer {

    public static final OffsetTimeKeyDeserializer INSTANCE = new OffsetTimeKeyDeserializer();

    private OffsetTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected OffsetTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return OffsetTime.parse(key, DateTimeFormatter.ISO_OFFSET_TIME);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(OffsetTime.class, key, e.getMessage());
        }
    }

}
