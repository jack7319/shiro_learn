package com.bizideal.mn.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bizideal.mn.entity.Resources;
import com.bizideal.mn.entity.Roles;
import com.bizideal.mn.entity.Users;
import com.bizideal.mn.mapper.ResourcesMapper;
import com.bizideal.mn.mapper.UsersMapper;

/**
 * @author 作者 liulq:
 * @data 创建时间：2016年12月23日 上午9:19:32
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-context.xml")
public class JunitTest extends AbstractJUnit4SpringContextTests {

	ApplicationContext applicationContext = null;
	UsersMapper usersMapper = null;
	ResourcesMapper resourcesMapper = null;

	@Before
	public void init() {
		applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
		// 从spring容器中获得Mapper的代理对象
		usersMapper = applicationContext.getBean(UsersMapper.class);
		resourcesMapper = applicationContext.getBean(ResourcesMapper.class);
	}

	@Test
	public void get() {
		Users users = new Users();
		users.setAccount("admin");
		users.setPassword("admin");
		Users selectByLogin = usersMapper.selectByLogin(users);
		System.out.println(selectByLogin);
	}

	@Test
	public void getr() {
		List<Roles> roles = new ArrayList<>();
		Roles r1 = new Roles();
		r1.setId(1);
		Roles r2 = new Roles();
		r2.setId(2);
		roles.add(r1);
		roles.add(r2);
		List<Resources> selectByRoles = resourcesMapper.selectByRoles(roles);
		for (Resources resources : selectByRoles) {
			System.out.println(resources);
		}
	}

}
