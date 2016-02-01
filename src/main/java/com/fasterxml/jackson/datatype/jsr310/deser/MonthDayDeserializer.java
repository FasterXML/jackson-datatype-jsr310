package com.fasterxml.jackson.datatype.jsr310.deser;

import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Deserializer for Java 8 temporal {@link MonthDay}s.
 */
public class MonthDayDeserializer extends JSR310DeserializerBase<MonthDay>
{
    private static final long serialVersionUID = 1L;
    private DateTimeFormatter formatter;
    public static final MonthDayDeserializer INSTANCE = new MonthDayDeserializer();

    private MonthDayDeserializer() {
        this(null);
    }

    public MonthDayDeserializer(DateTimeFormatter formatter) {
        super(MonthDay.class);
        this.formatter = formatter;
    }

    @Override
    public MonthDay deserialize(JsonParser parser, DeserializationContext context) throws IOException
    {
        if (parser.hasToken(JsonToken.VALUE_STRING)) {
            String str = parser.getValueAsString().trim();
            if (formatter == null) {
                return MonthDay.parse(str);
            }
            return MonthDay.parse(str, formatter);
        }
        throw context.mappingException("Unexpected token (%s), expected VALUE_STRING or VALUE_NUMBER_INT",
                parser.getCurrentToken());
    }
}
