package com.tropin.tablephone;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Utils {

    public static Map<String, List<String>> parseQueryString(String query) throws UnsupportedEncodingException{
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
            paramName = URLDecoder.decode(paramName, "UTF-8");

            if (paramName.isEmpty()) {
                continue;
            }

            paramValue = (idx > 0 ? param.substring(idx + 1) : "");
            paramValue = URLDecoder.decode(paramValue, "UTF-8");

            paramValues = parsedQuery.get(paramName);
            if (paramValues == null) {
                parsedQuery.put(paramName, paramValues = new LinkedList<>());
            }

            paramValues.add(paramValue);
        }

        return parsedQuery;
    }

    public static String escapeHTML(String s) {
        StringBuilder out = new StringBuilder(Math.max(16, s.length()));
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>' || c == '&') {
                out.append("&#");
                out.append((int) c);
                out.append(';');
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

}