package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.temporal.Temporal;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

abstract class Jsr310KeyDeserializer<T extends Temporal> extends KeyDeserializer {

    @Override
    public final Object deserializeKey(String key, DeserializationContext ctxt) {
        if (key.length() == 0) {
            // potential null key in HashMap
            return null;
        }
        return deserialize(key, ctxt);
    }

    protected abstract T deserialize(String key, DeserializationContext ctxt);
}