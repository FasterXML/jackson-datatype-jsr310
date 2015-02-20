package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.temporal.Temporal;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.key.Jsr310NullKeySerializer;

abstract class Jsr310KeyDeserializer<T extends Temporal> extends KeyDeserializer {

    @Override
    public final Object deserializeKey(String key, DeserializationContext ctxt) {
        if (Jsr310NullKeySerializer.NULL_KEY.equals(key)) {
            // potential null key in HashMap
            return null;
        }
        return deserialize(key, ctxt);
    }

    protected abstract T deserialize(String key, DeserializationContext ctxt);
}