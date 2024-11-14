package com.example.scheduledevelopproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LogInFilter implements Filter {

    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/", "/signup", "/login", "/logout"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if(!isWhiteList(requestURI)){
            HttpSession session = httpRequest.getSession(false);

            if(session == null || session.getAttribute("email") == null){
                throw new RuntimeException("로그인 해주세요");
            }

            log.info("로그인에 성공했습니다.");
        }

        filterChain.doFilter(request,response);
    }

    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST,requestURI);
    }
}
