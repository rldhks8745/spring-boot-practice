package com.example.demo.config.mybatis.handler;

import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;
import com.example.demo.config.mybatis.model.PagableResponse;
import lombok.Getter;

public class PagableResponseResulthandler implements ResultHandler<Object> {

  @Getter
  private final PagableResponse<Object> pagableResponse;

  public PagableResponseResulthandler() {
    pagableResponse = new PagableResponse<>();
  }

  @Override
  public void handleResult(ResultContext<? extends Object> resultContext) {
    pagableResponse.add(resultContext.getResultObject());
    
  }
}
