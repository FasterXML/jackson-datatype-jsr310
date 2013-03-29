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
import java.time.Duration;

/**
 * Deserializer for Java 8 temporal {@link Duration}s.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class DurationDeserializer extends JSR310DeserializerBase<Duration>
{
    public DurationDeserializer()
    {
        super(Duration.class);
    }

    @Override
    public Duration deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        switch(parser.getCurrentToken())
        {
            case VALUE_NUMBER_FLOAT:
                String text = parser.getText();
                String[] parts = text.split("\\.", 2);
                if(parts.length == 1 || parts[1] == null || parts[1].length() == 0)
                    return Duration.ofSeconds(Long.parseLong(parts[0]));
                return Duration.ofSeconds(Long.parseLong(parts[0]), Integer.parseInt(parts[1]));

            case VALUE_NUMBER_INT:
                return Duration.ofSeconds(parser.getLongValue());

            case VALUE_STRING:
                return Duration.parse(parser.getText());
        }

        throw context.mappingException("Expected type float, integer, or string.");
    }
}
