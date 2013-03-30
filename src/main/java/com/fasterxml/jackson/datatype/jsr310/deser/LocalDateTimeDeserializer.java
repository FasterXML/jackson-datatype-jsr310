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
import java.time.LocalDateTime;

/**
 * Deserializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class LocalDateTimeDeserializer extends JSR310DeserializerBase<LocalDateTime>
{
    private static final long serialVersionUID = 1L;

    public static final LocalDateTimeDeserializer INSTANCE = new LocalDateTimeDeserializer();

    private LocalDateTimeDeserializer()
    {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        switch(parser.getCurrentToken())
        {
            case START_ARRAY:
                parser.nextToken();
                if(parser.getCurrentToken() == JsonToken.END_ARRAY)
                    return null;
                int year = parser.getIntValue();

                parser.nextToken();
                int month = parser.getIntValue();

                parser.nextToken();
                int day = parser.getIntValue();

                parser.nextToken();
                int hour = parser.getIntValue();

                parser.nextToken();
                int minute = parser.getIntValue();

                parser.nextToken();
                if(parser.getCurrentToken() != JsonToken.END_ARRAY)
                {
                    int second = parser.getIntValue();

                    parser.nextToken();
                    if(parser.getCurrentToken() != JsonToken.END_ARRAY)
                    {
                        int nanosecond = parser.getIntValue();

                        parser.nextToken();
                        if(parser.getCurrentToken() != JsonToken.END_ARRAY)
                            throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");

                        return LocalDateTime.of(year, month, day, hour, minute, second, nanosecond);
                    }

                    return LocalDateTime.of(year, month, day, hour, minute, second);
                }

                return LocalDateTime.of(year, month, day, hour, minute);

            case VALUE_STRING:
                String string = parser.getText().trim();
                if(string.length() == 0)
                    return null;
                return LocalDateTime.parse(string);
        }

        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    }
}
