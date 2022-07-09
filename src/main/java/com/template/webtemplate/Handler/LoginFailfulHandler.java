package com.template.webtemplate.Handler;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailfulHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errmsg;
        if(exception instanceof AuthenticationServiceException)
        {
            errmsg = "시스템 오류";
        }
        else if(exception instanceof BadCredentialsException)
        {
            errmsg = "아이디 또는 비밀번호를 확인하세요";
        }
        else if(exception instanceof DisabledException)
        {
            errmsg = "사용할 수 없는 계정입니다.";
        }
        else if(exception instanceof LockedException)
        {
            errmsg = "잠긴 계정입니다.";
        }
        else if(exception instanceof AccountExpiredException)
        {
            errmsg = "사용할 수 없는 계정입니다.";
        }
        else if(exception instanceof CredentialsExpiredException)
        {
            errmsg = "사용할 수 없는 계정입니다.";
        }
        else
        {
            errmsg = "계정을 찾을 수 없습니다.";
        }
        errmsg = URLEncoder.encode(errmsg,"UTF-8");
        response.sendRedirect("/member/login?error=true&exception="+errmsg);
    }
}
