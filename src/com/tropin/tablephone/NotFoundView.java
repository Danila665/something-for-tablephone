package com.tropin.tablephone;

import java.util.Map;

public class NotFoundView extends HtmlView {

    @Override
    protected String getTitle() {
        return "Not Found";
    }

    @Override
    protected String getBody(Map<String, Object> map) {
        return "<h1>Not Found</h1>";
    }
}