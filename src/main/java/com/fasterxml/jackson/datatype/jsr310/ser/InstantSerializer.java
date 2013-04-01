/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package com.fasterxml.jackson.datatype.jsr310.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Serializer for Java 8 temporal {@link Instant}s, {@link OffsetDateTime}, and {@link ZonedDateTime}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public final class InstantSerializer<T extends Temporal> extends JSR310SerializerBase<T>
{
    public static final InstantSerializer<Instant> INSTANT =
            new InstantSerializer<>(Instant.class, Instant::toEpochMilli, Instant::getEpochSecond, Instant::getNano);

    public static final InstantSerializer<OffsetDateTime> OFFSET_DATE_TIME =
            new InstantSerializer<>(OffsetDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                    OffsetDateTime::toEpochSecond, OffsetDateTime::getNano);

    public static final InstantSerializer<ZonedDateTime> ZONED_DATE_TIME =
            new InstantSerializer<>(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
                    ZonedDateTime::toEpochSecond, ZonedDateTime::getNano);

    private final ToLongFunction<T> getEpochMillis;

    private final ToLongFunction<T> getEpochSeconds;

    private final ToIntFunction<T> getNanoseconds;

    private InstantSerializer(Class<T> supportedType, ToLongFunction<T> getEpochMillis,
                              ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds)
    {
        super(supportedType);
        this.getEpochMillis = getEpochMillis;
        this.getEpochSeconds = getEpochSeconds;
        this.getNanoseconds = getNanoseconds;
    }

    @Override
    public void serialize(T instant, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        if(provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS))
        {
            if(this.serializeWithNanoseconds())
            {
                generator.writeRaw(DecimalUtils.toDecimal(
                        this.getEpochSeconds.applyAsLong(instant), this.getNanoseconds.applyAsInt(instant)
                ));
            }
            else
            {
                generator.writeNumber(this.getEpochMillis.applyAsLong(instant));
            }
        }
        else
        {
            generator.writeString(instant.toString());
        }
    }

    //TODO: Placeholder until configuration option added
    private boolean serializeWithNanoseconds()
    {
        return true;
    }
}
