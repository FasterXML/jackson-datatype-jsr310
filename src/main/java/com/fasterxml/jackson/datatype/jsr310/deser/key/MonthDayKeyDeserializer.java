package com.fasterxml.jackson.datatype.jsr310.deser.key;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;

import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.databind.DeserializationContext;

public class MonthDayKeyDeserializer extends Jsr310KeyDeserializer {

    public static final MonthDayKeyDeserializer INSTANCE = new MonthDayKeyDeserializer();

    // formatter copied from MonthDay
    private static final DateTimeFormatter PARSER = new DateTimeFormatterBuilder()
            .appendLiteral("--")
            .appendValue(MONTH_OF_YEAR, 2)
            .appendLiteral('-')
            .appendValue(DAY_OF_MONTH, 2)
            .toFormatter();

    private MonthDayKeyDeserializer() {
        // singleton
    }

    @Override
    protected MonthDay deserialize(String key, DeserializationContext ctxt) throws IOException {
        try {
            return MonthDay.parse(key, PARSER);
        } catch (DateTimeParseException e) {
            throw ctxt.weirdKeyException(MonthDay.class, key, e.getMessage());
        }
    }
}
