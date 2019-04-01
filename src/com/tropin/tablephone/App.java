/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tropin.tablephone;

import com.sun.net.httpserver.HttpServer;
import com.tropin.tablephone.interfaces.Router;

import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;


/**
 *
 * @author Danila
 */
public class App {

    private HttpServer httpServer;

    private SQLiteDataSource dataSource;

    public DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new SQLiteDataSource();
            try {
                dataSource.setUrl("jdbc:sqlite:" + ClassLoader.getSystemResource("resources/db/db.s3db").toURI().getPath());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }

            try (Connection conn = dataSource.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS contacts (id INT, name VARCHAR(255), number VARCHAR(255))")) {
                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);

            }
        }

        return dataSource;
    }

    public Router getRouter() {
        NotSoStupidRouter router = new NotSoStupidRouter();

        //router.addRoute("/", (new IndexController(new ContactMemoryStorage())));
        router.addRoute("/", (new IndexController(new DbContactStorage(getDataSource()))));
        router.addRoute("/favicon.ico", new StaticController("/resources/img/favicon.ico"));
        router.addRoute("/style.css", new StaticController("/resources/css/style.css"));

        return router;
    }

    public void start() throws IOException {

        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);

        httpServer.createContext("/", new Handler(
                getRouter(),
                new ErrorController(), 
                new NotFoundController()));

        httpServer.start(); 
    }

    public void stop(int delay) {
        httpServer.stop(delay);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        final App myApp = new App();

        myApp.start();
    }
    
}
