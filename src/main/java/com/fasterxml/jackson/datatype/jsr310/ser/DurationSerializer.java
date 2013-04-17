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
import java.time.Duration;

/**
 * Serializer for Java 8 temporal {@link Duration}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class DurationSerializer extends JSR310SerializerBase<Duration>
{
    public static final DurationSerializer INSTANCE = new DurationSerializer();

    private DurationSerializer()
    {
        super(Duration.class);
    }

    @Override
    public void serialize(Duration duration, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        if(provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS))
        {
            if(provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
            {
                generator.writeNumber(DecimalUtils.toDecimal(
                        duration.getSeconds(), duration.getNano()
                ));
            }
            else
            {
                generator.writeNumber(duration.toMillis());
            }
        }
        else
        {
            generator.writeString(duration.toString());
        }
    }
}
