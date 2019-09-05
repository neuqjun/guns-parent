package com.stylefeng.guns.api.util;

import javax.servlet.http.HttpServletRequest;

public class TokenUitls {

    public static String getToken(HttpServletRequest request) {
        String requestHeader = request.getHeader("Authorization");
        String token = null;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
        }
        return token;
    }
}
