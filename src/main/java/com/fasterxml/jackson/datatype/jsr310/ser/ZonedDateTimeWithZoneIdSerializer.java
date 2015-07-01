package com.fasterxml.jackson.datatype.jsr310.ser;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

// TODO deprecate this: SerializationFeature config should be respected,
// default behaviour should be to serialize according to ISO-8601 format
/**
 * @since 2.6
 */
public class ZonedDateTimeWithZoneIdSerializer extends InstantSerializerBase<ZonedDateTime>
{
    private static final long serialVersionUID = 1L;

    public static final ZonedDateTimeWithZoneIdSerializer INSTANCE = new ZonedDateTimeWithZoneIdSerializer();

    protected ZonedDateTimeWithZoneIdSerializer() {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
                // Serialize in a backwards compatible way: with zone id, using toString method
                null);
    }

    protected ZonedDateTimeWithZoneIdSerializer(ZonedDateTimeWithZoneIdSerializer base,
            Boolean useTimestamp, DateTimeFormatter formatter) {
        super(base, useTimestamp, formatter);
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter) {
        return new ZonedDateTimeWithZoneIdSerializer(this, useTimestamp, formatter);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        // copied from ZonedDateTimeSerializer. Keep them in sync. This class is to be removed soon (maybe 2.7?)
        if (!useTimestamp(provider) &&
                provider.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)) {
            // write with zone
            generator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value));
        }
        // else
        super.serialize(value, generator, provider);
    }
}
