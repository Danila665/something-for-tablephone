package com.tropin.tablephone;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static Map<String, List<String>> parseQueryString(String query) {
        if (query == null) {
            return Collections.emptyMap();
        }

        final Map<String, List<String>> parsedQuery = new HashMap<>();
        final String[] params = query.split("&");

        List<String> paramValues;
        String paramName;
        String paramValue;
        int idx;

        for (String param : params) {
            idx = param.indexOf("=");
            paramName = (idx > 0 ? param.substring(0, idx) : param);
            paramName = URLDecoder.decode(paramName, StandardCharsets.UTF_8);

            if (paramName.isBlank()) {
                continue;
            }

            paramValue = (idx > 0 ? param.substring(idx + 1) : "");
            paramValue = URLDecoder.decode(paramValue, StandardCharsets.UTF_8);

            paramValues = parsedQuery.get(paramName);
            if (paramValues == null) {
                parsedQuery.put(paramName, paramValues = new LinkedList<>());
            }

            paramValues.add(paramValue);
        }

        return parsedQuery;
    }

}