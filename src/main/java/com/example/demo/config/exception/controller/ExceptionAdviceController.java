package com.example.demo.config.exception.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviceController {

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleException(HttpServletResponse response, Exception e) {
    return e.getLocalizedMessage();
  }

  
  /**
   * @Method : handleBindException
   * @CreateDate : 2021. 4. 15. 
   * @param response
   * @param e
   * @return
   * @Description : spring-boot-starter-validation @Valid용
   */
  @ExceptionHandler({BindException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public List<ObjectError> handleBindException(HttpServletResponse response, BindException e) {
    return e.getBindingResult().getAllErrors();
  }
}
