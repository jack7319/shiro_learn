package com.bizideal.mn.controller;

import java.util.Collection;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月10日 下午4:14:51
 * @version 1.0 类说明
 */
@Controller
@RequestMapping("/sessions")
public class SessionController {

	@Autowired
	private SessionDAO sessionDAO;

	@RequestMapping("/list")
	public String list(Model model) {
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		model.addAttribute("sessions", sessions);
		model.addAttribute("sessionCount", sessions.size());
		return "sessionlist";
	}

	@RequestMapping("/{sessionId}/forceLogout")
	public String forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			Session session = sessionDAO.readSession(sessionId);
			if (session != null) {
				session.setAttribute("forceOut", Boolean.TRUE);
			}
		} catch (Exception e) {/* ignore */
		}
		redirectAttributes.addFlashAttribute("msg", "强制退出成功！");
		return "redirect:/sessions/list";
	}

}
