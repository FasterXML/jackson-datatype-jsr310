package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class ZonedDateTimeKeyDeserializer extends Jsr310KeyDeserializer {

    public static final ZonedDateTimeKeyDeserializer INSTANCE = new ZonedDateTimeKeyDeserializer();

    private ZonedDateTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected ZonedDateTime deserialize(String key, DeserializationContext ctxt) throws IOException {
        // not serializing timezone data yet
        try {
            return ZonedDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(ZonedDateTime.class, key, e.getMessage());
        }
    }
}
