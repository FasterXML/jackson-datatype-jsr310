package com.fasterxml.jackson.datatype.jsr310.ser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime>
{
    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

    protected ZonedDateTimeSerializer() {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
                // ISO_ZONED_DATE_TIME is not the ISO format, it is an extension of it
                dt -> DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(dt));
    }
}
