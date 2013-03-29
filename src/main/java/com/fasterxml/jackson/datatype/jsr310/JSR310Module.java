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

package com.fasterxml.jackson.datatype.jsr310;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.PeriodDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;

import java.time.Duration;
import java.time.Period;

/**
 * Class that registers this module with the Jackson core.<br />
 * <br />
 * <code>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new JSR310Module());
 * </code><br />
 * <br />
 * All JSR310 types are serialized as numbers (integers or decimals as appropriate) if the
 * {@link com.fasterxml.jackson.databind.SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} feature is enabled, and
 * otherwise are serialized in standard <a href="http://en.wikipedia.org/wiki/ISO_8601" target="_blank">ISO-8601</a>
 * string representation. ISO-8601 specifies formats for representing offset dates and times, zoned dates and times,
 * local dates and times, periods, durations, and more. All JSR310 types have built-in translation to and from
 * ISO-8601 formats.<br />
 * <br />
 * The only exception to this rule is the serialization/deserialization of {@link java.time.Period}, which always
 * results in an ISO-8601 representation because Periods must be represented in years, months, and/or days.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class JSR310Module extends SimpleModule
{
    private static final long serialVersionUID = 1L;

    public JSR310Module()
    {
        super(PackageVersion.VERSION);
        // first deserializers
        //addDeserializer(DateMidnight.class, new DateMidnightDeserializer());
        //addDeserializer(DateTime.class, DateTimeDeserializer.forType(DateTime.class));
        addDeserializer(Duration.class, new DurationDeserializer());
        //addDeserializer(Instant.class, new InstantDeserializer());
        //addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        //addDeserializer(LocalDate.class, new LocalDateDeserializer());
        //addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        addDeserializer(Period.class, new PeriodDeserializer());
        //addDeserializer(ReadableDateTime.class, DateTimeDeserializer.forType(ReadableDateTime.class));
        //addDeserializer(ReadableInstant.class, DateTimeDeserializer.forType(ReadableInstant.class));

        // then serializers:
        //addSerializer(DateMidnight.class, new DateMidnightSerializer());
        //addSerializer(DateTime.class, new DateTimeSerializer());
        addSerializer(Duration.class, new DurationSerializer());
        //addSerializer(Instant.class, new InstantSerializer());
        //addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        //addSerializer(LocalDate.class, new LocalDateSerializer());
        //addSerializer(LocalTime.class, new LocalTimeSerializer());
        addSerializer(Period.class, ToStringSerializer.instance);
    }
}
