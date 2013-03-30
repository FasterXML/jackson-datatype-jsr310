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

import java.io.IOException;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.Function;

/**
 * Deserializer for all Java 8 temporal {@link java.time} types that cannot be represented with numbers and that have
 * parse functions that can take {@link String}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public final class JSR310StringParsableDeserializer<T> extends JSR310DeserializerBase<T>
{
    public static final JSR310StringParsableDeserializer<MonthDay> MONTH_DAY =
            new JSR310StringParsableDeserializer<>(MonthDay.class, MonthDay::parse);

    public static final JSR310StringParsableDeserializer<Period> PERIOD =
            new JSR310StringParsableDeserializer<>(Period.class, Period::parse);

    public static final JSR310StringParsableDeserializer<YearMonth> YEAR_MONTH =
            new JSR310StringParsableDeserializer<>(YearMonth.class, YearMonth::parse);

    public static final JSR310StringParsableDeserializer<ZoneId> ZONE_ID =
            new JSR310StringParsableDeserializer<>(ZoneId.class, ZoneId::of);

    public static final JSR310StringParsableDeserializer<ZoneOffset> ZONE_OFFSET =
            new JSR310StringParsableDeserializer<>(ZoneOffset.class, ZoneOffset::of);

    private final Function<String, T> parseMethod;

    private JSR310StringParsableDeserializer(Class<T> supportedType, Function<String, T> parseMethod)
    {
        super(supportedType);
        this.parseMethod = parseMethod;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        String string = parser.getText().trim();
        if(string.length() == 0)
            return null;
        return this.parseMethod.apply(string);
    }
}
