package com.hello.exception.exhandler.advice;

import com.hello.exception.exception.UserException;
import com.hello.exception.exhandler.errorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    /**
     * @ExceptionHandler -> 두가지 예외도 처리 가능하다. (두개를 묶어서 부모 예외로 처리 가능)
     * 해당 예외 처리 기능을 다른 컨트롤러에서도 동일하게 사용하고 싶을때는
     *  ControllerAdvice 를 사용하면 된다.
     *  
     *  패키지 단위, 컨트롤러 타입 등 특정 컨트롤러만 적용 시키는 방법도 가능하다. (일반적으로 패키지 단위로 사용한다)
     *  pdf 참고
     * */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    //@ExceptionHandler 는 예외를 해결하면서, 정상 로직으로 바꾸는데,
    //      ㄴ 그렇게 되면서 200 상태 코드가 뜬다. (예외인데도!) -> 상태 코드 또한 특정 하고 싶으면 @ResponseStatus 를 사용하면 된다
    public errorResult illegalArgumentHandler(IllegalArgumentException e){
        log.error("[exceptionHandler] ex", e);
        return new errorResult("bad", e.getMessage()); //json 으로 반환됌
    }

    @ExceptionHandler
    public ResponseEntity<errorResult> userExHandler(UserException e){
        log.error("[exceptionHandler] ex", e);
        errorResult errorResult1 = new errorResult("USER-EX", e.getMessage());
        return new ResponseEntity(errorResult1,HttpStatus.BAD_REQUEST);
        //ResponseEntity 의 매개값으로 error 정보와, 상태 코드를 설정한다
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public errorResult exHandler(Exception e) {
        log.error("[exceptionHandler] ex", e);
        return new errorResult("EX","내부오류");
    }
    /*Exception 은 최상위 예외이기 때문에, 위에 정의된 예외(IllegalArgumentException,UserException)
      에서 예외를 잡지 못하면, exHandler 로 넘어오게 된다.*/
}
