package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;

public class ZonedDateTimeKeyDeserializer extends Jsr310KeyDeserializer<ZonedDateTime> {

    public static final ZonedDateTimeKeyDeserializer INSTANCE = new ZonedDateTimeKeyDeserializer();

    private ZonedDateTimeKeyDeserializer() {
        // singleton
    }

    @Override
    protected ZonedDateTime deserialize(String key, DeserializationContext ctxt) {
        // not serializing timezone data yet
        return ZonedDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
