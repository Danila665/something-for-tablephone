/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 *
 * @author Danila
 */
public class FaviconController implements Controller{
    
    @Override
    public void process(HttpExchange he) throws IOException {
        Headers responseHeaders = he.getResponseHeaders();
        File fi = new File("E:\\favicon.ico");
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        
        String responseStr = String.join(
            "<!DOCTYPE html>",
            "<html>",
                "<head>",
                    "<title>FaviconPage</title>",
                "</head>",
                "<body>",
                    "<h1>Prepare for download</h1>",
                "</body>",
            "</html>"
        );
        
        responseHeaders.set("content-type", "image/x-icon");
        he.sendResponseHeaders(200, fileContent.length);
        he.getResponseBody().write(fileContent);
        he.close();
    }
}
