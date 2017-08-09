package com.bizideal.mn.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bizideal.mn.core.UserRealm;
import com.bizideal.mn.entity.Users;
import com.bizideal.mn.service.IUserService;

/**
 * @author liulq:
 * @data 2017年3月6日 下午9:11:06
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserRealm.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private UserRealm userRealm;

	@RequestMapping("/login")
	public String login(Users user, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		String username = user.getAccount();
		UsernamePasswordToken token = new UsernamePasswordToken(
				user.getAccount(), user.getPassword());
		if (null != request.getParameter("rememberMe")) {
			// 勾选了记住密码
			token.setRememberMe(true);
		}
		token.setRememberMe(true);
		// 获取当前的Subject
		Subject currentUser = SecurityUtils.getSubject();
		// logger.info("从HttpSession中读取Attr : test = {}",
		// currentUser.getSession().getAttribute("test"));
		try {
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.info("对用户[" + username + "]进行登录验证..验证开始");
			currentUser.login(token);
			logger.info("对用户[" + username + "]进行登录验证..验证通过");
		} catch (UnknownAccountException uae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户");
			redirectAttributes.addFlashAttribute("message", "未知账户");
		} catch (IncorrectCredentialsException ice) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
			redirectAttributes.addFlashAttribute("message", "密码不正确");
		} catch (LockedAccountException lae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
			redirectAttributes.addFlashAttribute("message", "账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
			redirectAttributes.addFlashAttribute("message", "用户名或密码错误次数过多");
		} catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
			ae.printStackTrace();
			redirectAttributes.addFlashAttribute("message", "用户名或密码不正确");
		}
		// 验证是否登录成功
		if (currentUser.isAuthenticated()) {
			logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
			Users users = new Users();
			users.setAccount(username);
			Users login = userService.login(users);
			request.getSession().setAttribute("user", login);
			return "loginsuccess";
		} else {
			token.clear();
			return "redirect:/login";
		}
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest request) {
		if (false) {
			request.getSession().setAttribute("a", 3);
			// 手动清除缓存的权限信息
			userRealm.clearCached();
		}
		return "index";
	}

	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/login";
	}
}
