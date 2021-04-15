package com.example.demo.api.user.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserRequest;
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
  public PagableResponse<User> selectUserList(UserRequest userRequest);

  @Select(value = """
          <script>
            SELECT * FROM USER
            WHERE 1 = 1
            <if test='id != null and !id.equals("")'>
              AND ID LIKE '%${id}%'
            </if>
          </script>
      """)
  public List<User> selectUserList2(UserRequest userRequest);

  @Select(value = """
      SELECT * FROM USER
      WHERE id like #{id}
      """)
  public User selectOneUser(@Param(value = "id") String id, @Param(value = "i") Integer i);
}
