package com.example.demo.api.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.cursor.Cursor;

import com.example.demo.api.user.model.User;

@Mapper
public interface UserMapper {
	
	@Select(value = """
			SELECT * FROM USER
			""")
	public Cursor<User> selectUserList();

	@Select(value = """
			SELECT * FROM USER
			WHERE id like #{id}
			""")
	public User selectOneUser(@Param(value = "id") String id, @Param(value = "i") Integer i);
}
