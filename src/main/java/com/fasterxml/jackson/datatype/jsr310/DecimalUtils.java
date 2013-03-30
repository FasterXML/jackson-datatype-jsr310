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

package com.fasterxml.jackson.datatype.jsr310;

import java.math.BigDecimal;

/**
 * Utilities to aid in the translation of decimal types to/from multiple parts.
 *
 * @author Nick Williams
 * @since 2.2.0
 */
public class DecimalUtils
{
    private static final char[] ZEROES = new char[] {'0', '0', '0', '0', '0', '0', '0', '0', '0'};

    private static final BigDecimal ONE_BILLION = new BigDecimal(1_000_000_000L);

    private DecimalUtils()
    {
        throw new RuntimeException("DecimalUtils cannot be instantiated.");
    }

    public static String toDecimal(long seconds, int nanoseconds)
    {
        StringBuilder string = new StringBuilder(Integer.toString(nanoseconds));
        if(string.length() < 9)
            string.insert(0, ZEROES, 0, 9 - string.length());
        return seconds + "." + string;
    }

    public static int extractNanosecondDecimal(BigDecimal value, long integer)
    {
        return value.subtract(new BigDecimal(integer)).multiply(ONE_BILLION).intValue();
    }
}
