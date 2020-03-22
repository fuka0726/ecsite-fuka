package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ログイン、ログアウト機能を持つコントローラー.
 * @author fuka
 *
 */
@Controller
@RequestMapping("")
public class LoginLogoutController {
	
	@Autowired
	private HttpServletRequest request;
	
	
	@RequestMapping("/tologin")
	public String toLogin(Model model, String error) {
		if (error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが不正です");
		}
		return "login";
	}
	
	
	
	
}
