package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.ZoneOffset;

import com.fasterxml.jackson.databind.DeserializationContext;

public class ZoneOffsetKeyDeserializer extends Jsr310KeyDeserializer {

    public static final ZoneOffsetKeyDeserializer INSTANCE = new ZoneOffsetKeyDeserializer();

    private ZoneOffsetKeyDeserializer() {
        // singleton
    }

    @Override
    protected ZoneOffset deserialize(String key, DeserializationContext ctxt) {
        return ZoneOffset.of(key);
    }

}
