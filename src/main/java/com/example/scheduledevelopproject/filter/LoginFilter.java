package com.example.scheduledevelopproject.filter;

import com.example.scheduledevelopproject.exception.CustomException;
import com.example.scheduledevelopproject.exception.ErrorResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

import static com.example.scheduledevelopproject.exception.ErrorCode.INVALID_LOGIN;

@Slf4j
public class LoginFilter implements Filter {

    // 인증을 하지 않아도될 URL Path 배열
    private static final String[] WHITE_LIST = {"/", "/users","/signup", "/login", "/logout"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        try{
            if(!isWhiteList(requestURI)){
                HttpSession session = httpRequest.getSession(false);

                if(session == null || session.getAttribute("userId") == null){
                    throw new CustomException(INVALID_LOGIN);
                }

                log.info("로그인에 성공했습니다.");
            }

            filterChain.doFilter(request,response);
        }catch(CustomException e){
            httpServletResponse.setStatus(e.getErrorCode().getHttpStatus().value());
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                    .status(e.getErrorCode().getHttpStatus().value())
                    .code(e.getErrorCode().name())
                    .message(e.getErrorCode().getMessage())
                    .detailMessage(e.getDetailMessage())
                    .build();

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorResponse);

            httpServletResponse.getWriter().write(jsonResponse);
        }

    }

    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST,requestURI);
    }
}
