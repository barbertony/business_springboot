package com.neuedu.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class MyExceptionHandler extends ExceptionHandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof MyException) {
            System.out.println("========ex====");
            MyException myException=(MyException) ex;
            String error=myException.getDirector();
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.setViewName("common/error");
            Map<String,Object> modle=modelAndView.getModel();
            modle.put("msg",myException.getMessage());
            return modelAndView;
        }
        return super.resolveException(request, response, handler, ex);

    }
}
