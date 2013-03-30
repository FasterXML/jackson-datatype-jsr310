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
import com.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Class that registers this module with the Jackson core.<br />
 * <br />
 * <code>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.discoverModules();
 * </code><br />
 * <b>—OR—</b><br />
 * <code>
 *     ObjectMapper mapper = new ObjectMapper();
 *     mapper.registerModule(new JSR310Module());
 * </code><br />
 * <br />
 * Most JSR310 types are serialized as numbers (integers or decimals as appropriate) if the
 * {@link com.fasterxml.jackson.databind.SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} feature is enabled, and
 * otherwise are serialized in standard <a href="http://en.wikipedia.org/wiki/ISO_8601" target="_blank">ISO-8601</a>
 * string representation. ISO-8601 specifies formats for representing offset dates and times, zoned dates and times,
 * local dates and times, periods, durations, zones, and more. All JSR310 types have built-in translation to and from
 * ISO-8601 formats.<br />
 * <br />
 * Some exceptions to this standard serialization/deserialization rule:<br />
 * <ul>
 *     <li>{@link Period}, which always results in an ISO-8601 format because Periods must be represented in years,
 *     months, and/or days.</li>
 *     <li>{@link java.time.Year}, which only contains a year and cannot be represented with a timestamp.</li>
 *     <li>{@link YearMonth}, which only contains a year and a month and cannot be represented with a timestamp.</li>
 *     <li>{@link MonthDay}, which only contains a month and a day and cannot be represented with a timestamp.</li>
 *     <li>{@link ZoneId} and {@link ZoneOffset}, which do not actually store dates and times but are supported with
 *     this module nonetheless.</li>
 * </ul>
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
        addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        addDeserializer(MonthDay.class, JSR310StringParsableDeserializer.MONTH_DAY);
        addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        addDeserializer(Year.class, new YearDeserializer());
        addDeserializer(YearMonth.class, JSR310StringParsableDeserializer.YEAR_MONTH);
        addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);
        //addDeserializer(ReadableDateTime.class, DateTimeDeserializer.forType(ReadableDateTime.class));
        //addDeserializer(ReadableInstant.class, DateTimeDeserializer.forType(ReadableInstant.class));

        // then serializers:
        //addSerializer(DateMidnight.class, new DateMidnightSerializer());
        //addSerializer(DateTime.class, new DateTimeSerializer());
        addSerializer(Duration.class, new DurationSerializer());
        //addSerializer(Instant.class, new InstantSerializer());
        //addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        addSerializer(MonthDay.class, ToStringSerializer.instance);
        addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        addSerializer(Period.class, ToStringSerializer.instance);
        addSerializer(Year.class, new YearSerializer());
        addSerializer(YearMonth.class, ToStringSerializer.instance);
        addSerializer(ZoneId.class, ToStringSerializer.instance);
        addSerializer(ZoneOffset.class, ToStringSerializer.instance);
    }
}
