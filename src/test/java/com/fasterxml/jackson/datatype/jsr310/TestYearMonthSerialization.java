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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.Temporal;

import static org.junit.Assert.*;

public class TestYearMonthSerialization
{
    private ObjectMapper mapper;

    @Before
    public void setUp()
    {
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JSR310Module());
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testSerialization01() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(1986, Month.JANUARY);

        String value = this.mapper.writeValueAsString(yearMonth);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "\"1986-01\"", value);
    }

    @Test
    public void testSerialization02() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(2013, Month.AUGUST);

        String value = this.mapper.writeValueAsString(yearMonth);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "\"2013-08\"", value);
    }

    @Test
    public void testSerializationWithTypeInfo01() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(2005, Month.NOVEMBER);

        this.mapper.addMixIn(Temporal.class, MockObjectConfiguration.class);
        String value = this.mapper.writeValueAsString(yearMonth);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", "[\"" + YearMonth.class.getName() + "\",\"2005-11\"]", value);
    }

    @Test
    public void testDeserialization01() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(1986, Month.JANUARY);

        YearMonth value = this.mapper.readValue("\"1986-01\"", YearMonth.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", yearMonth, value);
    }

    @Test
    public void testDeserialization02() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(2013, Month.AUGUST);

        YearMonth value = this.mapper.readValue("\"2013-08\"", YearMonth.class);

        assertNotNull("The value should not be null.", value);
        assertEquals("The value is not correct.", yearMonth, value);
    }

    @Test
    public void testDeserializationWithTypeInfo01() throws Exception
    {
        YearMonth yearMonth = YearMonth.of(2005, Month.NOVEMBER);

        this.mapper.addMixIn(Temporal.class, MockObjectConfiguration.class);
        Temporal value = this.mapper.readValue("[\"" + YearMonth.class.getName() + "\",\"2005-11\"]", Temporal.class);

        assertNotNull("The value should not be null.", value);
        assertTrue("The value should be a YearMonth.", value instanceof YearMonth);
        assertEquals("The value is not correct.", yearMonth, value);
    }
}
