package com.fasterxml.jackson.datatype.jsr310.ser;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime> {
    private static final long serialVersionUID = 1L;

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

    protected ZonedDateTimeSerializer() {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
                // ISO_ZONED_DATE_TIME is not the ISO format, it is an extension of it
                DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    protected ZonedDateTimeSerializer(ZonedDateTimeSerializer base,
            Boolean useTimestamp, DateTimeFormatter formatter) {
        super(base, useTimestamp, formatter);
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter) {
        return new ZonedDateTimeSerializer(this, useTimestamp, formatter);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (!useTimestamp(provider) &&
                provider.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)) {
            // write with zone
            generator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value));
            return;
        }
        // else
        super.serialize(value, generator, provider);
    }

}
