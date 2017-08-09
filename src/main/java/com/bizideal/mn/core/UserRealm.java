package com.bizideal.mn.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizideal.mn.entity.Resources;
import com.bizideal.mn.entity.Roles;
import com.bizideal.mn.entity.Users;
import com.bizideal.mn.service.IResourceService;
import com.bizideal.mn.service.IUserService;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月6日 下午8:52:52
 * @version 1.0 自定义的realm
 */
@Component
public class UserRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private IResourceService resourceService;

	/**
	 * 权限认证，为当前登录的Subject授予角色和权限
	 * 
	 * @see 经测试：本例中该方法的调用时机为需授权资源被访问时
	 * @see 经测试：并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
	 * @see 经测试：如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（使用的ehcache，时间在ehcache
	 *      -shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {
		logger.info("##################执行Shiro权限认证##################");
		// 获取当前登录输入的用户名，等价于(String)principalCollection.fromRealm(getName()).iterator().next();
		String loginName = (String) super
				.getAvailablePrincipal(principalCollection);
		// 到数据库查是否有此对象
		// 查出是否有此用户
		Users users = new Users();
		users.setAccount(loginName);
		Users user = userService.login(users);// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		// SecurityUtils.getSubject().getSession().setAttribute("user", user);
		if (user != null) {
			// 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 取出用户的角色集合
			Set<String> roleNames = new HashSet<>();
			for (Roles role : user.getRoles()) {
				roleNames.add(role.getName());
			}
			info.setRoles(roleNames);
			// 用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
			List<Resources> selectByRoles = resourceService.selectByRoles(user
					.getRoles());
			Set<String> resourceNames = new HashSet<>();
			for (Resources resources : selectByRoles) {
				resourceNames.add(resources.getName());
			}
			info.addStringPermissions(resourceNames);
			// 或者按下面这样添加 添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
			// simpleAuthorInfo.addRole("admin");
			// 添加权限
			// simpleAuthorInfo.addStringPermission("admin:manage");
			// logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");
			return info;
		}
		// 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// UsernamePasswordToken对象用来存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

		logger.info("验证当前Subject时获取到token为："
				+ ReflectionToStringBuilder.toString(token,
						ToStringStyle.MULTI_LINE_STYLE));

		// 查出是否有此用户
		Users users = new Users();
		users.setAccount(token.getUsername());
		Users login = userService.login(users);
		if (login != null) {
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
			return new SimpleAuthenticationInfo(login.getAccount(),
					login.getPassword(), getName());
		}
		return null;
	}

	/**
	 * 清除权限缓存信息
	 * 
	 * @param principals
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	// 清除缓存,不会清除session
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject()
				.getPrincipals();
		/* 会刷新权限缓存，再走一次doGetAuthorizationInfo方法 */
		// super.clearCache(principals);
		/* 会刷新权限缓存，再走一次doGetAuthorizationInfo方法 */
		super.clearCachedAuthorizationInfo(principals);
		/* 不会刷新权限缓存 */
		// super.clearCachedAuthenticationInfo(principals);
	}

}
