package com.bizideal.mn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author 作者 liulq:
 * @data 创建时间：2017年3月7日 上午9:46:32
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "redirect:/login";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/need")
	public String need() {
		return "need";
	}

	@RequestMapping("/noneed")
	public String noneed() {
		return "noneed";
	}

	@RequestMapping("/chat")
	public String chat() {
		return "chat";
	}

	@RequestMapping("/tuling")
	@ResponseBody
	public ObjectNode tuling(String text) {
		return post(text);
	}

	@SuppressWarnings("unused")
	public ObjectNode post(String text) {
		ObjectMapper mapper = new ObjectMapper();
		return null;
	}

}
