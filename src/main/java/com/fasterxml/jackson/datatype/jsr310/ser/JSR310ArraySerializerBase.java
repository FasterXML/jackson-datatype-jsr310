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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.lang.reflect.Type;

/**
 * Base class that provides an array schema instead of scalar schema if
 * {@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} is enabled.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
abstract class JSR310ArraySerializerBase<T> extends JSR310SerializerBase<T>
{
    protected JSR310ArraySerializerBase(Class<T> supportedType)
    {
        super(supportedType);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
    {
        return this.createSchemaNode(
                provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) ? "array" : "string", true
        );
    }
}
