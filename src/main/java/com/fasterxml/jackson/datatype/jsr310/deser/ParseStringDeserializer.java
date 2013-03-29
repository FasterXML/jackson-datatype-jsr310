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
public final class ParseStringDeserializer<T> extends JSR310DeserializerBase<T>
{
    public static final ParseStringDeserializer<MonthDay> MONTH_DAY =
            new ParseStringDeserializer<>(MonthDay.class, MonthDay::parse);

    public static final ParseStringDeserializer<Period> PERIOD =
            new ParseStringDeserializer<>(Period.class, Period::parse);

    public static final ParseStringDeserializer<YearMonth> YEAR_MONTH =
            new ParseStringDeserializer<>(YearMonth.class, YearMonth::parse);

    public static final ParseStringDeserializer<ZoneId> ZONE_ID =
            new ParseStringDeserializer<>(ZoneId.class, ZoneId::of);

    public static final ParseStringDeserializer<ZoneOffset> ZONE_OFFSET =
            new ParseStringDeserializer<>(ZoneOffset.class, ZoneOffset::of);

    private final Function<String, T> parseMethod;

    private ParseStringDeserializer(Class<T> supportedType, Function<String, T> parseMethod)
    {
        super(supportedType);
        this.parseMethod = parseMethod;
    }

    @Override
    public T deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        return this.parseMethod.apply(parser.getText());
    }
}
