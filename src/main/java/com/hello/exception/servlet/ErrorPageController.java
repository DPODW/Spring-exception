package com.hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response){
        log.info("errorpage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorpage 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    /**
     *  produces = MediaType 해당 속성을 사용하면, 특정 요청 형식(json,xml 등등) 에 맞는 컨트롤러가 호출된다.
     *      ㄴ 요청 타입과 같은 타입으로 응답해준다
     *
     *  ResponseEntity: HTTP 요청에 대한 응답을 나타내는데 사용됩니다
     *      ㄴ HTTP 응답 데이터를 포함할 수 있는 객체입니다. 예를 들어, ResponseEntity 를 사용하여 JSON, XML, HTML 등의 다양한 형식으로 응답 본문을 전달할 수 있습니다.
     *      ㄴ ResponseEntity 는 Generic 클래스로 구현되어 있으므로, 반환할 데이터의 타입도 지정할 수 있습니다.
     *
     *
     *
     *  이러한 과정 (pro~~ 타입 지정 같은) 을 spring 제공 에러 컨트롤러인 BasicErrorController 가 다 해준다. (안에 다 정의되어있다)
     * */
    public ResponseEntity<Map<String,Object>> errorPage500Api(HttpServletRequest request, HttpServletResponse response){
            log.info("API 에러페이지 500");

            Map<String, Object> result = new HashMap<>();
            Exception ex   = (Exception) request.getAttribute(ERROR_EXCEPTION);
            result.put("status", request.getAttribute(ERROR_STATUS_CODE));
            result.put("message",ex.getMessage());

            Integer statusCode =(Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex=", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}
