package com.hello.exception;

import com.hello.exception.filter.LogFilter;
import com.hello.exception.interceptor.LogInterceptor;
import com.hello.exception.resolver.MyHandlerExceptionResolver;
import com.hello.exception.resolver.UserHandlerExceptionResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  /**
   * 인터셉터에선
   * 에러 페이지 url 자체를 제외시킨다.
   *     ㄴ 추가적인 처리를 하려면 따로 인터셉터를 파던가 해야한다..!
   * */
   @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**","*.ico", "/error","error-page/**");
    }

    /**
     * 예외 리졸버 등록
     * */
    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(new MyHandlerExceptionResolver());
        resolvers.add(new UserHandlerExceptionResolver());
    }

    /**
 * DispatcherTypes: type 이 request -> 사용자의 요청 // type 이 error -> 에러 요청
 * 디폴트는 request 이다. 추가적으로 error 에 관한 요청을 따로 관리할 필요가 있을땐 error 타입을 지정한다.
 * 오류 페이지 요청만 받아들이는 '오류 페이지 전용 필터' 를 구현하고 싶을때는 error 만 지정해주면 된다.
 *          ㄴ 효율적이다.
 *
 * */
//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);
        //DispatcherTypes 이 매개값인 경우에 작동
        return filterRegistrationBean;

    }

}
