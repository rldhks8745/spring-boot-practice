package com.example.demo.api.user.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.example.demo.api.user.model.User;
import com.example.demo.api.user.model.UserSearch;
import com.example.demo.config.mybatis.model.PagableResponse;

@Mapper
public interface UserMapper {

  @Select( """
          <script>
            SELECT * FROM USER
            WHERE 1 = 1
            <if test='id != null and !id.equals("")'>
              AND ID LIKE '%${id}%'
            </if>
          </script>
      """)
  public PagableResponse<User> selectListUser(UserSearch userRequest);

  @Select( """
          <script>
            SELECT * FROM USER
            WHERE
              NUM = #{num}
          </script>
      """)
  public User selectOneUser(Long num);
  
  @Insert("""
          <script>
            INSERT INTO USER (
              ID
              , PW
              , NAME
            )
            VALUES (
              #{id}
              , #{pw}
              , #{name}
            )
          </script>
          """)
  @Options(useGeneratedKeys = true, keyProperty = "num")
  public Long insertUser(User user);

  @Update("""
          <script>
            UPDATE USER SET
              Num = #{num}
              <if test='id != null and !id.equals("")'>
              , ID = #{id}
              </if>
              <if test='pw != null and !pw.equals("")'>
              , pw = #{pw}
              </if>
              <if test='name != null and !name.equals("")'>
              , name = #{name}
              </if>
            WHERE
              NUM = #{num}
          </script>
          """)
  public Long updateUser(User user);
}
