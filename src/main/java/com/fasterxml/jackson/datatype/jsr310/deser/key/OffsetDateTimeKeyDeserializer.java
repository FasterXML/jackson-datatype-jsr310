package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class OffsetDateTimeKeyDeserializer extends Jsr310KeyDeserializer {

    public static final OffsetDateTimeKeyDeserializer INSTANCE = new OffsetDateTimeKeyDeserializer();

    private OffsetDateTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected OffsetDateTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return OffsetDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(OffsetDateTime.class, key, e.getMessage());
        }
    }
}
