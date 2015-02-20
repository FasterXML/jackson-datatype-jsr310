package com.fasterxml.jackson.datatype.jsr310.deser.key;

import static java.time.temporal.ChronoField.YEAR;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import com.fasterxml.jackson.databind.DeserializationContext;

public class YearKeyDeserializer extends Jsr310KeyDeserializer<Year> {

    /*
     * formatter copied from Year. There is no way of getting a reference to the
     * formatter it uses.
     */
    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .toFormatter();

    @Override
    protected Year deserialize(String key, DeserializationContext ctxt) {
        return Year.parse(key, FORMATTER);
    }

}
