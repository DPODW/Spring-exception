package com.hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {
/**
 * 매핑 경로로 이동해도, 결국 errorPageController 로 이동된다.
 * 그 이유는 -> servletExController  에서 예외가 발생하면, was 로 이동, WebServerCustomizer 가 호출된다
 * 그리고 WebServerCustomizer 에 기술된 매핑 경로로 매핑되는 것이다.
 *
 * */
    @GetMapping("/error-ex")
    public void errorEx(){
        throw  new RuntimeException("런타임 예외 발생~!");
    }

    @GetMapping("/error-404")
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404,"404오류");
    }

    @GetMapping("/error-500")
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500,"500오류");
    }

    @GetMapping("/error-400")
    public void error400(HttpServletResponse response) throws IOException {
        response.sendError(400,"400오류");
    }
}
