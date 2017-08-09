package com.bizideal.mn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bizideal.mn.entity.Users;
import com.bizideal.mn.mapper.UsersMapper;
import com.bizideal.mn.service.IUserService;

/**
 * @author liulq:
 * @data 2017年3月6日 下午9:10:29
 * @version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UsersMapper usersMapper;

	@Override
	public Users login(Users users) {
		return usersMapper.selectByLogin(users);
	}

}
