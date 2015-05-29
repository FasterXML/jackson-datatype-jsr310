package com.fasterxml.jackson.datatype.jsr310.ser;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

// TODO deprecate this: SerializationFeature config should be respected, default behaviour should be to
// serialize according to ISO-8601 format
public class ZonedDateTimeWithZoneIdSerializer extends InstantSerializerBase<ZonedDateTime>
{
    private static final long serialVersionUID = 1L;

    public static final ZonedDateTimeWithZoneIdSerializer INSTANCE = new ZonedDateTimeWithZoneIdSerializer();

    protected ZonedDateTimeWithZoneIdSerializer() {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
                // Serialize in a backwards compatible way: with zone id
                DateTimeFormatter.ISO_ZONED_DATE_TIME::format);
    }
}
