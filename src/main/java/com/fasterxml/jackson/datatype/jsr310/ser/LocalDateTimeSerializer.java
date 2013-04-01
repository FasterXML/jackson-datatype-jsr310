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

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Serializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class LocalDateTimeSerializer extends JSR310ArraySerializerBase<LocalDateTime>
{
    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

    private LocalDateTimeSerializer()
    {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator generator, SerializerProvider provider)
            throws IOException
    {
        if(provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS))
        {
            generator.writeStartArray();
            generator.writeNumber(dateTime.getYear());
            generator.writeNumber(dateTime.getMonthValue());
            generator.writeNumber(dateTime.getDayOfMonth());
            generator.writeNumber(dateTime.getHour());
            generator.writeNumber(dateTime.getMinute());
            if(dateTime.getSecond() > 0)
                generator.writeNumber(dateTime.getSecond());
            if(dateTime.getNano() > 0)
                generator.writeNumber(dateTime.getNano());
            generator.writeEndArray();
        }
        else
        {
            generator.writeString(dateTime.toString());
        }
    }
}
