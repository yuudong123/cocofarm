package com.cocofarm.webpage.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.cocofarm.webpage.domain.MemberVO;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        MemberVO memberVO = (MemberVO) request.getSession().getAttribute("userinfo");

        if (memberVO != null) {
            return true;
        } else {
            response.sendRedirect("/err/login-required");
            return false;
        }
    }
}
