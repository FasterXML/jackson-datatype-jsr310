package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;

public class OffsetDateTimeKeyDeserializer extends Jsr310KeyDeserializer<OffsetDateTime> {

    @Override
    protected OffsetDateTime deserialize(String key, DeserializationContext ctxt) {
        return OffsetDateTime.parse(key, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

}
