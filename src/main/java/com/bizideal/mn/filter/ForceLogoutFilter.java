package com.bizideal.mn.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月10日 下午4:19:28
 * @version 1.0
 */
public class ForceLogoutFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) throws Exception {
		Session session = getSubject(request, response).getSession(false);
		if (session == null) {
			return true;
		}
		return session.getAttribute("forceOut") == null;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request,
			ServletResponse response) throws Exception {
		try {
			getSubject(request, response).logout();// 强制退出
		} catch (Exception e) {/* ignore exception */
		}

		String loginUrl = getLoginUrl()
				+ (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=1";
		WebUtils.issueRedirect(request, response, loginUrl);
		return false;
	}
}
