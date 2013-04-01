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

package com.fasterxml.jackson.datatype.jsr310.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.util.function.Function;

/**
 * Deserializer for Java 8 temporal {@link Instant}s, {@link OffsetDateTime}, and {@link ZonedDateTime}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public final class InstantDeserializer<T extends Temporal> extends JSR310DeserializerBase<T>
{
    private static final long serialVersionUID = 1L;

    public static final InstantDeserializer<Instant> INSTANT = new InstantDeserializer<>(
            Instant.class, Instant::parse,
            a -> Instant.ofEpochMilli(a.value),
            a -> Instant.ofEpochSecond(a.integer, a.fraction)
    );

    public static final InstantDeserializer<OffsetDateTime> OFFSET_DATE_TIME = new InstantDeserializer<>(
            OffsetDateTime.class, OffsetDateTime::parse,
            a -> OffsetDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId),
            a -> OffsetDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction), a.zoneId)
    );

    public static final InstantDeserializer<ZonedDateTime> ZONED_DATE_TIME = new InstantDeserializer<>(
            ZonedDateTime.class, ZonedDateTime::parse,
            a -> ZonedDateTime.ofInstant(Instant.ofEpochMilli(a.value), a.zoneId),
            a -> ZonedDateTime.ofInstant(Instant.ofEpochSecond(a.integer, a.fraction), a.zoneId)
    );

    private final Function<FromIntegerArguments, T> fromMilliseconds;

    private final Function<FromDecimalArguments, T> fromNanoseconds;

    private final Function<CharSequence, T> parse;

    private InstantDeserializer(Class<T> supportedType, Function<CharSequence, T> parse,
                                Function<FromIntegerArguments, T> fromMilliseconds,
                                Function<FromDecimalArguments, T> fromNanoseconds)
    {
        super(supportedType);
        this.parse = parse;
        this.fromMilliseconds = fromMilliseconds;
        this.fromNanoseconds = fromNanoseconds;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        switch(parser.getCurrentToken())
        {
            case VALUE_NUMBER_FLOAT:
                BigDecimal value = parser.getDecimalValue();
                long seconds = value.longValue();
                int nanoseconds = DecimalUtils.extractNanosecondDecimal(value, seconds);
                return this.fromNanoseconds.apply(new FromDecimalArguments(
                        seconds, nanoseconds, this.getZone(context)
                ));

            case VALUE_NUMBER_INT:
                return this.fromMilliseconds.apply(new FromIntegerArguments(
                        parser.getLongValue(), this.getZone(context)
                ));

            case VALUE_STRING:
                String string = parser.getText().trim();
                if(string.length() == 0)
                    return null;
                return this.parse.apply(string);
        }
        throw context.mappingException("Expected type float, integer, or string.");
    }

    private ZoneId getZone(DeserializationContext context)
    {
        // instant doesn't need a zone, so don't waste compute cycles
        return this._valueClass == Instant.class ? null : context.getTimeZone().toZoneId();
    }

    private static class FromIntegerArguments
    {
        public final long value;
        public final ZoneId zoneId;

        private FromIntegerArguments(long value, ZoneId zoneId)
        {
            this.value = value;
            this.zoneId = zoneId;
        }
    }

    private static class FromDecimalArguments
    {
        public final long integer;
        public final int fraction;
        public final ZoneId zoneId;

        private FromDecimalArguments(long integer, int fraction, ZoneId zoneId)
        {
            this.integer = integer;
            this.fraction = fraction;
            this.zoneId = zoneId;
        }
    }
}
