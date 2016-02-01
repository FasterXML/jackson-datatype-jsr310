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
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link Year}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class YearDeserializer extends JSR310DeserializerBase<Year>
{
    private static final long serialVersionUID = 1L;
    private DateTimeFormatter formatter;
    public static final YearDeserializer INSTANCE = new YearDeserializer();

    private YearDeserializer()
    {
        super(Year.class);
    }

    public YearDeserializer(DateTimeFormatter formatter) {
        super(Year.class);
        this.formatter = formatter;
    }

    @Override
    public Year deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        JsonToken t = parser.getCurrentToken();
        if (t == JsonToken.VALUE_STRING) {
            String str = parser.getValueAsString().trim();
            return Year.parse(str, formatter);
        }
        if (t == JsonToken.VALUE_NUMBER_INT) {
            return Year.of(parser.getIntValue());
        }
        throw context.mappingException("Unexpected token (%s), expected VALUE_STRING or VALUE_NUMBER_INT",
                parser.getCurrentToken());
    }
}
