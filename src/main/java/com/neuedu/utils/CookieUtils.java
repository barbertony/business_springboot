package com.neuedu.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
    private static final String COOKIE_DOMAIN=".ilearn.com" ;
    public static void writeCookie(HttpServletResponse response,String cookieName,String cookieValue){

        Cookie cookie=new Cookie(cookieName,cookieValue);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);

    }

    public String readCookie(HttpServletRequest request,String key)
    {
        Cookie[] cookies=request.getCookies();
        if (cookies!=null&&cookies.length>0)
        {
            for (Cookie c:cookies)
            {
                if (c.getName().equals(key))
                {
                    return c.getValue();
                }
            }
        }
        return null;
    }
    public  static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String cookieName)

    {
        Cookie[] cookies=request.getCookies();
        if (cookies!=null&&cookies.length>0)
        {
            for (Cookie c:cookies)
            {
                if (c.getName().equals(cookieName))
                {
                    c.setDomain(COOKIE_DOMAIN);
                    c.setPath("/");
                    c.setMaxAge(0);
                    response.addCookie(c);
                }
            }
        }

    }
}
