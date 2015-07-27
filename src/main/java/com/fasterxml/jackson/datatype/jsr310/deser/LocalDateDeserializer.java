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
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Deserializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class LocalDateDeserializer extends JSR310DateTimeDeserializerBase<LocalDate>
{
    private static final long serialVersionUID = 1L;

    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

    private LocalDateDeserializer() {
        this(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public LocalDateDeserializer(DateTimeFormatter dtf) {
        super(LocalDate.class, dtf);
    }

    @Override
    protected JsonDeserializer<LocalDate> withDateFormat(DateTimeFormatter dtf) {
        return new LocalDateDeserializer(dtf);
    }
    
    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        switch(parser.getCurrentTokenId())
        {
            case JsonTokenId.ID_START_ARRAY:
                if(parser.nextToken() == JsonToken.END_ARRAY) {
                    return null;
                }
                int year = parser.getIntValue();

                parser.nextToken();
                int month = parser.getIntValue();

                parser.nextToken();
                int day = parser.getIntValue();

                if (parser.nextToken() != JsonToken.END_ARRAY) {
                    throw context.wrongTokenException(parser, JsonToken.END_ARRAY, "Expected array to end.");
                }
                return LocalDate.of(year, month, day);

            case JsonTokenId.ID_STRING:
                String string = parser.getText().trim();
                if(string.length() == 0) {
                    return null;
                }

                if(string.contains("T")) {
                    return LocalDate.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }
                
                return LocalDate.parse(string, _formatter);
        }

        throw context.wrongTokenException(parser, JsonToken.START_ARRAY, "Expected array or string.");
    }
}
