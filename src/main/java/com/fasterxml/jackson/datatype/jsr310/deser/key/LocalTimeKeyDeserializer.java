package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;

public class LocalTimeKeyDeserializer extends Jsr310KeyDeserializer<LocalTime> {

    public static final LocalTimeKeyDeserializer INSTANCE = new LocalTimeKeyDeserializer();

    private LocalTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected LocalTime deserialize(String key, DeserializationContext ctxt) {
        return LocalTime.parse(key, DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
