package com.vinsguru.springrsocket.security;

import org.springframework.util.RouteMatcher;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;

import java.util.Map;

public class TestMain {

    public static void main(String[] args) {
        PathPatternRouteMatcher matcher = new PathPatternRouteMatcher();
        Map<String, String> map = matcher.matchAndExtract("**\\.table", new RouteMatcher.Route() {
            @Override
            public String value() {
                return "math.service.secured.table";
            }
        });

        System.out.println(map);
    }

}
