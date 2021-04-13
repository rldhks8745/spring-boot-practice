package com.example.demo.config.mybatis.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


/**
 * @Package : com.oneplat.ecommerce.common.model
 * @FileName : AbstractPagableResponse.java
 * @CreateDate : 2021. 4. 9.
 * @author : 82105
 * @param <M>
 * @Description : 페이지 정보를 가지고 있는 model response
 */

@Data
@JsonFormat(shape = Shape.OBJECT)
public class PagableResponse<T> implements Collection<T> {

  private List<T> list;
  
  private PageInfo pageInfo;

  public PagableResponse() {
    list = new ArrayList<>();
    pageInfo = new PageInfo();
  }
  
  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return list.contains(o);
  }

  @Override
  public Iterator<T> iterator() {
    return list.iterator();
  }

  @Override
  public Object[] toArray() {
    return list.toArray();
  }

  @Override
  public <T> T[] toArray(T[] a) {
    return list.toArray(a);
  }

  @Override
  public boolean add(T e) {
    return list.add(e);
  }

  @Override
  public boolean remove(Object o) {
    return list.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return list.containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends T> c) {
    return list.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return list.retainAll(c);
  }

  @Override
  public void clear() {
    list.clear();
  }
}
