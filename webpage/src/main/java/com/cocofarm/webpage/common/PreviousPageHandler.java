package com.cocofarm.webpage.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class PreviousPageHandler {
    private static final String[] EXCEPTION_PAGES = { "/err", "/admin", "/login", "/join", "/modifypw" };

    public String getPreviousPage(HttpServletRequest request) {
        String referer = request.getHeader("referer");

        if (referer != null && !containsExceptionPage(referer)) {
            return referer;
        } else {
            return "/";
        }
    }

    private boolean containsExceptionPage(String page) {
        for (String exceptionPage : EXCEPTION_PAGES) {
            if (page.contains(exceptionPage)) {
                return true;
            }
        }
        return false;
    }
}
