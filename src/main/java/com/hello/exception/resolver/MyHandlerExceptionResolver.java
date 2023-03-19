package com.hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            if (ex instanceof IllegalArgumentException) {
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                //IllegalArgumentException 해당 예외가 터지면 그냥 400 으로 먹어버림

                return new ModelAndView();
                //ModelAndView 를 빈 값으로 던지면 그냥 정상 흐름으로 감
            }

        } catch (IOException e) {
            log.error("resolver ex",e);
        }
        return null;
    }
}
