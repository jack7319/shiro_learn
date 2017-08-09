package com.bizideal.mn.mapper;

import com.bizideal.mn.entity.Users;

import tk.mybatis.mapper.common.Mapper;

public interface UsersMapper extends Mapper<Users> {

	public Users selectByLogin(Users users);

}