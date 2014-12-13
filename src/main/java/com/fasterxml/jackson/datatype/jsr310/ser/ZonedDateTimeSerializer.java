package com.fasterxml.jackson.datatype.jsr310.ser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime>
{
    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

    protected ZonedDateTimeSerializer() {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
                dt -> DateTimeFormatter.ISO_ZONED_DATE_TIME.format(dt));
    }
}
