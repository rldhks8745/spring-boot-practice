package com.example.demo.api.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.config.mybatis.model.PagableResponse;

@Mapper
public interface UserMapper {

  @Select(value = """
          <script>
            SELECT * FROM USER
            WHERE 1 = 1
            <if test='id != null and !id.equals("")'>
              AND ID LIKE '%${id}%'
            </if>
          </script>
      """)
  public PagableResponse<User> selectUserList(UserSearch userRequest);
}
