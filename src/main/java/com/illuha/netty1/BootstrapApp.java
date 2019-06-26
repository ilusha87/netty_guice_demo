package com.illuha.netty1;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.illuha.netty1.guice.module.GuiceModule;
import com.illuha.netty1.netty.HttpServer;

public class BootstrapApp {

    public static void main(String[] args) throws Exception {

        String dbConnectionUrl = "jdbc:derby:mydb1;create=true";
        String hostname = "localhost";
        int port = 8080;
        if (args.length >= 2) {
            hostname = args[0];
            port = Integer.valueOf(args[1]);
        }
        Injector injector = Guice.createInjector(new GuiceModule(hostname, port, dbConnectionUrl));
        final HttpServer server = injector.getInstance(HttpServer.class);
        server.run();

    }

}
