package com.bizideal.mn.utils;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bizideal.mn.entity.Users;

/**
 * @author liulq:
 * @data 2017年3月11日 上午11:32:26
 * @version 1.0
 * @Description:TODO
 */
@Component
public class SessionHelper {

	/* 静态成员注入时，方法不能设置为静态，get方法不相关 */
	@Autowired
	private static SessionDAO sessionDAO;

	public SessionHelper() {
		super();
	}

	public static String getAccount(String sessionId) {
		Session readSession = sessionDAO.readSession(sessionId);
		Users user = (Users) readSession.getAttribute("user");
		if (null == user) {
			return "";
		}
		return user.getAccount();
	}

	public static boolean isForceOut(String sessionId) {
		Session readSession = sessionDAO.readSession(sessionId);
		if (null != readSession.getAttribute("forceOut")) {
			return true;
		}
		return false;
	}

	public void setSessionDAO(SessionDAO sessionDAO) {
		SessionHelper.sessionDAO = sessionDAO;
	}

	public static SessionDAO getSessionDAO() {
		return sessionDAO;
	}

}
