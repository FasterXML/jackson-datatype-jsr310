package com.fasterxml.jackson.datatype.jsr310.deser.key;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.DeserializationContext;

public class LocalDateDeserializer extends Jsr310KeyDeserializer<LocalDate> {

    @Override
    protected LocalDate deserialize(String key, DeserializationContext ctxt) {
        return LocalDate.parse(key, DateTimeFormatter.ISO_LOCAL_DATE);
    }

}
