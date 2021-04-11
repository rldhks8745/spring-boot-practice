package com.example.demo.api.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.api.user.model.User;

@Mapper
public interface UserMapper {
	
	@Select(value = """
			SELECT * FROM USER
			""")
	public List<User> selectUserList();
}
