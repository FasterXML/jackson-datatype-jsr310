package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.Duration;

import com.fasterxml.jackson.databind.DeserializationContext;

public class DurationKeyDeserializer extends Jsr310KeyDeserializer {

    public static final DurationKeyDeserializer INSTANCE = new DurationKeyDeserializer();

    private DurationKeyDeserializer() {
        // singleton
    }

    @Override
    protected Duration deserialize(String key, DeserializationContext ctxt) {
        return Duration.parse(key);
    }

}
