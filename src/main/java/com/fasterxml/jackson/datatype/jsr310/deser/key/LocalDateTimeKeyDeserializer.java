package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;

public class LocalDateTimeKeyDeserializer extends Jsr310KeyDeserializer {

    public static final LocalDateTimeKeyDeserializer INSTANCE = new LocalDateTimeKeyDeserializer();

    private LocalDateTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected LocalDateTime deserialize(String key, DeserializationContext ctxt) {
        return LocalDateTime.parse(key, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

}
