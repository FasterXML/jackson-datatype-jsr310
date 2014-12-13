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
import java.time.temporal.Temporal;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Base class for serializers used for {@link java.time.Instant} and related types.
 */
public class InstantSerializerBase<T extends Temporal> extends JSR310SerializerBase<T>
{
    private final ToLongFunction<T> getEpochMillis;

    private final ToLongFunction<T> getEpochSeconds;

    private final ToIntFunction<T> getNanoseconds;

    private final Function<T, String> toIsoString;

    protected InstantSerializerBase(Class<T> supportedType, ToLongFunction<T> getEpochMillis,
                              ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds)
    {
        this(supportedType, getEpochMillis, getEpochSeconds, getNanoseconds, t -> t.toString());
    }

    protected InstantSerializerBase(Class<T> supportedType, ToLongFunction<T> getEpochMillis,
        ToLongFunction<T> getEpochSeconds, ToIntFunction<T> getNanoseconds,
        Function<T, String> toIsoString)
    {
        super(supportedType);
        this.getEpochMillis = getEpochMillis;
        this.getEpochSeconds = getEpochSeconds;
        this.getNanoseconds = getNanoseconds;
        this.toIsoString = toIsoString;
    }

    @Override
    public void serialize(T instant, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        // 21-Aug-2014, tatu: TODO -- use @JsonFormat annotation to allow per-property overrides
        
        if(provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS))
        {
            if(provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
            {
                generator.writeNumber(DecimalUtils.toDecimal(
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
            generator.writeString(toIsoString.apply(instant));
        }
    }
}
