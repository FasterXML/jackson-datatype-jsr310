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
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

/**
 * Serializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author Nick Williams
 * @since 2.2
 */
public class OffsetTimeSerializer extends JSR310FormattedSerializerBase<OffsetTime>
{
    private static final long serialVersionUID = 1L;

    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();

    protected OffsetTimeSerializer() {
        super(OffsetTime.class);
    }

    protected OffsetTimeSerializer(OffsetTimeSerializer base,
            Boolean useTimestamp, DateTimeFormatter dtf) {
        super(base, useTimestamp, dtf);
    }

    @Override
    protected OffsetTimeSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf) {
        return new OffsetTimeSerializer(this, useTimestamp, dtf);
    }
    
    @Override
    public void serialize(OffsetTime time, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        if (useTimestamp(provider)) {
            generator.writeStartArray();
            generator.writeNumber(time.getHour());
            generator.writeNumber(time.getMinute());
            final int secs = time.getSecond();
            final int nanos = time.getNano();
            if (secs > 0 || nanos > 0)
            {
                generator.writeNumber(secs);
                if (nanos > 0)
                {
                    if(provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
                        generator.writeNumber(nanos);
                    else
                        generator.writeNumber(time.get(ChronoField.MILLI_OF_SECOND));
                }
            }
            generator.writeString(time.getOffset().toString());
            generator.writeEndArray();
        } else {
            String str = (_formatter == null) ? time.toString() : time.format(_formatter);
            generator.writeString(str);
        }
    }
}
