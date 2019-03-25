package com.tropin.tablephone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

class UtilsTest {

    @Test
    void parseNullOrEmptyQueryString() throws UnsupportedEncodingException{
        Map<String, List<String>> query;

        query = Utils.parseQueryString(null);
        assertNotNull(query);
        assertTrue(query.isEmpty());

        query = Utils.parseQueryString("");
        assertNotNull(query);
        assertTrue(query.isEmpty());
    }

    @Test
    void parseSimpleQueryString() throws UnsupportedEncodingException{
        Map<String, List<String>> query = Utils.parseQueryString("param1=value1&param2=value2");
        assertNotNull(query);
        assertFalse(query.isEmpty());
        assertTrue(query.containsKey("param1"));
        assertTrue(query.containsKey("param2"));
        assertEquals(query.get("param1"), List.of("value1"));
        assertEquals(query.get("param2"), List.of("value2"));
    }

    @Test
    void parseMultiplyValuesQueryString() throws UnsupportedEncodingException{
        Map<String, List<String>> query = Utils.parseQueryString("param=value1&param=value2");
        assertNotNull(query);
        assertFalse(query.isEmpty());
        assertTrue(query.containsKey("param"));
        assertEquals(query.get("param"), List.of("value1", "value2"));
    }

    @Test
    void parseUrlEncodedQueryString() throws UnsupportedEncodingException{
        Map<String, List<String>> query = Utils.parseQueryString("<%20param%20%20%20=?%20%20%20абвГДЕ");
        assertNotNull(query);
        assertFalse(query.isEmpty());
        assertTrue(query.containsKey("< param   "));
        assertEquals(query.get("< param   "), List.of("?   абвГДЕ"));
    }

}