package com.hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")
/* reason (메시지) 를 확인하고 싶으면 application properties 의 메시지 기능을 always 로 변경하면 된다.
*   message properties 기능도 지원한다.
* */
public class BadRequestException extends  RuntimeException{
}
