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
import java.time.OffsetTime;
import java.time.ZoneOffset;

/**
 * Deserializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class OffsetTimeDeserializer extends JSR310DeserializerBase<OffsetTime>
{
    private static final long serialVersionUID = 1L;

    public static final OffsetTimeDeserializer INSTANCE = new OffsetTimeDeserializer();

    private OffsetTimeDeserializer()
    {
        super(OffsetTime.class);
    }

    @Override
    public OffsetTime deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        switch(parser.getCurrentToken())
        {
            case START_ARRAY:
                parser.nextToken();
                if(parser.getCurrentToken() == JsonToken.END_ARRAY)
                    return null;
                int hour = parser.getIntValue();

                parser.nextToken();
                int minute = parser.getIntValue();

                int second, nanosecond;
                parser.nextToken();
                if(parser.getCurrentToken() == JsonToken.VALUE_STRING)
                    return OffsetTime.of(hour, minute, 0, 0, ZoneOffset.of(parser.getText()));
                else if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT)
                    second = parser.getIntValue();
                else
                    throw context.wrongTokenException(parser, JsonToken.VALUE_NUMBER_INT, "Expected int or string");

                parser.nextToken();
                if(parser.getCurrentToken() == JsonToken.VALUE_STRING)
                    return OffsetTime.of(hour, minute, second, 0, ZoneOffset.of(parser.getText()));
                else if(parser.getCurrentToken() == JsonToken.VALUE_NUMBER_INT)
                    nanosecond = parser.getIntValue();
                else
                    throw context.wrongTokenException(parser, JsonToken.VALUE_NUMBER_INT, "Expected int or string");

                parser.nextToken();
                if(parser.getCurrentToken() == JsonToken.VALUE_STRING)
                    return OffsetTime.of(hour, minute, second, nanosecond, ZoneOffset.of(parser.getText()));
                else
                    throw context.wrongTokenException(parser, JsonToken.VALUE_STRING, "Expected string");

            case VALUE_STRING:
                String string = parser.getText().trim();
                if(string.length() == 0)
                    return null;
                return OffsetTime.parse(string);
        }

        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    }
}
