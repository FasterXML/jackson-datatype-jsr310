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
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Year;

/**
 * Serializer for Java 8 temporal {@link Year}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class YearSerializer extends JSR310SerializerBase<Year>
{
    public static final YearSerializer INSTANCE = new YearSerializer();

    private YearSerializer()
    {
        super(Year.class);
    }

    @Override
    public void serialize(Year year, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        generator.writeNumber(year.getValue());
    }
}
